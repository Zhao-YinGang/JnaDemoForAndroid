package com.zyg.jnademo.jna.java;

import com.sun.jna.Structure;
import com.sun.jna.Union;

public class MyUnion extends Union {
    public int field1 = 0;
    public double field2 = 0.0;

    public static class ByValue extends MyUnion implements Structure.ByValue {
    }

    public static class ByReference extends MyUnion implements Structure.ByReference {
    }
}