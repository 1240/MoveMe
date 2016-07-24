package com.noisyz.patternededittext;

/**
 * Created by Oleg on 14.03.2016.
 */
public class PatternChar {

    private char value;
    private int index;

    public PatternChar(int index, char value) {
        this.index = index;
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
