package com.example.applecalculator;

/**
 * Created by Daniel on 2018-07-19.
 *
 * All operations will use this interface since they all have 1/2 inputs
 * and 1 output.
 */

public interface Operations_Interface {

    /**
     * This method is for the operations that need one parameter.
     * The operations that use two, will not use this, and if they do,
     * it will only return the num1 given.
     * @param num1
     * @return solution
     */
    double oprt1Prmtr(int num1);

    /**
     * This method is for the operations that need two parameters.
     * The operations that use one, will not use this, and if they do,
     * it will only return the num1 given.
     * @param num1
     * @param num2
     * @return solution
     */
    double oprt2Prmtr(int num1, int num2);

}
