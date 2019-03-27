package com.epam.impl;

import com.epam.api.Path;
import com.epam.utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericNavigator {

    private final String ERROR_TAG = "ERROR: ";

    private List<Node> startPoints;
    private List<Node> endPoints;
    private List<Road> roads;

    private List<Way> possibleWays;
    private Node destination;

    public void readData(String filePath) {
        File roadMap = new File(filePath);

        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();
        roads = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(roadMap));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                Node startPoint = new Point(values[0]);
                Node endPoint = new Point(values[1]);
                startPoints.add(startPoint);
                endPoints.add(endPoint);
                roads.add(new Road(startPoint, endPoint, Integer.parseInt(values[2]), Integer.parseInt(values[3])));
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public Path findPath(Point pointA, Point pointB) {
        this.destination = pointB;
        possibleWays = new ArrayList<>();

        Stack<Point> initWay = new Stack<>();
        initWay.push(pointA);
        resolveWay(initWay);

        if (possibleWays.isEmpty()) {
            System.out.println("No ways!");
            return null;
        }

        return choosePath();
    }

    private void resolveWay(Stack<Point> way) {
        Point currentPoint = way.peek();

        int[] indices = IntStream.range(0, startPoints.size())
                .filter(i -> currentPoint.equals(startPoints.get(i)))
                .toArray();

        for (int index : indices) {
            Point nextPoint = (Point)endPoints.get(index);
            if (way.contains(nextPoint)) {
                continue;
            } else if (nextPoint.equals(destination)) {
                way.push(nextPoint);
                appendPossibleWay(way);
                way.pop();
                continue;
            } else {
                way.push(nextPoint);
                resolveWay(way);
            }
            way.pop();
        }
    }

    private void appendPossibleWay(Stack<Point> way) {
        possibleWays.add(new Way(way));
    }

    private Path choosePath() {
        Map<Way, Integer> routes = new LinkedHashMap<>();

        for (Way way : possibleWays) {
            List<Point> points = way.getWaySet();
            int cost = calculateCost(points);
            routes.put(way, cost);
        }

        routes = routes
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new)
                );

        List<Point> route = routes.entrySet().iterator().next().getKey().getWaySet();
        System.out.println(route + ", cost: " + calculateCost(route));
        Path path = null;
        return null;
    }

    private int calculateDistance(List<Point> points) {
        int distance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            Road road = new Road(points.get(i), points.get(i+1));
            for (Road r : roads) {
                if (r.equals(road)) {
                    distance += r.getLength();
                }
            }
        }
        return distance;
    }

    private int calculateCost(List<Point> route) {
        int cost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            Road road = new Road(route.get(i), route.get(i+1));
            for (Road r : roads) {
                if (r.equals(road)) {
                    cost += r.getLength() * r.getCost();
                }
            }
        }
        return cost;
    }

    /*
    Road road = new Road(new Point("A"), new Point("B"), 1, 1);

        for (Road r : roads) {
            if (r.equals(road)) {
                System.out.println(r.getName() + ", cost: " + r.getCost() + ", len: " + r.getLength());
                break;
            }
        }
     */
}
