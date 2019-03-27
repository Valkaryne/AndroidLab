package com.epam.strategies;

public class StandardCalculationStrategy implements CalculationStrategyInterface {
    @Override
    public int calculate(int length, int cost) {
        return length * cost;
    }
}
