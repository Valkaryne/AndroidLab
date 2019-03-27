package com.epam.utils;

public class Road {

    private String name;
    private int length;
    private int cost;
    private Node start;
    private Node end;

    public Road(Node start, Node end, int length, int cost) {
        this.name = start.getName().concat(end.getName());
        this.start = start;
        this.end = end;
        this.length = length;
        this.cost = cost;
    }

    public Road(Node start, Node end) {
        this.name = start.getName().concat(end.getName());
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        Road road = (Road) obj;
        return name.equals(road.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
