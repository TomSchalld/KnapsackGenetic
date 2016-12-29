package com.schalldach.knapsack;

import java.util.Arrays;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Instance {
    private int cost[];
    private int weight[];
    private int instanceSize;
    private int knapsackSize;
    private int id;





    public int[] getCost() {
        return cost;
    }

    public void setCost(int[] cost) {
        this.cost = cost;
    }

    public int[] getWeight() {
        return weight;
    }

    public void setWeight(int[] weight) {
        this.weight = weight;
    }

    public int getInstanceSize() {
        return instanceSize;
    }

    public void setInstanceSize(int instanceSize) {
        this.instanceSize = instanceSize;
    }

    public int getKnapsackSize() {
        return knapsackSize;
    }

    public void setKnapsackSize(int knapsackSize) {
        this.knapsackSize = knapsackSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "cost=" + Arrays.toString(cost) +
                ", weight=" + Arrays.toString(weight) +
                ", instanceSize=" + instanceSize +
                ", knapsackSize=" + knapsackSize +
                ", id=" + id +
                '}';
    }
}
