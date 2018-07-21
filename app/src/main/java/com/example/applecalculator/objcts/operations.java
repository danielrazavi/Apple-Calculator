package com.example.applecalculator.objcts;

public abstract class operations extends token {

    @Override
    public String get_type() {
        return "operation";
    }

    public abstract int get_priority();
}
