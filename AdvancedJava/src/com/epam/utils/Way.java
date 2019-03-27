package com.epam.utils;

import java.util.*;

public class Way implements Comparable<Way> {
    private List<Node> ways;
    private int price;

    public Way(Collection<Node> nodes) {
        ways = new ArrayList<>();
        ways.addAll(nodes);
        calculatePrice();
    }

    public List<Node> getWays() {
        return ways;
    }

    public int getPrice() {
        return price;
    }

    private void calculatePrice() {
        int price = 0;

        for (int i = 0; i < ways.size() - 1; i++) {
            Node current = ways.get(i);
            Node next = ways.get(i + 1);
            Set<Road> roadSet = current.getRoads();
            Road road;
            for (Road r : roadSet) {
                if (r.getStart().equals(current.getName()) && r.getEnd().equals(next.getName())) {
                    road = r;
                    price += road.getCost() * road.getLength();
                    break;
                }
            }
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return ways.toString();
    }

    @Override
    public int compareTo(Way way) {
        return new Integer(price).compareTo(way.price);
    }
}
