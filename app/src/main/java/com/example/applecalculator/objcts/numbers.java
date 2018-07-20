package com.example.applecalculator.objcts;

public class numbers extends token {

    double value;
    public numbers(double given_number){
        this.value = given_number;
    }

    @Override
    public String get_type() {
        return "number";
    }

    public double get_value() {
        return this.value;
    }
}
