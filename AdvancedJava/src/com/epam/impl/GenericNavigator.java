package com.epam.impl;

import com.epam.api.*;
import com.epam.strategies.*;
import com.epam.utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class GenericNavigator<T extends Node> implements GpsNavigator {

    private final String ERROR_TAG = "ERROR: ";
    private Class<T> tClass;

    private final Map<String, T> nodes = new HashMap<>();
    private final List<Way> possibleWays = new ArrayList<>();

    public GenericNavigator(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public void readData(String filePath) {
        File roadMap = new File(filePath);

        List<String> startPoints = new ArrayList<>();
        List<String> endPoints = new ArrayList<>();
        List<Integer> lengths = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(roadMap));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                startPoints.add(values[0]);
                endPoints.add(values[1]);
                lengths.add(Integer.parseInt(values[2]));
                costs.add(Integer.parseInt(values[3]));
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.getMessage();
        }

        try {
            for (String pointName : startPoints) {
                T node;
                if (nodes.containsKey(pointName)) {
                    node = nodes.get(pointName);
                    nodes.remove(pointName);
                }
                else {
                    node = genericFactoryMethod();
                    node.setName(pointName);
                }

                node.setStart(true);
                if (endPoints.contains(pointName))
                    node.setEnd(true);

                int[] indices = IntStream.range(0, startPoints.size())
                        .filter(i -> pointName.equals(startPoints.get(i)))
                        .toArray();
                for (int index : indices) {
                    Road road = new Road(pointName, endPoints.get(index), lengths.get(index), costs.get(index));
                    node.addRoad(road);
                }

                nodes.put(pointName, node);
            }

            for (String pointName : endPoints) {
                T node;
                if (nodes.containsKey(pointName)) {
                    continue;
                } else {
                    node = genericFactoryMethod();
                    node.setName(pointName);
                }


                node.setStart(false);
                node.setEnd(true);

                nodes.put(pointName, node);
            }
        } catch (ReflectiveOperationException roe) {
            roe.getMessage();
        }

    }

    @Override
    public Path findPath(String pointA, String pointB) {
        Node initial = nodes.get(pointA);
        Node destination = nodes.get(pointB);

        Stack<Node> initWay = new Stack<>();
        initWay.push(initial);
        resolveWay(initWay, destination);

        Way way = Collections.min(possibleWays);
        System.out.println(way + ", price: " + way.getPrice());

        return new Path(way.getWays(), way.getPrice());
    }

    private void resolveWay(Stack<Node> way, final Node dst) {
        //We make ways from the last point. Initially it is the first point
        Node currentNode = way.peek();

        //Find roads where we can go to from the current node
        Set<Road> roads = currentNode.getRoads();

        for (Road road : roads) {
            Node nextNode = nodes.get(road.getEnd());

            if (way.contains(nextNode)) {
                // we don't want to move in circles so repeated point should not be added to path
                continue;
            } else if (nextNode.equals(dst)) {
                // we add destination node to path, write it into array of possible ways
                // and pop to review other options
                way.push(nextNode);
                possibleWays.add(new Way(way, new CheapCalculationStrategy()));
                way.pop();
                continue;
            } else {
                // we add new node to path and start to make ways from it
                way.push(nextNode);
                resolveWay(way, dst);
            }
            // all options for this chain were reviewed so we pop odd nodes
            way.pop();
        }
    }

    private T genericFactoryMethod() throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }
}

