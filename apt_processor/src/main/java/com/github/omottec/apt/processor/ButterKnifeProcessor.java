package com.github.omottec.apt.processor;

import com.github.omottec.apt.api.BindView;
import com.github.omottec.apt.api.ViewBinding;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

// 自动注册未生效
@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {
    private Map<TypeElement, List<Element>> elementPackage = new HashMap<>();
    public static final String VIEW_TYPE = "android.view.View";
    public static final String VIEW_BINDING_TYPE = ViewBinding.class.getCanonicalName();

    private Elements elementUtils;

    private Filer filter;

    private Messager messager;

    private Types typeUtils;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filter = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process annotation");
        if (annotations == null || annotations.isEmpty()) return false;
        messager.printMessage(Diagnostic.Kind.NOTE, "annotations:" + annotations);
        elementPackage.clear();
        Set<? extends Element> bindViewElements = roundEnv.getElementsAnnotatedWith(BindView.class);
        collectData(bindViewElements);
        generateCode();
        return true;
    }

    private void generateCode() {
        Iterator<Map.Entry<TypeElement, List<Element>>> iterator = elementPackage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<TypeElement, List<Element>> entry = iterator.next();
            TypeElement parent = entry.getKey();
            List<Element> elements = entry.getValue();
            MethodSpec methodSpec = generateBindViewMethod(parent, elements);

            PackageElement packageElement = elementUtils.getPackageOf(parent);
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "typeElement:" + parent + ", packageElement:" + packageElement);
            String packageName = packageElement.getQualifiedName().toString();
            ClassName viewBindingInterface = ClassName.get(elementUtils.getTypeElement(VIEW_BINDING_TYPE));
            String className = parent.getQualifiedName().toString().substring(
                    packageName.length() + 1).replace('.', '$');
            ClassName bindingClassName = ClassName.get(packageName, className + "_ViewBinding");

            TypeSpec typeSpec = TypeSpec.classBuilder(bindingClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(viewBindingInterface)
                    .addMethod(methodSpec)
                    .build();
            try {
                JavaFile.builder(packageName, typeSpec).build().writeTo(filter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MethodSpec generateBindViewMethod(TypeElement parent, List<Element> elements) {
        ParameterSpec.Builder parameter = ParameterSpec.builder(TypeName.OBJECT, "target");
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("bindView");
        bindViewMethod.addParameter(parameter.build());
        bindViewMethod.addModifiers(Modifier.PUBLIC);
        bindViewMethod.addStatement("$T temp = ($T) target", parent, parent);
        for (Element element : elements) {
            int id = element.getAnnotation(BindView.class).value();
            bindViewMethod.addStatement("temp.$N = temp.findViewById($L)",
                    element.getSimpleName().toString(),
                    id);
        }
        return bindViewMethod.build();
    }

    private void collectData(Set<? extends Element> elements) {
        Iterator<? extends Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            TypeMirror elementTypeMirror = element.asType();
            TypeMirror viewTypeMirror = elementUtils.getTypeElement(VIEW_TYPE).asType();
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "elementTypeMirror:" + elementTypeMirror
                            + ", viewTypeMirror:" + viewTypeMirror);
            if (typeUtils.isSameType(elementTypeMirror, viewTypeMirror)
                    || typeUtils.isSubtype(elementTypeMirror, viewTypeMirror)) {
                TypeElement parent = (TypeElement) element.getEnclosingElement();
                List<Element> parentElements = elementPackage.get(parent);
                if (parentElements == null) {
                    parentElements = new ArrayList<>();
                    elementPackage.put(parent, parentElements);
                }
                parentElements.add(element);
            } else {
                throw new RuntimeException("BindView应该标注在类型是View的字段上");
            }
        }
    }
}
