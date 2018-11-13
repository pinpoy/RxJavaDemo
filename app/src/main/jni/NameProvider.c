#include <jni.h>

JNIEXPORT jstring JNICALL
Java_jpush_test_com_rxjavademo_Java2CJNI_java2c(JNIEnv *env, jclass type) {

    // TODO
    return (*env)->NewStringUTF(env, "heheh");
}