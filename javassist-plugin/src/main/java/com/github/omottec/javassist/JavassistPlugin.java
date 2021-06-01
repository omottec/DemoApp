package com.github.omottec.javassist;

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
import com.github.omottec.Target;
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
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class JavassistPlugin extends Transform implements Plugin<Project> {
    private AppExtension appExtension;
    private boolean incremental;

    @Override
    public void apply(@NotNull Project target) {
        System.out.println("JavassistPlugin 1.1.14 apply " + target);
        appExtension = target.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(this);
    }

    @Override
    public String getName() {
        return "JavassistPlugin";
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
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation)
        throws TransformException, InterruptedException, IOException {
        System.out.println("*************************>JavassistPlugin.transform begin");
        long startTime = System.currentTimeMillis();

        incremental = transformInvocation.isIncremental();
        System.out.println("incremental:" + incremental);

        TransformOutputProvider provider = transformInvocation.getOutputProvider();
        if (!incremental)
            provider.deleteAll();

        try {
            System.out.println("appExtension.getBootClasspath:" + appExtension.getBootClasspath());
            ClassPool.getDefault().appendClassPath(appExtension.getBootClasspath().get(0).getAbsolutePath());
            Collection<TransformInput> inputs = transformInvocation.getInputs();
            for (TransformInput ti : inputs) {
                for (DirectoryInput di : ti.getDirectoryInputs())
                    handleDirectoryInput(di, provider);
                for (JarInput ji : ti.getJarInputs())
                    handleJarInput(ji, provider);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("<*************************JavassistPlugin.transform end cost " + cost + " ms");
    }

    private void handleDirectoryInput(DirectoryInput di, TransformOutputProvider provider) {
        System.out.println("***********> JavassistPlugin.handleDirectoryInput begin");
        System.out.println("directoryInput:" + di);
        ClassPool classPool = ClassPool.getDefault();
        try {
            classPool.appendClassPath(di.getFile().getAbsolutePath());
            File dest = provider.getContentLocation(di.getName(), di.getContentTypes(), di.getScopes(),
                    Format.DIRECTORY);
            FileUtils.copyDirectory(di.getFile(), dest);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("<*********** JavassistPlugin.handleDirectoryInput end");
    }

    private void handleJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        System.out.println("***********> JavassistPlugin.handleJarInput begin");
        System.out.println("jarInput:" + jarInput);

        String jarName = jarInput.getName();
        if (jarName.endsWith(".jar"))
            jarName = jarName.substring(0, jarName.length() - 4);

        File file = jarInput.getFile();
        String md5Name = DigestUtils.md5Hex(file.getAbsolutePath());

        File dest = outputProvider.getContentLocation(jarName + md5Name,
            jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
        System.out.println("dest:" + dest + ", exists:" + dest.exists());

        if (incremental) {
            switch (jarInput.getStatus()) {
                case NOTCHANGED:
                    break;
                case ADDED:
                case CHANGED:
                    foreachJar(jarInput, dest);
                    break;
                case REMOVED:
                    deleteDest(dest);
                    break;
            }
        } else {
            foreachJar(jarInput, dest);
        }
        System.out.println("<*********** JavassistPlugin.handleJarInput end");
    }

    private void deleteDest(File dest) {
        try {
            FileUtils.deleteIfExists(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void foreachJar(JarInput jarInput, File dest) {
        File file = jarInput.getFile();
        try {
            JarFile jarFile = new JarFile(file);
            Enumeration enumeration = jarFile.entries();

            if (!enumeration.hasMoreElements()) {
                FileUtils.copyFile(file, dest);
                return;
            }

            ClassPool.getDefault().appendClassPath(file.getAbsolutePath());

            File tmpFile = new File(file.getParent() + File.separator + "classes_temp.jar");
            FileUtils.deleteIfExists(tmpFile);
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile));

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                System.out.println("entryName:" + entryName);
                if (Target.CLASS_NAME_WITH_SUFFIX.equals(entryName)) {
                    //插桩class
                    jarOutputStream.putNextEntry(zipEntry);
                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.get(Target.CLASS_NAME.replace("/", "."));
                    System.out.println("ctClass:" + ctClass);
                    if (ctClass.isFrozen())
                        ctClass.defrost();
                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate");
                    System.out.println("ctMethod:" + ctMethod);
                    ctMethod.insertBefore("android.util.Log.i(\"Javassist\", \"=====  onCreate begin===\");");
                    ctMethod.insertAfter("android.util.Log.i(\"Javassist\", \"=====  onCreate end===\");");
                    byte[] bytes = ctClass.toBytecode();
                    ctClass.detach();
                    jarOutputStream.write(bytes);
                } else {
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            jarFile.close();

            FileUtils.copyFile(tmpFile, dest);
            tmpFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}
