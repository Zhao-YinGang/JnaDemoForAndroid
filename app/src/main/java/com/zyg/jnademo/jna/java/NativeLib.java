package com.zyg.jnademo.jna.java;

// https://github.com/java-native-access/jna/releases

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * JNA interface for libNative_lib.so (source code: native_lib.cpp)
 */
interface NativeLib extends Library {
    String NATIVE_LIN_NAME = "native_lib";

    NativeLib instance = (NativeLib) Native.load(NATIVE_LIN_NAME, NativeLib.class);

    int testInt(int a, int b);

    void testStringByVal(String str);

    void testStringByRef(Pointer str, int len);

    void testStructByVal(MyString myStr);

    void testStructByRef(MyString myStr);

    int testCallBack(int a, int b, SumCallback sum);
}