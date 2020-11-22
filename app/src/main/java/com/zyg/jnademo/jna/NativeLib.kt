package com.zyg.jnademo.jna

import com.sun.jna.*
import com.sun.jna.Structure.FieldOrder

// https://github.com/java-native-access/jna/releases

/**
 * JNA interface for libNative_lib.so (source code: native_lib.cpp)
 */
interface NativeLib : Library {
    companion object {
        private const val NATIVE_LIN_NAME = "native_lib"

        @JvmStatic
        val instance = Native.load(NATIVE_LIN_NAME, NativeLib::class.java) as NativeLib
    }

    fun testInt(a: Int, b: Int): Int

    fun testStringByVal(str: String)
    fun testStringByRef(str: Pointer, len: Int)

    fun testStructByVal(myStr: MyString)
    fun testStructByRef(myStr: MyString)

    fun testCallBack(a: Int, b: Int, sum: SumCallback): Int
}

@FieldOrder("str", "buffLen")
open class MyString @JvmOverloads constructor(
    @JvmField var str: Pointer? = null,
    @JvmField var buffLen: Int = 0
) : Structure() {
    class ByValue(
        str: Pointer? = null,
        buffLen: Int = 0
    ) : MyString(str, buffLen), Structure.ByValue

    class ByReference(
        str: Pointer? = null,
        buffLen: Int = 0
    ) : MyString(str, buffLen), Structure.ByReference
}

fun interface SumCallback : Callback {
    fun invoke(a: Int, b: Int): Int
}
