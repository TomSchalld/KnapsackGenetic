package com.schalldach.knapsack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Population {
    private final int size;
    private List<Chromosome> population;
    private double averageCost = 0;
    private double averageWeight = 0;
    private int generation = 0;
    private double averageFitness;
    private Instance instance;

   /* public Population() {
        population = new LinkedList<>();
    }*/

    public Population(int size, Instance instance) {
        this.instance = instance;
        this.size = size;
        population = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            addChromosome(new Chromosome(instance));
        }
        evaluate();
        generation++;
    }

    public void evaluate() {
        averageEvaluation();
        setFitness();
    }

    private void averageEvaluation() {
        averageCost = 0;
        averageWeight = 0;
        for (Chromosome chromosome : population) {
            averageCost += chromosome.getFinalCost();
            averageWeight += chromosome.getFinalWeight();
        }
        averageCost /= population.size();
        averageWeight /= population.size();
    }

    private void setFitness() {
        averageFitness = 0;
        for (Chromosome chromosome : population) {
            chromosome.setFitness(chromosome.getFinalCost() / averageCost);
            averageFitness += chromosome.getFitness();
        }
        averageFitness /= population.size();
    }

    private void setOnNewPopFitness(List<Chromosome> newPop) {
        int averageCost = 0;
        for (Chromosome chromosome : newPop) {
            averageCost += chromosome.getFinalCost();
        }
        averageCost /= newPop.size();
        for (Chromosome chromosome : newPop) {
            chromosome.setFitness(chromosome.getFinalCost() / averageCost);
        }
    }

    public void addChromosome(Chromosome chromosome) {
        population.add(chromosome);
    }

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public void purgeDead() {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.isDead()) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);
        evaluate();
    }

    public void purgeBelowAverageFitness(int generation) {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() < averageFitness && chromosome.getGeneration() == generation) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);
        evaluate();
    }

    public void purgeAllBelowAverageFitness() {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() < averageFitness) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);
        evaluate();
    }

    public void purgeAboveAverageFitness(int generation) {
        System.out.println("generation " + generation + " purged");
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() > averageFitness && chromosome.getGeneration() == generation) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);
    }

    public void bread() {
        List<Chromosome> population = new ArrayList<>(this.population);
        List<Chromosome> newPopulation = new LinkedList<>();
        this.generation++;
        for (int i = 0, j = 1; i < population.size(); i++, j++) {
            if (j == population.size()) {
                j = 0;
            }
            newPopulation.addAll(population.get(i).twoPointCrossover(population.get(j), generation));
        }
        setOnNewPopFitness(newPopulation);
        replaceWeakest(newPopulation);
        evaluate();
        //purgeDead(this.generation);
        //purgeBelowAverageFitness(this.generation);
        //System.out.println(this.population.size());
    }

    public void replaceWeakest(List<Chromosome> nextGen) {
        this.purgeBelowAverageFitness(this.getGeneration());
        this.population.addAll(selectStrongest(nextGen));
        if (this.population.size() > size) {
            List<Chromosome> sublist = new ArrayList<>(this.population.subList(0, size));
            this.population.clear();
            this.population.addAll(sublist);
        }
    }

    public double getAverageFitnessOFlList(List<Chromosome> pop) {
        double av = 0.0;
        for (Chromosome chromosome : pop) {
            av += chromosome.getFitness();
        }
        av /= pop.size();
        return av;
    }

    public List<Chromosome> selectStrongest(List<Chromosome> population) {
        List<Chromosome> buffer = new LinkedList<>();
        double av = getAverageFitnessOFlList(population);
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() > av) {
                buffer.add(chromosome);
            }
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "Population{" +
                "population=" + population +
                ", \naverageCost=" + averageCost +
                ", averageWeight=" + averageWeight +
                '}';
    }

    public int getGeneration() {
        return generation;
    }

    public void findSolution() {
        this.purgeDead();
        int bestCost = 0;
        int bestWeight = 0;
        Chromosome bestSoFar=new Chromosome(instance);
        for (Chromosome chromosome : population) {
            if (chromosome.getFinalCost() > bestCost) {
                bestCost = chromosome.getFinalCost();
                bestWeight = chromosome.getFinalWeight();
                bestSoFar = chromosome;
            }
        }
        for (Chromosome chromosome : population) {
            if (chromosome.getFinalWeight() < bestWeight&&chromosome.getFinalCost() > bestCost) {
                bestCost = chromosome.getFinalCost();
                bestWeight = chromosome.getFinalWeight();
                bestSoFar = chromosome;
            }
        }
        population.add(0,bestSoFar);
        System.out.println(bestSoFar);
    }

    public void generateSolution() {
        this.purgeDead();
        this.evaluate();
        while (this.averageFitness != 1.0) {
            evaluate();
            purgeAllBelowAverageFitness();
        }
    }

    public void printSolution() {
        System.out.println("SOLUTION: " + population.get(0));
    }
}
