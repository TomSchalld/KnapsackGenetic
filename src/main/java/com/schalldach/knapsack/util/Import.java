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

    private List<Instance> instances;

    {
        instances = new LinkedList<>();
        createInstances(4);
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
                    System.out.println("File input completed;");
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

            i.setWeight(PrimitiveConvertor.integerListToArray(weight));
            i.setCost(PrimitiveConvertor.integerListToArray(cost));
            instances.add(i);
        }

    }


}
