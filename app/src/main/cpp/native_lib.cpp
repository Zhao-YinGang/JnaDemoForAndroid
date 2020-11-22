#include <string>
#include <android/log.h>
#include "native_lib.h"
#include <string.h>
#define LOG_TAG "native_lib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


int getInt() {
    return 12345;
}

void getString(char *str, size_t len) {
    snprintf(str, len,"getString");
}

const char * getString2() {
    return "getString2";
}

void testUser(User user) {
    LOGI("user: %s height: %d weight: %.2f \n", user.name, user.height, user.weight);
    sprintf(user.name, "X");
}

void testUserPointer(User *user) {
    LOGI("user: %s height: %d weight: %.2f \n", user->name, user->height, user->weight);
    sprintf(user->name, "X");
}

void testUserPointer2(User *user) {
    user = new User();
    user->height = 10;
}