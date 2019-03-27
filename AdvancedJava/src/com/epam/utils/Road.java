package com.epam.utils;

public class Road {

    private String name;
    private int length;
    private int cost;
    private String start;
    private String end;

    public Road(String start, String end, int length, int cost) {
        this.name = start.concat(end);
        this.start = start;
        this.end = end;
        this.length = length;
        this.cost = cost;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Road: " + name + ", length: " + length + ", cost: " + cost;
    }

    @Override
    public boolean equals(Object obj) {
        Road road = (Road) obj;
        return start.equals(road.start) && end.equals(road.end);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
