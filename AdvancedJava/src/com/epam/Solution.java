package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.impl.GenericNavigator;
import com.epam.utils.Node;
import com.epam.utils.Place;

public class Solution {

    public static void main(String[] args) {

        GpsNavigator navigator = new GenericNavigator(Place.class);
        String roadMap = "D:\\Gps\\road_map.ext";
        navigator.readData(roadMap);
        final Path path = navigator.findPath("A", "C");

        System.out.println(path);
        /*final GpsNavigator navigator = new SimpleNavigator();
        String roadMap = "D:\\Gps\\road_map_.ext";

        try {
            navigator.readData(roadMap);
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Please, enter a start point: ");
            String start = scanner.nextLine();

            System.out.print("Please, enter an end point: ");
            String end = scanner.nextLine();

            final Path path = navigator.findPath(start, end);
            System.out.println(path);
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        } finally {
            scanner.close();
        } */
    }
}
