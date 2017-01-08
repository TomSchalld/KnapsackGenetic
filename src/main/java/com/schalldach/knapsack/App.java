package com.schalldach.knapsack;

import com.schalldach.knapsack.util.Import;
import com.schalldach.knapsack.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static List<Double> relerror = new LinkedList<>();
    public static void main(String[] args) {

        List<int[]> solution = new ArrayList<>();
        List<Integer> solCost = new ArrayList<>();
        List<Chromosome> solChrom = new ArrayList<>();
        Import i = new Import();

        List<Instance> instances = i.getInstances();


        for (Instance instance : instances) {
            System.gc();
            Knapsack sack = new Knapsack(instance, 1);
            Thread t = new Thread(sack);
            //run
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            solution.add(sack.getSolution());
            solCost.add(sack.getSolutionChrom().getFinalCost());
            solChrom.add(sack.getSolutionChrom());
        }
        verifySolution(solution, solCost,solChrom, i);

    }

    private static void verifySolution(List<int[]> solution, List<Integer> solCost, List<Chromosome> solChrom, Import i) {
        int tmp = 0;
        double relerr =0.0;
        for (int i1 = 0; i1 < solution.size(); i1++) {
            int[] solmeta = i.getSolutionMeta().get(i1);
            relerror.add(((double)solmeta[2]-(double) solChrom.get(i1).getFinalCost())/(double) solmeta[2]);
            relerr+=relerror.get(i1);
            System.out.println("Instance ID: "+solmeta[0]+" relative error= "+relerror.get(i1)+" algorithm stopped after generation: "+solChrom.get(i1).getGeneration()); //ε = ( C(OPT)-C(APX) ) / C(OPT)
            if (!Util.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                tmp++;
            }

        }
        /*for (int i1 = 0; i1 < solution.size(); i1++) {
            if (!Util.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                System.out.println(Arrays.toString(solution.get(i1))+"\tVS actual solution:\t"+Arrays.toString(i.getSolution().get(i1)));
            }
        }*/
        System.err.println(((double) tmp / solution.size() * 100) + "% error rate");
        System.err.println((relerr / relerror.size() ) + " rel error average per 50 instances");


    }
}
