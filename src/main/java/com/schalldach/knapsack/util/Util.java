package com.schalldach.knapsack.util;

import java.util.List;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Util {





    public static boolean arrayEquals(int[] lhs, int[] rhs) {
        if (lhs.length != rhs.length) {
            return false;
        }
        for (int i = 0; i < lhs.length; i++) {
            if (lhs[i] != rhs[i]) {
                return false;
            }
        }
        return true;
    }

    public static void swapArraysAtIndex(int[] lhs, int[] rhs, int index) {
        if (lhs.length != rhs.length || lhs.length - 1 < index) {
            throw new ArrayIndexOutOfBoundsException("Comparing apples and peers, are we?");
        }
        int tmp = lhs[index];
        lhs[index] = rhs[index];
        rhs[index] = tmp;

    }

    public static int[] arrayCopy(int[] other) {
        int ret[] = new int[other.length];
        for (int i = 0; i < other.length; i++) {
            ret[i] = other[i];
        }
        return ret;
    }

    public static int[] integerListToArray(List<Integer> list) {
        int ret[] = new int[list.size()];
        int i = 0;
        for (Integer integer : list) {
            ret[i++] = integer;
        }
        return ret;
    }
}
