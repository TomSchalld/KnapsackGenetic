package com.schalldach.knapsack;

import java.util.LinkedList;
import java.util.List;

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
        List<Chromosome> initialPopulation = generateInitialPopulation(instanceSize);
        // evaluate fitness
        initialPopulation.forEach(System.out::println);




    }
    private List<Chromosome> generateInitialPopulation(int instanceSize) {
        final int populationSize = 5;
        List<Chromosome> population = new LinkedList<>();
        //generate initial population
        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome(instance));
        }
        return population;
    }
}
