package com.schalldach.knapsack.util;

import java.util.List;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class PrimitiveConvertor {



    public static int[] integerListToArray(List<Integer> list) {
        int ret[] = new int[list.size()];
        int i = 0;
        for (Integer integer : list) {
            ret[i++]= integer;
        }
        return ret;
    }
}
