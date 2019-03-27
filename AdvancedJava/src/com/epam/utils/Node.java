package com.epam.utils;

import java.util.*;

public class Node {

    private String name;
    private Set<Road> roads;
    private boolean start;
    private boolean end;

    public Node() {
        roads = new HashSet<>();
    }

    public Node(String name) {
        roads = new HashSet<>();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    public String getName() {
        return name;
    }

    public Set<Road> getRoads() {
        return roads;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        Node node = (Node) obj;
        return name.equals(node.name) && roads.equals(node.roads)
                && (start == node.start) && (end == node.end);
    }
}

