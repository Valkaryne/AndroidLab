package com.epam.utils;

import java.util.Arrays;
import java.util.List;

public class Place extends Node {

    private String description;

    private static List<String> places = Arrays.asList("Pizza", "Cafe", "Cinema", "Spa", "Epam", "Home", "Opera");

    public Place() {
        int rand = (int) (Math.random() * places.size());
        this.description = places.get(rand);
    }

    @Override
    public String toString() {
        return description;
    }
}
