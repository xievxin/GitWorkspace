LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := jniTest
LOCAL_SRC_FILES := com_example_jnitest_JniSayHello.c
include $(BUILD_SHARED_LIBRARY)