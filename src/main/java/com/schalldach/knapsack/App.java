package com.schalldach.knapsack;

import com.schalldach.knapsack.util.Import;
import com.schalldach.knapsack.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        List<int[]> solution = new ArrayList<>();
        Import i = new Import();

        List<Instance> instances = i.getInstances();


        for (Instance instance : instances) {
            System.gc();
            Knapsack sack = new Knapsack(instance,1);
            Thread t = new Thread(sack);
            //run
            t.start();
           try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            solution.add(sack.getSolution());
            System.out.println(sack.getSolutionChrom());
        }
        verifySolution(solution, i);

    }

    private static void verifySolution(List<int[]> solution, Import i) {
        int tmp=0;
        for (int i1 = 0; i1 < solution.size(); i1++) {

            if (!Util.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                tmp++;
            }

        }
        /*for (int i1 = 0; i1 < solution.size(); i1++) {
            if (!Util.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                System.out.println(Arrays.toString(solution.get(i1))+"\tVS actual solution:\t"+Arrays.toString(i.getSolution().get(i1)));
            }
        }*/
        System.err.println(((double) tmp/solution.size()*100)+"% error rate");


    }
}
