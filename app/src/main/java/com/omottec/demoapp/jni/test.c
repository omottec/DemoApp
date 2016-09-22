//
// Created by qinbingbing on 9/22/16.
//

# include "com_omottec_demoapp_jni_JniTest.h"
# include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_omottec_demoapp_jni_JniTest_get
  (JNIEnv *env, jobject thiz) {
    printf("invoke get in c\n");
    return (*env)->NewStringUTF(env, "Hello from Jni!");
  }

JNIEXPORT void JNICALL Java_com_omottec_demoapp_jni_JniTest_set
  (JNIEnv *env, jobject thiz, jstring string) {
    printf("invoke set in c\n");
    char* str =(char*) (*env)->GetStringUTFChars(env, string, NULL);
    printf("%s\n", str);
    (*env)->ReleaseStringUTFChars(env, string, str);
  }