package com.omottec.plugin.lifecycle;

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
        System.out.println("LifecycleClassVisitor.visit " + name);
        mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {
        System.out.println("LifecycleClassVisitor.visitMethod " + name);
        MethodVisitor mv =
            cv.visitMethod(access, name, descriptor, signature, exceptions);
        if ("androidx/fragment/app/FragmentActivity".equals(mClassName)) {
            if ("onCreate".equals(name)) {
                return new LifecycleOnCreateMethodVisitor(Opcodes.ASM9, mv);
            } else if ("onDestroy".equals(name)) {
                return new LifecycleOnDestroyMethodVisitor(Opcodes.ASM9, mv);
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
