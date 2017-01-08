package com.schalldach.knapsack.util;

import com.schalldach.knapsack.Instance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by @author Thomas Schalldach on 29/12/2016 software@thomas-schalldach.de.
 */
public class Import {
    private final int size = 40;
    private List<Instance> instances;
    private List<int[]>solution;

    {
        instances = new LinkedList<>();
        solution = new LinkedList<>();
        createInstances(size);
        importSolution(size);
    }

    private void importSolution(int size) {
        int sol[];
        InputStream input = this.getClass().getResourceAsStream("/solution/knap_" + size + ".sol.dat");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String line="";
        try {
            while (true) {
                sol = new int[size];
                line = buffer.readLine();
                if (line != null) {
                    final String instanceSplit[] =  line.split(" ");
                    final int length = instanceSplit.length;
                    if (length > 0) {

                        for (int j = 4;j < length; j++) {
                            sol[j-4]= Integer.parseInt(instanceSplit[j].trim());
                        }
                        solution.add(sol);
                    }
                } else {
                    //System.out.println("File input completed;");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<Instance> getInstances() {
        return instances;
    }

    private List<Instance> createInstances(int size) {

        InputStream input = this.getClass().getResourceAsStream("/instances/knap_" + size + ".inst.dat");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String line="";
        try {
            while (true) {
                line = buffer.readLine();
                if (line != null) {
                    createAndAddInstance(line);
                } else {
                    //System.out.println("File input completed;");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<Instance>();
    }

    private void createAndAddInstance(String line) throws NullPointerException{
        final String instanceSplit[] =  line.split(" ");
        final int length = instanceSplit.length;
        if (length > 0) {
            Instance i = new Instance();
            List<Integer> cost = new LinkedList<>();
            List<Integer> weight = new LinkedList<>();
            i.setId(Integer.parseInt(instanceSplit[0].trim()));
            i.setInstanceSize(Integer.parseInt(instanceSplit[1].trim()));
            i.setKnapsackSize(Integer.parseInt(instanceSplit[2].trim()));
            for (int j = 3, k=4; k < length; j+=2,k+=2) {
                weight.add(Integer.valueOf(instanceSplit[j].trim()));
                cost.add(Integer.valueOf(instanceSplit[k].trim()));
            }

            i.setWeight(Util.integerListToArray(weight));
            i.setCost(Util.integerListToArray(cost));
            instances.add(i);
        }

    }

    public List<int[]> getSolution() {
        return solution;
    }
}
