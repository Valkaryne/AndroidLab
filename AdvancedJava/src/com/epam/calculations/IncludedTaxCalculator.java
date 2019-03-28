package com.epam.calculations;

import com.epam.utils.*;

import java.util.Set;

public class IncludedTaxCalculator implements Calculator<Place> {
    @Override
    public int calculate(Place n1, Place n2) {
        Set<Road> roadSet = n1.getRoads();
        for (Road r: roadSet) {
            if (r.getStart().equals(n1.getName()) && r.getEnd().equals(n2.getName())) {
                return r.getLength() * r.getCost() + n2.getTax();
            }
        }
        return 0;
    }
}
