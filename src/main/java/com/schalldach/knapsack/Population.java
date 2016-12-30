package com.schalldach.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Population {
    private List<Chromosome> population;
    private double averageCost = 0;
    private double averageWeight = 0;
    private int generation = 0;
    private double averageFitness;

    public Population() {
        population = new LinkedList<>();
    }

    public Population(int size, Instance instance) {
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

    public void purgeAboveAverageFitness(int generation) {
        System.out.println("generation "+generation+" purged");
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
            newPopulation.addAll(Arrays.asList(population.get(i).twoPointCrossover(population.get(j),generation)));
        }

        replaceWeakest(newPopulation);
        evaluate();
        //purgeDead(this.generation);
        //purgeBelowAverageFitness(this.generation);
        System.out.println(this.population.size());
    }

    public void replaceWeakest(List<Chromosome> nextGen) {
        this.purgeBelowAverageFitness(this.getGeneration());
        this.population.addAll(selectStrongest(nextGen));
        if (this.population.size() > 5000) {
            List<Chromosome> sublist = this.population.subList(0,5000);
            this.population.clear();
            this.population.addAll(sublist);
        }
    }

    public List<Chromosome> selectStrongest(List<Chromosome> population) {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() > averageFitness) {
                buffer.add(chromosome);
            }
        }
        double tempAvFitness = 0.0;
        for (Chromosome chromosome : buffer) {
            tempAvFitness+=chromosome.getFitness();
        }
        final double temp = tempAvFitness/buffer.size();

        return buffer.stream().filter(chromosome -> chromosome.getFitness()>temp).collect(Collectors.toList());
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
}
