package com.epam.utils;

import java.util.*;

public class Place extends Node {

    private String description;
    private int tax;

    private static List<String> places = Arrays.asList("Pizza", "Cafe", "Cinema", "Spa", "Epam", "Home", "Opera");

    public Place() {
        int rand = (int) (Math.random() * places.size());
        this.description = places.get(rand);
        this.tax = (int) (Math.random() * 5);
    }

    public int getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return description;
    }
}
