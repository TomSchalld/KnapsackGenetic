package com.schalldach.knapsack;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Knapsack {
    private final Instance instance;
    private final int[] solution;
    private int maxCost;
    private int maxWeight;
    final private int MULTIPLIER;

    public Knapsack(Instance instance, int MULTIPLIER) {
        this.instance = instance;
        this.MULTIPLIER = MULTIPLIER;
        this.solution = new int[instance.getInstanceSize()];
    }

    public void solveWithMultiplier() {
        for (int i = 0; i < MULTIPLIER; i++) {
            solve();
            System.out.println();

        }
    }


    public void solve() {
        final int instanceSize = instance.getInstanceSize();
        Population initialPopulation = new Population(instanceSize*instanceSize*(instanceSize/2),instance);
        // evaluate fitness
        System.out.println(initialPopulation);

    }

}
