#include <string>
#include <android/log.h>
#include "native_lib.h"
#include <cstring>

#ifndef LOG_TAG
#define LOG_TAG "native_lib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#endif

const char *TEST_STR_FROM_NATIVE = "TEST_STR_FROM_NATIVE";

int testInt(int a, int b) {
    return a + b;
}

void testStringByVal(const char *str) {
    LOGI("testStringByVal: %s", str);
}

void testStringByRef(char *str, int len) {
    LOGI("testStringByRef step 1: %s", str);
    snprintf(str, len, "%s", TEST_STR_FROM_NATIVE);
    LOGI("testStringByRef step 2: %s", str);
}

void testStructByVal(const MyString *myStr) {
    LOGI("testStructByVal: str:%s\n", myStr->str);
}

void testStructByRef(MyString *myStr) {
    LOGI("testStructByRef step 1: myStr:%s\n", myStr->str);
    snprintf(myStr->str, myStr->buffLen, "%s", TEST_STR_FROM_NATIVE);
    LOGI("testStructByRef step 2: myStr:%s\n", myStr->str);
}

int testCallBack(int a, int b, SumCallback sum) {
    return sum(a, b);
}