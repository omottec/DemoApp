package com.github.omottec.lifecycle;

import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {
    private String mClassName;

    public LifecycleClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version,
                      int access,
                      String name,
                      String signature,
                      String superName,
                      String[] interfaces) {
        System.out.println(new StringBuilder("LifecycleClassVisitor.visit version:").append(version)
            .append(", access:").append(access)
            .append(", name:").append(name)
            .append(", signature:").append(signature)
            .append(", superName:").append(superName)
            .append(", interfaces:").append(Arrays.toString(interfaces))
            .toString());
        mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {
        System.out.println(new StringBuilder("LifecycleClassVisitor.visitMethod ")
            .append(" access:").append(access)
            .append(", name:").append(name)
            .append(", descriptor:").append(descriptor)
            .append(", signature:").append(signature)
            .append(", exceptions:").append(Arrays.toString(exceptions))
            .toString());
        MethodVisitor mv =
            cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (Target.CLASS_NAME.equals(mClassName)) {
            if ("onCreate".equals(name)) {
                return new LifecycleOnCreateMethodVisitor(Opcodes.ASM7, mv);
            } else if ("onDestroy".equals(name)) {
                return new LifecycleOnDestroyMethodVisitor(Opcodes.ASM7, mv);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        System.out.println("LifecycleClassVisitor.visitEnd");
        super.visitEnd();
    }
}
