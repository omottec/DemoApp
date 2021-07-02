package com.omottec.demoapp.asm;

import android.util.Log;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class AsmComputerClassVisitor extends ClassVisitor {
    public static final String TAG = "AsmComputerClassVisitor";

    public AsmComputerClassVisitor(int api) {
        super(api);
    }

    public AsmComputerClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version,
                      int access,
                      String name,
                      String signature,
                      String superName,
                      String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        Log.d(TAG, new StringBuilder("visit version:").append(version)
            .append(", access:").append(access)
            .append(", name:").append(name)
            .append(", signature:").append(signature)
            .append(", superName:").append(superName)
            .append(", interfaces").append(Arrays.toString(interfaces))
            .toString());
    }

    @Override
    public FieldVisitor visitField(int access,
                                   String name,
                                   String descriptor,
                                   String signature,
                                   Object value) {
        Log.d(TAG, new StringBuilder("visitField access:").append(access)
            .append(", name:").append(name)
            .append(", descriptor:").append(descriptor)
            .append(", signature:").append(signature)
            .append(", value:").append(value)
            .toString());
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {
        Log.d(TAG, new StringBuilder("visitMethod access:").append(access)
            .append(", name:").append(name)
            .append(", descriptor:").append(descriptor)
            .append(", signature:").append(signature)
            .append(", exceptions:").append(Arrays.toString(exceptions))
            .toString());
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        Log.d(TAG, "visitEnd");
        super.visitEnd();
    }
}
