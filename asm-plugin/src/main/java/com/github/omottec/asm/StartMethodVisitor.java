package com.github.omottec.asm;

import org.objectweb.asm.MethodVisitor;

public class StartMethodVisitor extends MethodVisitor {
    public StartMethodVisitor(int api) {
        super(api);
    }

    public StartMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        System.out.println("StartMethodVisitor#visitCode");

        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("StartMethodVisitor#visitInsn opcode:" + opcode);
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
        System.out.println("StartMethodVisitor#visitEnd");
        super.visitEnd();
    }
}
