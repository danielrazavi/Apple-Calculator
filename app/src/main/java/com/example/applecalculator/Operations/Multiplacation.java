package com.example.applecalculator.Operations;

/**
 * Created by Daniel on 2018-07-19.
 */

public class Multiplacation implements Operations_Interface {

    @Override
    public double oprt1Prmtr(int num1) {
        return num1;
    }

    @Override
    public double oprt2Prmtr(int num1, int num2) {
        return num1 * num2;
    }
}
