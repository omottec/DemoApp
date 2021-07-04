package com.github.omottec.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;

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
        mv.visitLdcInsn("AsmComputer");
        mv.visitLdcInsn("before start");
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d",
            "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);

        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("StartMethodVisitor#visitInsn opcode:" + opcode);
        if (opcode == RETURN) {
            mv.visitLdcInsn("AsmComputer");
            mv.visitLdcInsn("after start");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d",
                "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
        System.out.println("StartMethodVisitor#visitEnd");
        super.visitEnd();
    }
}
