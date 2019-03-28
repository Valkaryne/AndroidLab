package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.calculations.*;
import com.epam.impl.GenericNavigator;
import com.epam.utils.Node;
import com.epam.utils.Place;
import com.epam.utils.Road;

import java.util.Set;

public class Solution {

    public static void main(String[] args) {

        GpsNavigator navigator = new GenericNavigator<Node>(Node.class, (n1, n2) -> {
            Set<Road> roadSet = n1.getRoads();
            for (Road r: roadSet) {
                if (r.getStart().equals(n1.getName()) && r.getEnd().equals(n2.getName())) {
                    return r.getLength() * r.getCost();
                }
            }
            return 0;
        });
        String roadMap = "D:\\Gps\\road_map.ext";
        navigator.readData(roadMap);
        final Path path = navigator.findPath("F", "B");

        System.out.println("Standard generic calculation: " + path);

        GpsNavigator navigator2 = new GenericNavigator<>(Place.class, new IncludedTaxCalculator());
        navigator2.readData(roadMap);
        final Path path2 = navigator2.findPath("A", "C");

        System.out.println("Included tax calculation: " + path2);
    }
}
