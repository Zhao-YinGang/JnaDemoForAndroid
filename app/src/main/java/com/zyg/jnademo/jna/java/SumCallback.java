package com.zyg.jnademo.jna.java;

import com.sun.jna.Callback;

public interface SumCallback extends Callback {
    int invoke(int a, int b);
}
