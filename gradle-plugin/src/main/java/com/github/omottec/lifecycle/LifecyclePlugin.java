package com.github.omottec.lifecycle;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class LifecyclePlugin extends Transform implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        System.out.println("LifecyclePlugin apply " + target);
        AppExtension android = target.getExtensions().getByType(AppExtension.class);
        android.registerTransform(this);
    }

    @Override
    public String getName() {
        return "LifecyclePlugin";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation)
        throws TransformException, InterruptedException, IOException {
        System.out.println("===================>LifecyclePlugin.transform begin");
        long startTime = System.currentTimeMillis();

        TransformOutputProvider provider = transformInvocation.getOutputProvider();
        // delete old output
        if (provider != null)
            provider.deleteAll();
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        for (TransformInput ti : inputs) {
            for (DirectoryInput di : ti.getDirectoryInputs())
                handleDirectoryInput(di, provider);
            for (JarInput ji : ti.getJarInputs())
                handleJarInput(ji, provider);
        }
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("<===================LifecyclePlugin.transform end cost " + cost + " ms");
    }

    private void handleDirectoryInput(DirectoryInput di, TransformOutputProvider provider) {
        System.out.println("handleDirectoryInput di:" + di + ", provider:" + provider);
    }

    private void handleJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {

        System.out.println("==========> handleJarInput begin");
        System.out.println("jarInput:" + jarInput);

        String jarName = jarInput.getName();
        File file = jarInput.getFile();
        if (!file.getAbsolutePath().endsWith(".jar")) return;

        String md5Name = DigestUtils.md5Hex(file.getAbsolutePath());
        if (jarName.endsWith(".jar"))
            jarName = jarName.substring(0, jarName.length() - 4);

        try {
            JarFile jarFile = new JarFile(file);
            Enumeration enumeration = jarFile.entries();
            File tmpFile = new File(file.getParent() + File.separator + "classes_temp.jar");
            if (tmpFile.exists())
                tmpFile.delete();

            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile));
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                //插桩class
                System.out.println("entryName:" + entryName);
                if (Target.CLASS_NAME_WITH_SUFFIX.equals(entryName)) {
                    jarOutputStream.putNextEntry(zipEntry);
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    ClassVisitor cv = new LifecycleClassVisitor(Opcodes.ASM7, classWriter);

                    classReader.accept(cv, ClassReader.EXPAND_FRAMES);
                    byte[] code = classWriter.toByteArray();
                    jarOutputStream.write(code);
                } else {
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            jarFile.close();
            File dest = outputProvider.getContentLocation(jarName + md5Name,
                jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
            System.out.println("dest:" + dest);
            System.out.println("<========== handleJarInput end");
            FileUtils.copyFile(tmpFile, dest);
            tmpFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}