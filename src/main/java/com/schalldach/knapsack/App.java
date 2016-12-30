package com.schalldach.knapsack;

import com.schalldach.knapsack.util.Import;
import com.schalldach.knapsack.util.PrimitiveHandler;

import java.util.ArrayList;
import java.util.Arrays;
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
            Knapsack sack = new Knapsack(instance,1);
            sack.solveWithMultiplier();
            solution.add(sack.getSolution());

        }
        verifySolution(solution, i);
        /*int x; //mutation probability 0.02%
        for (int j = 0; j < 10000; j++) {
            x = Random.getInt(0,5000);
            if (x==1) {
                System.err.println(x);
            }
        }*/

        /*TestMemory.test();
        System.gc();
        TestMemory.test();*/
    }

    private static void verifySolution(List<int[]> solution, Import i) {
        int tmp=0;
        for (int i1 = 0; i1 < solution.size(); i1++) {

            if (!PrimitiveHandler.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                tmp++;
                //System.out.println("Solution: " + i1 + " is valid: " + false);
            } else {
                //System.out.println("Solution: "+i1+" is valid: "+ true);
            }

        }
        System.err.println(((double) tmp/solution.size()*100)+"% error rate");
        for (int i1 = 0; i1 < solution.size(); i1++) {
            if (!PrimitiveHandler.arrayEquals(solution.get(i1), i.getSolution().get(i1))) {
                System.out.println(Arrays.toString(solution.get(i1))+"\tVS actual solution:\t"+Arrays.toString(i.getSolution().get(i1)));
            }
        }


    }
}
