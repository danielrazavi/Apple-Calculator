package com.example.applecalculator.objcts;

public class addition extends operations {


    public double operate(int num1, int num2){
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
}
