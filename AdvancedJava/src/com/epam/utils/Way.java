package com.epam.utils;

import com.epam.calculations.Calculator;

import java.util.*;

public class Way<T extends Node> implements Comparable<Way> {
    private List<T> routes;
    private int price;

    public Way(Collection<T> nodes) {
        routes = new ArrayList<>();
        routes.addAll(nodes);
        //calculatePrice();
    }

    public List<T> getRoutes() {
        return routes;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static <T extends Node> void calculatePrice(Way<T> way, Calculator<T> c) {
        int price = 0;

        List<T> routes = way.getRoutes();

        for (int i = 0; i < routes.size() - 1; i++) {
            T current = routes.get(i);
            T next = routes.get(i + 1);
            price += c.calculate(current, next);
        }

        way.setPrice(price);
    }

    @Override
    public String toString() {
        return routes.toString();
    }

    @Override
    public int compareTo(Way way) {
        return new Integer(price).compareTo(way.price);
    }
}
