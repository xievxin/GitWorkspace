#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_xx_ndk_JniTest_getHW(JNIEnv *env, jobject instance) {

    // TODO


    return (*env)->NewStringUTF(env, "hello world");
}

JNIEXPORT jstring JNICALL
Java_com_xx_ndk_JniTest_getNameDetail(JNIEnv *env, jclass type, jstring name_) {
    const char *name = (*env)->GetStringUTFChars(env, name_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, name_, name);

    return (*env)->NewStringUTF(env, name_);
}

JNIEXPORT jstring JNICALL
Java_com_xx_ndk_SecJni_ssss(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "");
}