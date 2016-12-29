package com.schalldach.knapsack;

import com.schalldach.knapsack.util.Import;
import com.schalldach.knapsack.util.TestMemory;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Import i = new Import();

        List<Instance> instances = i.getInstances();

        Knapsack sack = new Knapsack(instances.get(0),1);
        sack.solveWithMultiplier();

        TestMemory.test();
        System.gc();
        TestMemory.test();
    }
}
