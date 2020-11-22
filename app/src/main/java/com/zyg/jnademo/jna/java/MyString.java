package com.zyg.jnademo.jna.java;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"str", "buffLen"})
public class MyString extends Structure {
    public Pointer str;
    public int buffLen;

    public MyString(Pointer str, int buffLen) {
        this.str = str;
        this.buffLen = buffLen;
    }

    public static class ByValue extends MyString implements Structure.ByValue {
        public ByValue(Pointer str, int buffLen) {
            super(str, buffLen);
        }
    }

    public static class ByReference extends MyString implements Structure.ByReference {
        public ByReference(Pointer str, int buffLen) {
            super(str, buffLen);
        }
    }
}