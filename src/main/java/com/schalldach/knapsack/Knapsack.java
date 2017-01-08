package com.schalldach.knapsack;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Knapsack implements Runnable{
    private final Instance instance;
    private int[] solution;
    private int maxCost;
    private int maxWeight;
    final private int MULTIPLIER;
    private int maxGenerations= 1000;
    private long finalTimeInMs = 0;
    private Chromosome solutionChrom;

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
        Population population = new Population(256*instanceSize,instance);
        long time = System.nanoTime();
        for (int i = 0; i < maxGenerations; i++) {
            population.bread();
            if (population.hasNoConvergence()) {
                //System.out.println(population);
                break;
            }
        }
        //System.out.println("final population of instance id: "+instance.getId());
        //System.out.println(population);
        finalTimeInMs = (System.nanoTime()-time)/1000000;
        //System.out.println("Elapsed time of iterations: " +(System.nanoTime()-time)/1000000 + "ms");
       // population.findBest();
        //System.out.println("Elapsed time to find solution: " +(System.nanoTime()-time)/1000000 + "ms");
        //population.generateSolution();
        //population.printSolution();
        this.solutionChrom = population.getBest();
        this.solution = solutionChrom.getGenotype();


    }

    public int[] getSolution() {
        return solution;
    }

    @Override
    public void run() {
        solve();
        System.gc();
    }

    public Chromosome getSolutionChrom() {
        return solutionChrom;
    }
}
