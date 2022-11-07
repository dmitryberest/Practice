package com.company;

import java.io.IOException;

public class MyClass {
    public static void main(String[] args) {

        final int n = 2;                        //Кол-во матриц

        Matrix[] matrices = new Matrix[n];

        double firstNormMin = Double.MAX_VALUE,secondNormMin = Double.MAX_VALUE;


        for (int i = 0; i < matrices.length; i++) {
            try{
                matrices[i] = new Matrix(+i + ".txt");

                double bufFirst = matrices[i].getFirstNorm(),
                        bufSecond = matrices[i].Transpose().getFirstNorm();



                if (firstNormMin>=bufFirst) firstNormMin = bufFirst;
                if (secondNormMin>=bufSecond) secondNormMin = bufSecond;

                System.out.println("\nMatrix["+i+"]\n"+matrices[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("First norms " + firstNormMin);
        System.out.println("Second norms " + secondNormMin);


        System.out.println("\nMatrix[0]*Matrix[1]\n"+ matrices[0].Multiply(matrices[1]));
        System.out.println("\nMatrix[0]+Matrix[1]\n"+matrices[0].Plus(matrices[1]));
        System.out.println("\nMatrix[0]-Matrix[1]\n"+matrices[0].Minus(matrices[1]));

    }
}