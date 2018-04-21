#include <jni.h>
#include <string>

#include <android/log.h>

#define TAG  "AndroidLearn JNI"

#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,TAG,__VA_ARGS__) // 定义LOGF类型

jstring jniFromString2(JNIEnv *env, jobject instance);

void callJavaMethod(JNIEnv *env, jobject instance);

static const JNINativeMethod gMethods[] = {
        {"stringFromJNI2", "()Ljava/lang/String;", (jstring *) jniFromString2},
        {"callJavaMethod", "()V",                  (void *) callJavaMethod}
};


extern "C"
JNIEXPORT jstring JNICALL
Java_me_jiahuan_androidlearn_example_function_jni_JNIActivity_stringFromJNI(JNIEnv *env,
                                                                            jobject instance) {
    // 实例化Java对象
    jclass date_clazz = env->FindClass("java/util/Date");
    jmethodID date_init_method_id = env->GetMethodID(date_clazz, "<init>", "()V");
    jobject now = env->NewObject(date_clazz, date_init_method_id);
    jmethodID mid_date_get_time_method_id = env->GetMethodID(date_clazz, "getTime", "()J");
    LOGD("Time now is %ld", env->CallLongMethod(now, mid_date_get_time_method_id));
    LOGD("%s", "JNI CALL stringFromJNI");
    return env->NewStringUTF("Hello From C++");
}

jstring jniFromString2(JNIEnv *env, jobject instance) {
    LOGD("%s", "动态注册 JNI CALL stringFromJNI2");
    return env->NewStringUTF("动态注册");
}

void callJavaMethod(JNIEnv *env, jobject instance) {

    jclass clazz = env->FindClass("me/jiahuan/androidlearn/example/function/jni/JNIActivity");

    // 设置Field
    jfieldID fieldID = env->GetFieldID(clazz, "field1", "D");
    LOGD("%f", env->GetDoubleField(instance, fieldID));
    env->SetDoubleField(instance, fieldID, 30);


    jfieldID staticFieldID = env->GetStaticFieldID(clazz, "staticField1", "F");
    LOGD("%f", env->GetStaticFloatField(clazz, staticFieldID));
    env->SetStaticFloatField(clazz, staticFieldID, 50.0f);

    // 调用非static方法
    jmethodID methodID = env->GetMethodID(clazz, "callInJNI", "(Ljava/lang/String;)V");
    env->CallVoidMethod(instance, methodID, env->NewStringUTF("hello body"));

    // 调用static方法
    jmethodID methodStaticID = env->GetStaticMethodID(clazz, "callStaticInJNI",
                                                      "(Ljava/lang/String;)V");
    env->CallStaticVoidMethod(clazz, methodStaticID, env->NewStringUTF("Hello static boy"));
}


jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGD("%s", "JNI_OnLoad");
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        LOGE("get env error");
        return -1;
    }
    jclass clazz = env->FindClass("me/jiahuan/androidlearn/example/function/jni/JNIActivity");

    if (clazz == NULL) {
        LOGE("%s", "get clazz error");
        return -1;
    }

    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods) / sizeof(JNINativeMethod)) < 0) {
        LOGE("动态注册失败");
        return -1;
    }

    return JNI_VERSION_1_6;
}


void JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGD("%s", "JNI_OnUnload");
}

