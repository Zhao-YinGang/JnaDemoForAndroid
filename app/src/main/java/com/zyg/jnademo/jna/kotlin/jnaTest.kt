package com.zyg.jnademo.jna.kotlin

import android.util.Log
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
        val array = IntArray(5) { 1 }
        nativeLib.testArrayByVal(array, array.size)
        array.all { it == 1 }
    }
    jnaAssert {
        val len = 5
        val array = Memory(4L * len)
        for (i in 0 until 5) {
            array.setInt(4L * i.toLong(), 1)
        }
        nativeLib.testArrayByRef(array, len)
        array.getIntArray(0, len)?.all { it == 2 } ?: false
    }

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

    jnaAssert {
        val myUnion = MyUnion.ByValue()
        myUnion.setType("field1")
        myUnion.field1 = 1
        nativeLib.testUnionByVal(myUnion, 1)
        myUnion.field1 == 1
    }
    jnaAssert {
        val myUnion = MyUnion.ByValue()
        myUnion.setType("field2")
        myUnion.field2 = 2.0
        nativeLib.testUnionByVal(myUnion, 2)
        myUnion.field2 > 1.999 && myUnion.field2 < 2.001
    }
    jnaAssert {
        val myUnion = MyUnion.ByReference()
        myUnion.setType("field1")
        myUnion.field1 = 1
        nativeLib.testUnionByRef(myUnion, 1)
        myUnion.field1 == 2
    }
    jnaAssert {
        val myUnion = MyUnion.ByReference()
        myUnion.setType("field2")
        myUnion.field2 = 2.0
        nativeLib.testUnionByRef(myUnion, 2)
        myUnion.field2 > 2.999 && myUnion.field2 < 3.001
    }
}


suspend fun jnaTest() = withContext(Dispatchers.IO) {
    doJnaTest()
}