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
            int[] array = new int[]{1, 1, 1, 1, 1};
            NativeLib.instance.testArrayByVal(array, array.length);
            for (int i : array) {
                if (i != 1) {
                    return false;
                }
            }
            return true;
        });
        jnaAssert(() -> {
            int len = 5;
            Pointer array = new Memory(4L * len);
            for (int i = 0; i < 5; ++i){
                array.setInt(4L * i, 1);
            }
            NativeLib.instance.testArrayByRef(array, len);
            for (int i : array.getIntArray(0, len)) {
                if (i != 2) {
                    return false;
                }
            }
            return true;
        });

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

        jnaAssert(() -> {
            MyUnion myUnion = new MyUnion.ByValue();
            myUnion.setType("field1");
            myUnion.field1 = 1;
            NativeLib.instance.testUnionByVal(myUnion, 1);
            return myUnion.field1 == 1;
        });
        jnaAssert(() -> {
            MyUnion myUnion = new MyUnion.ByValue();
            myUnion.setType("field2");
            myUnion.field2 = 2.0;
            NativeLib.instance.testUnionByVal(myUnion, 2);
            return myUnion.field2 > 1.999 && myUnion.field2 < 2.001;
        });
        jnaAssert(() -> {
            MyUnion myUnion = new MyUnion.ByReference();
            myUnion.setType("field1");
            myUnion.field1 = 1;
            NativeLib.instance.testUnionByRef(myUnion, 1);
            return myUnion.field1 == 2;
        });
        jnaAssert(() -> {
            MyUnion myUnion = new MyUnion.ByReference();
            myUnion.setType("field2");
            myUnion.field2 = 2.0;
            NativeLib.instance.testUnionByRef(myUnion, 2);
            return myUnion.field2 > 2.999 && myUnion.field2 < 3.001;
        });
    }
}
