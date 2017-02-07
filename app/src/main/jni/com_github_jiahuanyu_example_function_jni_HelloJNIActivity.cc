#include "com_github_jiahuanyu_example_function_jni_HelloJNIActivity.h"

JNIEXPORT jstring JNICALL Java_com_github_jiahuanyu_example_function_jni_HelloJNIActivity_stringFromJNI(
        JNIEnv *env, jobject obj) {
    return env->NewStringUTF("This just a test for Android Studio NDK JNI developer!");
}


JNIEXPORT jboolean JNICALL Java_com_github_jiahuanyu_example_function_jni_HelloJNIActivity_writeFile(
        JNIEnv *env, jobject obj, jstring path) {
    const char *pathChar = env->GetStringUTFChars(path, 0);
    FILE *stream = fopen(pathChar, "w");
    if (NULL == stream) {

    } else {
        if (1 != fwrite("1", sizeof(char), 1, stream)) {

        } else {
            fflush(stream);
        }
        fclose(stream);
    }
    env->ReleaseStringUTFChars(path, pathChar);
    return 0;
}