package com.epam.strategies;

public class ExpensiveCalculationStrategy implements CalculationStrategyInterface {
    @Override
    public int calculate(int length, int cost) {
        return length * cost^2;
    }
}
