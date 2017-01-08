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
    private final int selectionPressure = 2;// increase to increase pressure
    private int generationsOfSame = 0;
    private boolean noConvergence = false;
    private Chromosome bestSoFar;

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
            if (chromosome.isDead()) {
                chromosome.setFitness(0);
            } else {
                chromosome.setFitness(chromosome.getFinalCost() / averageCost);
            }
            averageFitness += chromosome.getFitness();
        }
        averageFitness /= population.size();
    }

    private void setOnNewPopFitness(List<Chromosome> newPop) {
        double averageCost = 0;
        for (Chromosome chromosome : newPop) {
            averageCost += chromosome.getFinalCost();
        }
        averageCost /= newPop.size();
        for (Chromosome chromosome : newPop) {
            chromosome.setFitness(chromosome.getFinalCost() / averageCost);
        }
        //System.out.println(newPop);
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
    public List<Chromosome> createIntermediatePopulation(int popSize) {
       // System.out.println(popSize);
        //System.out.println(popSize/2);
        if (popSize > population.size()) {
            popSize=population.size();

        }
        List<Chromosome> oldPop = new ArrayList<>(population);
        oldPop.sort((o1, o2) -> ((int) (o1.getFitness() * 100000 - o2.getFitness() * 100000)));
        return oldPop.subList(popSize,population.size());
    }

    public void bread() {
        List<Chromosome> intermediate = createIntermediatePopulation(this.population.size()/this.selectionPressure);
        //System.out.println("intermediate.size "+intermediate.size());
        List<Chromosome> newPopulation = new LinkedList<>();
        this.generation++;
        for (int i = 0, j = 1; i < intermediate.size(); i++, j++) {
            if (j == intermediate.size()) {
                j = 0;
            }
            newPopulation.addAll(intermediate.get(i).twoPointCrossover(intermediate.get(j), generation));
        }
       // System.out.println("XXXXXXXXXXXXXXXXXX\taverage fitness:"+averageFitness);
        setOnNewPopFitness(newPopulation);
        replaceWeakest(newPopulation);
        evaluate();
        findBest();
        if (generationsOfSame > 30) {
            this.noConvergence=true;
            bestSoFar.setGeneration(this.generation);
        }

        //purgeDead(this.generation);
        //purgeBelowAverageFitness(this.generation);
        //System.out.println(this.population.size());
    }

    public void replaceWeakest(List<Chromosome> nextGen) {
        int sizeOfNextGen = nextGen.size();
        List<Chromosome> oldPop = new ArrayList<>(population);
        nextGen.sort((o1, o2) -> ((int) (o1.getFitness() * 100000 - o2.getFitness() * 100000)));
        oldPop.sort((o1, o2) -> ((int) (o1.getFitness() * 100000 - o2.getFitness() * 100000)));
        /*System.out.println("nextGen");
        System.out.println(nextGen);
        System.out.println("oldPop");
        System.out.println(oldPop);*/
        if (sizeOfNextGen % 2 == 0) {
            //System.out.println("Replacing the weak");
            population.removeAll(oldPop.subList(0,sizeOfNextGen/2));
            population.addAll(nextGen.subList(sizeOfNextGen/2,sizeOfNextGen));
            //System.out.println("size of pop: "+population.size());

        }
    }

    public double getAverageFitnessOFList(List<Chromosome> pop) {
        double av = 0.0;
        for (Chromosome chromosome : pop) {
            av += chromosome.getFitness();
        }
        av /= pop.size();
        return av;
    }

    public List<Chromosome> selectStrongest(List<Chromosome> population) {
        List<Chromosome> buffer = new LinkedList<>();
        double av = getAverageFitnessOFList(population);
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

    public void findBest() {
        Chromosome oldBest = bestSoFar;

        int bestCost = 0;
        int bestWeight = 0;
        this.bestSoFar=new Chromosome(instance);
        for (Chromosome chromosome : population) {
            if (chromosome.getFinalCost() > bestCost && !chromosome.isDead()) {
                bestCost = chromosome.getFinalCost();
                bestWeight = chromosome.getFinalWeight();
                bestSoFar = chromosome;
            }
        }
        for (Chromosome chromosome : population) {
            if (chromosome.getFinalWeight() < bestWeight&&chromosome.getFinalCost() > bestCost && !chromosome.isDead()) {
                bestCost = chromosome.getFinalCost();
                bestWeight = chromosome.getFinalWeight();
                bestSoFar = chromosome;
            }
        }
        if (oldBest != null) {
            if (oldBest.equals(bestSoFar)) {
                this.generationsOfSame++;
            }
        }
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

    public double getAverageFitness() {
        return averageFitness;
    }

    public boolean hasNoConvergence() {
        return noConvergence;
    }

    public Chromosome getBest() {
        return bestSoFar;
    }
}
