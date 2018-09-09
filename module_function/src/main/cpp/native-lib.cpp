#include <jni.h>
#include <string>
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

#define LOG_TAG "NATIVE_LIB"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG,__VA_ARGS__)


jstring stringFromJNI2(JNIEnv *env, jobject obj);


JNINativeMethod nativeMethods[] = {
        {"stringFromJNI2", "()Ljava/lang/String;", (jstring *) stringFromJNI2}
};

JNIEXPORT jstring
Java_me_jiahuan_androidlearn_function_jni_JNIActivity_stringFromJNI(JNIEnv *env, jobject obj) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

jstring stringFromJNI2(JNIEnv *env, jobject obj) {
    std::string str = "动态注册";
    return env->NewStringUTF(str.c_str());
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("JNI_OnLoad");

    JNIEnv *env = nullptr;

    jint result = vm->GetEnv((void **) &env, JNI_VERSION_1_6);

    if (result != JNI_OK) {
        LOGE("JNI GET ENV ERROR");
        return JNI_ERR;
    }


    jclass clazz = env->FindClass("me/jiahuan/androidlearn/function/jni/JNIActivity");
    if (clazz == nullptr) {
        LOGE("FIND JNIActivity Error");
        return JNI_ERR;
    }

    // 动态注册本地方法
    result = env->RegisterNatives(clazz, nativeMethods, sizeof(nativeMethods) / sizeof(nativeMethods[0]));
    if (result != JNI_OK) {
        LOGE("动态注册ERROR");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}

#ifdef __cplusplus
}
#endif
