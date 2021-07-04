package com.github.omottec.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.POP;

public class GetStatusMethodVisitor extends MethodVisitor {
    public GetStatusMethodVisitor(int api) {
        super(api);
    }

    public GetStatusMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitCode() {
        System.out.println("GetStatusMethodVisitor.visitCode");
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("GetStatusMethodVisitor.visitInsn");
        if (opcode == Opcodes.IRETURN) {
            mv.visitLdcInsn("AsmComputer");
            mv.visitLdcInsn("before return from getStatus");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d",
                "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
        System.out.println("GetStatusMethodVisitor.visitEnd");
        super.visitEnd();
    }
}
