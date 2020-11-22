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

void testArrayByVal(const int *array, int len) {
    for (int i = 0; i < len; ++i) {
        LOGI("testArrayByVal step1: array[%d]=%d\n", i, array[i]);
    }
}

void testArrayByRef(int *array, int len) {
    for (int i = 0; i < len; ++i) {
        LOGI("testArrayByRef step1: array[%d]=%d\n", i, array[i]);
        ++array[i];
        LOGI("testArrayByRef step2: array[%d]=%d\n", i, array[i]);
    }
}

void testStringByVal(const char *str) {
    LOGI("testStringByVal: %s\n", str);
}

void testStringByRef(char *str, int len) {
    LOGI("testStringByRef step 1: %s\n", str);
    snprintf(str, len, "%s", TEST_STR_FROM_NATIVE);
    LOGI("testStringByRef step 2: %s\n", str);
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

void testUnionByVal(const MyUnion *myUnion, int fieldNum) {
    switch (fieldNum) {
        case 1:
        case 2:
            LOGI("testUnionByVal: field1:%d\n", myUnion->field1);
            LOGI("testUnionByVal: field2:%lf\n", myUnion->field2);
            break;
        default:
            LOGE("testUnionByVal: fieldNum[%d] error\n", fieldNum);
            break;
    }
}

void testUnionByRef(MyUnion *myUnion, int fieldNum) {
    switch (fieldNum) {
        case 1:
            LOGI("testUnionByRef step1: field1:%d\n", myUnion->field1);
            ++myUnion->field1;
            LOGI("testUnionByRef step2: field1:%d\n", myUnion->field1);
            break;
        case 2:
            LOGI("testUnionByRef step1: field2:%lf\n", myUnion->field2);
            myUnion->field2 += 1.0;
            LOGI("testUnionByRef step2: field2:%lf\n", myUnion->field2);
            break;
        default:
            LOGE("testUnionByRef: fieldNum[%d] error\n", fieldNum);
            break;
    }
}