package com.epam.impl;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;


public class SimpleNavigator implements GpsNavigator {

    private final String ERROR_TAG = "ERROR: ";

    private List<String> startPoints;
    private List<String> endPoints;
    private Map<String, Integer> lengths;
    private Map<String, Integer> costs;

    private List<StringBuilder> possibleWays;
    private String destination;

    @Override
    public void readData(String filePath) throws RuntimeException {
        File roadMap = new File(filePath);

        startPoints = new ArrayList<>();
        endPoints = new ArrayList<>();
        lengths = new HashMap<>();
        costs = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(roadMap));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                startPoints.add(values[0]);
                endPoints.add(values[1]);
                lengths.put(values[0].concat(values[1]), Integer.parseInt(values[2]));
                costs.put(values[0].concat(values[1]), Integer.parseInt(values[3]));
            }
            reader.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Default file couldn't be found. Please set the path manually");
            loadFile();
        } catch (IOException ioe) {
            throw new RuntimeException(ERROR_TAG + "Couldn't read the roads file");
        } catch (NumberFormatException nfe) {
            throw new RuntimeException(ERROR_TAG + "File data is incorrect. Please check your input file.");
        }
    }

    @Override
    public Path findPath(String pointA, String pointB) throws RuntimeException {
        if (pointA.equals(pointB)) {
            throw new RuntimeException(ERROR_TAG + "Start Point and End Point are equal. Please, rethink your request");
        }
        if (!startPoints.contains(pointA)) {
            throw new RuntimeException(ERROR_TAG + "Point " + pointA
                    + " can't be a start point. Please, rethink your request");
        }
        if (!endPoints.contains(pointB)) {
            throw new RuntimeException(ERROR_TAG + "Point " + pointB
                    + " can't be an end point. Please, rethink your request");
        }

        this.destination = pointB;
        possibleWays = new ArrayList<>();

        Stack<String> initWay = new Stack<>();
        initWay.push(pointA);
        resolveWay(initWay);

        if (possibleWays.isEmpty())
            throw new RuntimeException(ERROR_TAG + "There is no path from " + pointA + " to "
                    + pointB + ". Please, rethink your request");

        return choosePath();
    }

    /**
     * Allows user to find file with road manually
     * if it couldn't be found by default
     */
    private void loadFile() throws RuntimeException {
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                else
                    return f.getName().toLowerCase().endsWith(".ext");
            }

            @Override
            public String getDescription() {
                return "Road Network File (*.ext)";
            }
        });
        JFrame loadingFrame = new JFrame();
        fileOpen.showOpenDialog(loadingFrame);
        if (fileOpen.getSelectedFile() == null) {
            loadingFrame.dispose();
            throw new RuntimeException(ERROR_TAG + "Couldn't open the file");
        } else {
            String filePath = fileOpen.getSelectedFile().toString();
            loadingFrame.dispose();
            readData(filePath); // try to read data from file again
        }
    }

    /**
     * Method finds possible paths from pointA to pointB
     * @param way Current way chain from the start point
     */
    private void resolveWay(Stack<String> way) {
        // We make ways from the last point. Initially it is the first point
        String currentPoint = way.peek();

        // Find indices of point where we can go to from the current
        int[] indices = IntStream.range(0, startPoints.size())
                .filter(i -> currentPoint.equals(startPoints.get(i)))
                .toArray();

        for (int index : indices) {
            String nextPoint = endPoints.get(index);
            if (way.contains(nextPoint)) {
                // we don't want to move in circles so repeated point should not be added to path
                continue;
            } else if (nextPoint.equals(destination)) {
                // we add destination point to path, write it into array of possible ways
                // and pop to review other options
                way.push(nextPoint);
                appendPossibleWay(way);
                way.pop();
                continue;
            } else {
                // we add new point to path and start to make ways from it
                way.push(nextPoint);
                resolveWay(way);
            }
            // all options for this chain were reviewed so we pop odd points
            way.pop();
        }
    }

    /**
     * Write way into array of possible ways
     * @param way Full way from start point to end point
     */
    private void appendPossibleWay(Stack<String> way) {
        StringBuilder builder = new StringBuilder();
        for (String s : way) {
            builder.append(s + " ");
        }
        possibleWays.add(builder);
    }

    /**
     * Chooses path with the minimal distance between edge points
     * @return object, describes appropriate path
     */
    private Path choosePath() {
        Map<String, Integer> routes = new LinkedHashMap<>();

        for (StringBuilder way : possibleWays) {
            List<String> points = Arrays.asList(way.toString().trim().split(" "));
            int distance = calculateDistance(points);
            routes.put(way.toString().trim(), distance);
        }

        /* Sort map by values in ascending order */
        routes = routes
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new)
                );

        Path path = null;
        if (hasAlternatives(routes)) {
            System.out.println("There are more than one ways with minimal distance");
            path = chooseAlternative(routes);
        } else {
            List<String> route = Arrays.asList(
                    (routes.entrySet().iterator().next().getKey().split(" "))
            );
            int cost = calculateCost(route);
            path = new Path(route, cost);
        }

        return path;
    }

    /**
     * @param points Points of the path
     * @return integer value means length of the path
     */
    private int calculateDistance(List<String> points) {
        int distance = 0;
        for (int i = 0; i < points.size() - 1; i++)  {
            distance += lengths.get(points.get(i).concat(points.get(i+1)));
        }
        return distance;
    }

    /**
     * @param route Points of the path
     * @return integer value means total cost of roads of the path
     */
    private int calculateCost(List<String> route) {
        int cost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            /**
             * ver. 0.2: according to the document, cost should be multiplied by road length
             */
            String road = route.get(i).concat(route.get(i+1));
            cost += (costs.get(road) * lengths.get(road));
        }
        return cost;
    }

    /**
     * @param routes Map of routes containing path points and distance and sorted in ascending order
     * @return true if there are more than one path with the minimal distance and false else
     */
    private boolean hasAlternatives(Map<String, Integer> routes) {
        Set<Map.Entry<String, Integer>> entries = routes.entrySet();
        Iterator iter = entries.iterator();
        Map.Entry<String, Integer> e1 = (Map.Entry<String, Integer>)iter.next();
        if (iter.hasNext()) {
            Map.Entry<String, Integer> e2 = (Map.Entry<String, Integer>)iter.next();
            return e1.getValue().equals(e2.getValue());
        }
        else
            return false;
    }

    /**
     * @param routes Map of routes containing path points and distance and sorted in ascending order
     * @return object, describes appropriate path chosen by user
     */
    private Path chooseAlternative(Map<String, Integer> routes) {
        int minDistance = routes.entrySet().iterator().next().getValue();
        List<String> alternatives = new ArrayList<>();
        List<Integer> alternativesCosts = new ArrayList<>();

        int counter = 0;
        // Which paths have the same minimal distance
        for (Map.Entry<String, Integer> entry : routes.entrySet()) {
            if (entry.getValue().equals(minDistance)) {
                alternatives.add(entry.getKey());
                List<String> route = Arrays.asList(
                        (entry.getKey().split(" ")));
                int cost = calculateCost(route);
                alternativesCosts.add(cost);
                System.out.println(++counter + ": " + entry.getKey()
                        + ", cost: " + cost);
            }
        }

        System.out.print("Please, choose the alternative you prefer the most: ");

        int no = readOptionNo(alternatives.size());

        return new Path(Arrays.asList(alternatives.get(no).split(" ")),
                alternativesCosts.get(no));
    }

    /**
     * Allows user to choose alternative path
     * @param altNumber natural number of alternatives
     * @return integer value means number of the preferable path
     *
     * ver 0.2: We should preserve user from incorrect input
     */
    private int readOptionNo(int altNumber) {
        Scanner scanner = new Scanner(System.in);
        int no = 0;
        boolean incorrectInput = false;

        do {
            try {
                no = scanner.nextInt();
                if (no > 0 && no <= altNumber)
                    incorrectInput = false;
                else {
                    System.out.print("Value should be natural and less than " + altNumber + " or equal: ");
                    incorrectInput = true;
                }
            } catch (InputMismatchException ex) {
                System.out.print("Incorrect value. Integer is required: ");
                incorrectInput = true;
                scanner.nextLine();
            }
        } while (incorrectInput);

        scanner.close();
        return no-1;
    }
}
