package com.epam.strategies;

public class CheapCalculationStrategy implements CalculationStrategyInterface {

    @Override
    public int calculate(int length, int cost) {
        return cost;
    }
}
