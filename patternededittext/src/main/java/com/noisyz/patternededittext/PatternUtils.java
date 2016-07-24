package com.noisyz.patternededittext;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Oleg on 14.03.2016.
 */
public class PatternUtils {

    public static ArrayList<PatternChar> createPatternCollection(String pattern, String defaultChar) {
        ArrayList<PatternChar> itemList = new ArrayList<PatternChar>();
        char[] patternChars = pattern.toCharArray();
        for (int i = 0; i < patternChars.length; i++) {
            if (!String.valueOf(patternChars[i]).equals(defaultChar)) {
                PatternChar patternChar = new PatternChar(i, patternChars[i]);
                itemList.add(patternChar);
            }
        }
        return itemList;
    }

    public static String makeStringPatterned(CharSequence input, ArrayList<PatternChar> patternChars) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            PatternChar patternChar = getPatterCharAtIndex(i, patternChars);
            if (patternChar != null && patternChar.getValue() != input.charAt(i)) {
                builder.append(patternChar.getValue());
            }
            builder.append(input.charAt(i));
        }
        return builder.toString();
    }


    public static PatternChar getPatterCharAtIndex(int index, ArrayList<PatternChar> patternChars) {
        for (PatternChar patternChar : patternChars)
            if (patternChar.getIndex() == index)
                return patternChar;
        return null;
    }
}
