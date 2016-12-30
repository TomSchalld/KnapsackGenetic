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
        Population population = new Population(5000,instance);
        // evaluate fitness
        while (population.getGeneration() < 1000) {
            //population.purgeAboveAverageFitness(population.getGeneration());
            population.bread();
        }
        population.purgeDead();
        //population.purgeBelowAverageFitness(population.getGeneration());
        System.out.println(population);
        population.purgeBelowAverageFitness(population.getGeneration());
        System.out.println(population);

    }

}
