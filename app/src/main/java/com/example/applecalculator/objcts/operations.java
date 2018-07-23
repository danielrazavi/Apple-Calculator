package com.example.applecalculator.objcts;

public abstract class operations extends token {

    @Override
    public String get_type() {
        return "operation";
    }

    public abstract int get_priority();

    public abstract int get_num_operands();

    public abstract double operate(double num1, double num2);
}
