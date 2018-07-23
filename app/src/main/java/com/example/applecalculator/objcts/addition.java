package com.example.applecalculator.objcts;

/**
 * Created by Daniel on 2018-07-19.
 */

public class addition extends operations {

    @Override
    public double operate(double num1, double num2){
        return num1 + num2;
    }

    /*
    Brackets        4
    Exponents       3
    Division        2
    Multiplication  2
    Addition        1
    Subtraction     1
     */
    @Override
    public int get_priority() {
        return 0;
    }

    @Override
    public int get_num_operands() {
        return 2;
    }
}
