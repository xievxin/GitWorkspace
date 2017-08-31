#include <jni.h>
#include <stdio.h>

jint init = 0;
jclass cls;

void main() {
    printf("lllll");
}

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM* vm, void* reserved) {
    init = 1;
    return JNI_VERSION_1_4;
}

JNIEXPORT jstring JNICALL
Java_com_xx_ndk_JniTest_getHW(JNIEnv *env, jclass type) {

    char* string = "aaaaa";
    jstring str = (*env)->NewStringUTF(env, "abc");
    main();
    return str;
}

JNIEXPORT void JNICALL
Java_com_xx_ndk_JniTest_sayDog(JNIEnv *env, jobject instance) {

    jstring num = (*env)->NewStringUTF(env, "2");

    jclass cls = (*env)->GetObjectClass(env, instance);
    jmethodID jmethodID = (*env)->GetMethodID(env, cls, "wangwang", "(Ljava/lang/String;)V");
    (*env)->CallVoidMethod(env, instance, jmethodID, num);

    (*env)->ExceptionOccurred(env);
    (*env)->GetVersion(env);
}
