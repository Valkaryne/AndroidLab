package com.epam.utils;

import java.util.*;

public class Way {
    private List<Point> waySet;

    public Way(Collection<Point> points) {
        waySet = new ArrayList<>();
        waySet.addAll(points);
    }

    public List<Point> getWaySet() {
        return waySet;
    }

    @Override
    public String toString() {
        return waySet.toString();
    }
}
