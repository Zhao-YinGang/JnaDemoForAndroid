package com.zyg.jna;

import com.sun.jna.Structure;

@Structure.FieldOrder({"name", "height", "weight"})
public class JUser extends Structure implements Structure.ByReference {
    public String name;
    public int height;
    public double weight;

    public JUser(String name, int height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public static class ByValue extends JUser implements Structure.ByValue {
        public ByValue(String name, int height, double weight) {
            super(name, height, weight);
        }
    }
}