#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

void callJavaMethod(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("com/omottec/demoapp/fragment/JniFragment");
    if (clazz == NULL) {
        printf("find class JniFragment error!");
        return;
    }
    jmethodID id = env->GetStaticMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");
    if (id == NULL) {
        printf("find method methodCalledByJni error!");
    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod in test.cpp.");
    env->CallStaticVoidMethod(clazz, id, msg);
}

jstring Java_com_omottec_demoapp_fragment_JniFragment_get(JNIEnv *env, jobject thiz) {
    printf("invoke get in c++\n");
    callJavaMethod(env, thiz);
    return env->NewStringUTF("Hello from Jni in libjni-test.so !");
}

void Java_com_omottec_demoapp_fragment_JniFragment_set(JNIEnv *env, jobject thiz, jstring string) {
    printf("invoke set in C++\n");
    char* str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%s\n", str);
    env->ReleaseStringUTFChars(string, str);
}

#ifdef __cplusplus
}
#endif
