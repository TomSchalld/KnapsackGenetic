package com.schalldach.knapsack;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Population {
    private List<Chromosome> population;
    private double averageCost = 0;
    private double averageWeight = 0;
    private int generation =0;
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
            averageFitness+=chromosome.getFitness();
        }
        averageFitness/=population.size();
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

    public void purgeDead(int generation) {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.isDead()&&chromosome.getGeneration()==generation) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);

    }
    public void purgeBelowAverageFitness(int generation) {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness()<averageFitness&&chromosome.getGeneration()==generation) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);

    }
    public void purgeAboveAverageFitness(int generation) {
        List<Chromosome> buffer = new LinkedList<>();
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness()>averageFitness&&chromosome.getGeneration()==generation) {
                buffer.add(chromosome);
            }
        }
        population.removeAll(buffer);
    }




    @Override
    public String toString() {
        return "Population{" +
                "population=" + population +
                ", \naverageCost=" + averageCost +
                ", averageWeight=" + averageWeight +
                '}';
    }
}
