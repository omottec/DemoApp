package com.github.omottec.apt.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ButterKnife {
    static final Map<Class<?>, Constructor<? extends ViewBinding>> BINDINGS = new HashMap<>();

    public static void inject(Object object) {
        if (object == null) return;

        Class<?> clazz = object.getClass();
        try {
            Constructor<? extends ViewBinding> constructor = findBindingConstructorForClass(clazz);
            ViewBinding viewBinding = constructor.newInstance();
            viewBinding.bindView(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Constructor<? extends ViewBinding> findBindingConstructorForClass(Class<?> clazz)
            throws ClassNotFoundException, NoSuchMethodException {
        Constructor<? extends ViewBinding> constructor = BINDINGS.get(clazz);
        if (constructor == null) {
            String clazzName = clazz.getName();
            Class<?> bindingClass = clazz.getClassLoader().loadClass(clazzName + "_ViewBinding");
            constructor = (Constructor<? extends ViewBinding>) bindingClass.getConstructor();
            BINDINGS.put(clazz, constructor);
        }
        return constructor;
    }
}
