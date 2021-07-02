package com.github.omottec.asm;

import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

public class AsmComputerVisitor extends ClassVisitor {
    public AsmComputerVisitor(int api) {
        super(api);
    }

    public AsmComputerVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version,
                      int access,
                      String name,
                      String signature,
                      String superName,
                      String[] interfaces) {
        System.out.println(new StringBuilder("AsmComputerVisitor.visit version:").append(version)
            .append(", access:").append(access)
            .append(", name:").append(name)
            .append(", signature:").append(signature)
            .append(", superName:").append(superName)
            .append(", interfaces:").append(Arrays.toString(interfaces))
            .toString());
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {
        System.out.println(new StringBuilder("AsmComputerVisitor.visitMethod ")
            .append(" access:").append(access)
            .append(", name:").append(name)
            .append(", descriptor:").append(descriptor)
            .append(", signature:").append(signature)
            .append(", exceptions:").append(Arrays.toString(exceptions))
            .toString());
        MethodVisitor mv =
            super.visitMethod(access, name, descriptor, signature, exceptions);
        //if (name.equals("start")) {
        //    return new StartMethodVisitor(Opcodes.ASM7, mv);
        //}
        return mv;
    }

    @Override
    public FieldVisitor visitField(int access,
                                   String name,
                                   String descriptor,
                                   String signature,
                                   Object value) {
        System.out.println(new StringBuilder("AsmComputerVisitor.visitField ")
            .append(" access:").append(access)
            .append(", name:").append(name)
            .append(", descriptor:").append(descriptor)
            .append(", signature:").append(signature)
            .append(", value:").append(value)
            .toString());
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        System.out.println("AsmComputerVisitor.visitEnd");
        Method m = Method.getMethod("void start1 ()");
        GeneratorAdapter mg =
            new GeneratorAdapter(Opcodes.ACC_PUBLIC, m, null, null, cv);
        mg.invokeStatic(Type.getType(System.class), Method.getMethod("long currentTimeMillis ()"));
        int local = mg.newLocal(Type.LONG_TYPE);
        mg.storeLocal(local, Type.LONG_TYPE);
        mg.returnValue();
        mg.endMethod();
        super.visitEnd();
    }
}
