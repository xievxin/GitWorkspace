#include "com_example_jnitest_JniSayHello.h"
#include <stdlib.h>
#include <stdio.h>
#ifdef __cplusplus   
extern "C"  
{   
#endif  
JNIEXPORT jstring JNICALL Java_com_example_jnitest_JniSayHello_keyWords
  (JNIEnv *env, jclass class, jstring part1)
{
     jstring str = (*env)->NewStringUTF(env, "HelloWorld from JNI !");
    return str;  
}
#ifdef __cplusplus   
}   
#endif