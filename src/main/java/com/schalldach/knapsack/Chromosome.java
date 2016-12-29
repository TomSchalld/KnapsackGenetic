package com.schalldach.knapsack;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Chromosome {

    private Instance instance;
    private int[] genotype;
    private double fitness = 0;
    private int finalCost = 0;
    private int finalWeight = 0;
    private boolean dead = false;
    private int generation=0;


    private void evaluate() {
        int cost[] = instance.getCost();
        int weight[] = instance.getWeight();
        int maxSize = instance.getKnapsackSize();
        for (int i = 0; i < genotype.length; i++) {
            if (genotype[i] == 1) {
                finalCost += cost[i];
                finalWeight += weight[i];
                if (finalWeight > maxSize) {
                    dead = true;
                    break;
                }
            }
        }

    }


    public Chromosome(Instance instance) {
        this.instance = instance;
        this.genotype = createGenotype();
        evaluate();
    }

    private int[] createGenotype() {
        final int size = instance.getInstanceSize();
        int genotype[] = new int[size];
        for (int i = 0; i < size; i++) {
            genotype[i] = ThreadLocalRandom.current().nextInt(0, 2);
        }
        return genotype;
    }

    public boolean isDead() {
        return dead;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public int getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(int finalWeight) {
        this.finalWeight = finalWeight;
    }

    public int[] getGenotype() {
        return genotype;
    }

    public void setGenotype(int[] genotype) {
        this.genotype = genotype;
    }

    @Override
    public String toString() {
        return "\nChromosome{" +
                "genotype=" + Arrays.toString(genotype) +
                ", fitness=" + fitness +
                ", finalCost=" + finalCost +
                ", finalWeight=" + finalWeight +
                ", dead=" + dead +
                "}";
    }

    public int getGeneration() {
        return generation;
    }
}
