LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni
LOCAL_SRC_FILES := com_github_jiahuanyu_example_function_jni_HelloJNIActivity.cc

LOCAL_CPP_EXTENSION := .cc

include $(BUILD_SHARED_LIBRARY)