package com.omottec.plugin.asm;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import com.android.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.apache.commons.codec.digest.DigestUtils;

public class AsmTransform extends Transform {
    @Override
    public String getName() {
        return "AsmTransform";
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
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        System.out.println("===================> AsmTransform.transform begin");
        TransformOutputProvider op = transformInvocation.getOutputProvider();
        for (TransformInput ti : transformInvocation.getInputs()) {
            for (DirectoryInput di : ti.getDirectoryInputs()) {
                System.out.println("DirectoryInput:" + di.getFile().getAbsolutePath());
//                copyQualifiedContent(op, di, null, Format.DIRECTORY);
            }

            for (JarInput ji : ti.getJarInputs()) {
                System.out.println("JarInput:" + ji.getFile().getAbsolutePath());
//                copyQualifiedContent(op, ji, getUniqueName(ji.getFile()), Format.JAR);
            }
        }
        super.transform(transformInvocation);
        System.out.println("===================> AsmTransform.transform end");

    }

    private String getUniqueName(File jar) {
        String name = jar.getName();
        String suffix = "";
        if (name.lastIndexOf(".") > 0) {
            suffix = name.substring(name.lastIndexOf("."));
            name = name.substring(0, name.lastIndexOf("."));
        }
        String hexName = DigestUtils.md5Hex(jar.getAbsolutePath());
        return String.format("%s_%s%s", name, hexName, suffix);
    }

    private void copyQualifiedContent(TransformOutputProvider provider, QualifiedContent file, String fileName, Format format) throws IOException {
        boolean useDefaultName = fileName == null;
        File dest = provider.getContentLocation(useDefaultName ? file.getName() : fileName, file.getContentTypes(), file.getScopes(), format);
        if (!dest.exists()) {
            dest.mkdirs();
            dest.createNewFile();
        }

        if (useDefaultName) {
            FileUtils.copyDirectory(file.getFile(), dest);
        } else {
            FileUtils.copyFile(file.getFile(), dest);
        }
    }
}
