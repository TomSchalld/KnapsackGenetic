package com.schalldach.knapsack;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Knapsack {
    private final Instance instance;
    private int[] solution;
    private int maxCost;
    private int maxWeight;
    final private int MULTIPLIER;

    public Knapsack(Instance instance, int MULTIPLIER) {
        this.instance = instance;
        this.MULTIPLIER = MULTIPLIER;
        this.solution = new int[instance.getInstanceSize()];
    }

    public void solveWithMultiplier() {
        //for (int i = 0; i < MULTIPLIER; i++) {
            solve();
            //System.out.println();

        //}
    }


    public void solve() {
        final int instanceSize = instance.getInstanceSize();
        Population population = new Population(500,instance);
        long time = System.nanoTime();
        int i = 0;
        while (population.getGeneration() < 800) {
            //population.purgeAboveAverageFitness(population.getGeneration());
            population.bread();
        }
        System.out.println("Elapsed time of iterations: " +(System.nanoTime()-time)/1000000 + "ms");
        population.findSolution();
        System.out.println("Elapsed time to find solution: " +(System.nanoTime()-time)/1000000 + "ms");
        //population.generateSolution();
        //population.printSolution();
        this.solution = population.getPopulation().get(0).getGenotype();


    }

    public int[] getSolution() {
        return solution;
    }
}
