package com.zyg.jnademo.jna.java;

import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * test the JNA interface for libNative_lib.so
 */
public class JnaTest {

    private static final String TEST_STR_IN_ANDROID = "TEST_STR_IN_ANDROID";
    private static final String TEST_STR_FROM_NATIVE = "TEST_STR_FROM_NATIVE";
    private static final int TEST_STR_BUFF_SIZE = 128;

    private interface TestBlock {
        boolean invoke();
    }

    private static void jnaAssert(TestBlock block) {
        if (!block.invoke()) {
            throw new AssertionError("Assertion failed");
        }
    }

    public static void doJnaTest() {

        jnaAssert(() -> NativeLib.instance.testInt(1, 2) == 1 + 2);

        jnaAssert(() -> {
            String str = TEST_STR_IN_ANDROID;
            NativeLib.instance.testStringByVal(str);
            return TEST_STR_IN_ANDROID.equals(str);
        });

        jnaAssert(() -> {
            Pointer str = new Memory(TEST_STR_BUFF_SIZE);
            str.setString(0, TEST_STR_IN_ANDROID);
            NativeLib.instance.testStringByRef(str, TEST_STR_BUFF_SIZE);
            return TEST_STR_FROM_NATIVE.equals(str.getString(0));
        });

        jnaAssert(() -> {
            Pointer str = new Memory(TEST_STR_BUFF_SIZE);
            str.setString(0, TEST_STR_IN_ANDROID);
            MyString myStr = new MyString.ByValue(str, TEST_STR_BUFF_SIZE);
            NativeLib.instance.testStructByVal(myStr);
            return TEST_STR_IN_ANDROID.equals(myStr.str.getString(0));
        });
        jnaAssert(() -> {
            Pointer str = new Memory(TEST_STR_BUFF_SIZE);
            str.setString(0, TEST_STR_IN_ANDROID);
            MyString myStr = new MyString.ByReference(str, TEST_STR_BUFF_SIZE);
            NativeLib.instance.testStructByRef(myStr);
            return TEST_STR_FROM_NATIVE.equals(myStr.str.getString(0));
        });

        jnaAssert(() -> {
            SumCallback sum = Integer::sum;
            Native.setCallbackThreadInitializer(sum, new CallbackThreadInitializer(true, false));
            return NativeLib.instance.testCallBack(1, 2, sum) == sum.invoke(1, 2);
        });
    }
}
