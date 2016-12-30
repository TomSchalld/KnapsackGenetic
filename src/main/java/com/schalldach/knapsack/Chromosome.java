package com.schalldach.knapsack;

import com.schalldach.knapsack.util.PrimitiveHandler;
import com.schalldach.knapsack.util.Random;

import java.util.Arrays;

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
    private int generation = 0;
    private int mutationUpperBound = 5000 ; //std mutation probability 0.02% =5000
    private boolean mutated = false;


    private void evaluate() {
        int cost[] = instance.getCost();
        int weight[] = instance.getWeight();
        int maxSize = instance.getKnapsackSize();
        finalCost = 0;
        finalWeight = 0;
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

    public Chromosome() {
    }

    public Chromosome(Instance instance, int[] genotype) {
        this.instance = instance;
        this.genotype = genotype;
        evaluate();
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
            genotype[i] = Random.getInt(0, 2);
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
        evaluate();
    }

    @Override
    public String toString() {
        return "\nChromosome{" +
                "genotype=" + Arrays.toString(genotype) +
                ", fitness=" + fitness +
                ", finalCost=" + finalCost +
                ", finalWeight=" + finalWeight +
                ", dead=" + dead +
                ", mutated=" + mutated +
                "}";
    }

    public int getGeneration() {
        return generation;
    }

    public Chromosome[] twoPointCrossover(Chromosome other, int generation) {
        int genotypeChildOne[] = PrimitiveHandler.arrayCopy(this.genotype);
        int genotypeChildTwo[] = PrimitiveHandler.arrayCopy(other.genotype);
        Chromosome childOne = new Chromosome(instance, genotypeChildOne);
        Chromosome childTwo = new Chromosome(instance, genotypeChildTwo);
        childOne.setGeneration(generation);
        childTwo.setGeneration(generation);
        int crossoverPointOne = Random.getInt(0, genotype.length);
        int crossoverPointTwo = Random.getInt(0, genotype.length);
        while (crossoverPointTwo == crossoverPointOne) {
            crossoverPointTwo = Random.getInt(0, genotype.length);
        }
        if (crossoverPointOne > crossoverPointTwo) {
            int tmp = crossoverPointOne;
            crossoverPointOne = crossoverPointTwo;
            crossoverPointTwo = tmp;
        }
        /*System.out.println("Child one:\t" + childOne);
        System.out.println("Child two:\t" + childTwo);*/
        for (int i = crossoverPointOne; i <= crossoverPointTwo; i++) {
            PrimitiveHandler.swapArraysAtIndex(genotypeChildOne, genotypeChildTwo, i);
        }
        /*System.out.println("Child one:\t" + childOne);
        System.out.println("Child two:\t" + childTwo);*/
        if (Random.getInt(0, mutationUpperBound) == 1) {
            childOne.mutate();
        }
        if (Random.getInt(0, mutationUpperBound) == 1) {
            childTwo.mutate();
        }
        childOne.evaluate();
        childTwo.evaluate();
        return new Chromosome[]{childOne, childTwo};
    }

    public void mutate() {
        flipBit(Random.getInt(0, genotype.length));
        this.mutated = true;
        //System.err.println("MUTATION!!!");
    }

    public void flipBit(int index) {
        if (genotype[index] == 0) {
            genotype[index] = 1;
        } else {
            genotype[index] = 0;
        }
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
