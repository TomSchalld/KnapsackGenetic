package com.schalldach.knapsack.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by @author Thomas Schalldach on 30/12/2016 software@thomas-schalldach.de.
 */
public class Random {

    public static int getInt(int origin, int upperBound) {
        return ThreadLocalRandom.current().nextInt(origin, upperBound);
    }
}
