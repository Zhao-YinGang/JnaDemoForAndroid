package com.zyg.jnademo.jna.kotlin

import com.sun.jna.CallbackThreadInitializer
import com.sun.jna.Memory
import com.sun.jna.Native
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.zyg.jnademo.jna.kotlin.NativeLib.Companion.instance as nativeLib

private const val TEST_STR_IN_ANDROID = "TEST_STR_IN_ANDROID"
private const val TEST_STR_FROM_NATIVE = "TEST_STR_FROM_NATIVE"
private const val TEST_STR_BUFF_SIZE = 128

/**
 * test the JNA interface for libNative_lib.so
 */
private fun doJnaTest() {
    fun jnaAssert(block: () -> Boolean) {
        if (!block()) {
            throw AssertionError("Assertion failed")
        }
    }

    jnaAssert { nativeLib.testInt(1, 2) == 1 + 2 }

    jnaAssert {
        val str = TEST_STR_IN_ANDROID
        nativeLib.testStringByVal(str)
        str == TEST_STR_IN_ANDROID
    }
    jnaAssert {
        val str = Memory(TEST_STR_BUFF_SIZE.toLong()).apply {
            setString(0, TEST_STR_IN_ANDROID)
        }
        nativeLib.testStringByRef(str, TEST_STR_BUFF_SIZE)
        str.getString(0) == TEST_STR_FROM_NATIVE
    }

    jnaAssert {
        val str = Memory(TEST_STR_BUFF_SIZE.toLong()).apply {
            setString(0, TEST_STR_IN_ANDROID)
        }
        val myStr = MyString.ByValue(str, TEST_STR_BUFF_SIZE)
        nativeLib.testStructByVal(myStr)
        myStr.str?.getString(0) == TEST_STR_IN_ANDROID
    }
    jnaAssert {
        val str = Memory(TEST_STR_BUFF_SIZE.toLong()).apply {
            setString(0, TEST_STR_IN_ANDROID)
        }
        val myStr =
            MyString.ByReference(str, TEST_STR_BUFF_SIZE)
        nativeLib.testStructByRef(myStr)
        myStr.str?.getString(0) == TEST_STR_FROM_NATIVE
    }

    jnaAssert {
        val sum = SumCallback { a, b -> a + b }
        Native.setCallbackThreadInitializer(sum, CallbackThreadInitializer(true, false))
        nativeLib.testCallBack(1, 2, sum) == sum.invoke(1, 2)
    }
}


suspend fun jnaTest() = withContext(Dispatchers.IO) {
    doJnaTest()
}