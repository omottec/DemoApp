package com.github.omottec.apt.processor;

import com.github.omottec.apt.api.GenCode;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * apt_processor/build/classes/java/main/META-INF/services/javax.annotation.processing.Processor
 */
@AutoService(Processor.class)
public class GenCodeProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GenCode.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "GenCodeProcessor.process");
        if (annotations == null || annotations.isEmpty()) return false;

        messager.printMessage(Diagnostic.Kind.NOTE, annotations.toString());
        TypeElement annotationElement = elementUtils.getTypeElement(GenCode.class.getCanonicalName());
        messager.printMessage(Diagnostic.Kind.NOTE, annotationElement.toString());
        if (!annotations.contains(annotationElement)) return false;

        genCode();
        getInfo(roundEnv);
        return true;
    }

    private void getInfo(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(
            elementUtils.getTypeElement(GenCode.class.getCanonicalName()));
        messager.printMessage(Diagnostic.Kind.NOTE, elements.toString());
        for (Element element : elements) {
            messager.printMessage(Diagnostic.Kind.NOTE, "element:" + element
                + ", element.getKind:" + element.getKind()
                + ", element.asType:" + element.asType());

            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            messager.printMessage(Diagnostic.Kind.NOTE, annotationMirrors.toString());
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                messager.printMessage(Diagnostic.Kind.NOTE, "annotationMirror.getAnnotationType:" + annotationMirror.getAnnotationType()
                    + ", annotationMirror.getElementValues:" + annotationMirror.getElementValues());
            }

            Element enclosingElement = element.getEnclosingElement();
            messager.printMessage(Diagnostic.Kind.NOTE, "enclosingElement:" + enclosingElement
                + ", enclosingElement.getKind:" + enclosingElement.getKind()
                + ", enclosingElement.asType:" + enclosingElement.asType());

            List<? extends Element> enclosedElements = element.getEnclosedElements();
            for (Element enclosedElement : enclosedElements) {
                messager.printMessage(Diagnostic.Kind.NOTE, "enclosedElement:" + enclosedElement
                    + ", enclosedElement.getKind:" + enclosedElement.getKind()
                    + ", enclosedElement.asType:" + enclosedElement.asType());
            }
        }
    }

    private void genCode() {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, "TAG")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer("$S", "JavaPoetFragment")
            .build();

        ClassName layoutInflater = ClassName.get("android.view", "LayoutInflater");
        ClassName viewGroup = ClassName.get("android.view", "ViewGroup");
        ClassName bundle = ClassName.get("android.os", "Bundle");
        ClassName view = ClassName.get("android.view", "View");
        ClassName nullable = ClassName.get("android.support.annotation", "Nullable");

        MethodSpec onCreateView = MethodSpec.methodBuilder("onCreateView")
            .addParameter(ParameterSpec.builder(layoutInflater, "inflater").build())
            .addParameter(
                ParameterSpec.builder(viewGroup, "container")
                    .addAnnotation(nullable)
                    .build())
            .addParameter(
                ParameterSpec.builder(bundle, "savedInstanceState")
                    .addAnnotation(nullable)
                    .build())
            .returns(view)
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return inflater.inflate($T.layout.full_screen_text, container, false)",
                ClassName.get("com.omottec.demoapp", "R"))
            .addAnnotation(Override.class)
            .addAnnotation(nullable)
            .build();

        MethodSpec onViewCreate = MethodSpec.methodBuilder("onViewCreated")
            .addParameter(ParameterSpec.builder(view, "view").build())
            .addParameter(
                ParameterSpec.builder(bundle, "savedInstanceState")
                    .addAnnotation(nullable)
                    .build())
            .addModifiers(Modifier.PUBLIC)
            .addStatement("$T.i(TAG, $S)", ClassName.get("android.util", "Log"), "onViewCreated")
            .addAnnotation(Override.class)
            .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("JavaPoetFragmentGen")
            .superclass(ClassName.get("android.support.v4.app", "Fragment"))
            .addModifiers(Modifier.PUBLIC)
            .addField(fieldSpec)
            .addMethod(onCreateView)
            .addMethod(onViewCreate)
            //.addAnnotation(GenCode.class)
            .build();

        try {
            JavaFile.builder("com.omottec.demoapp.apt", typeSpec)
                .build()
                .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
