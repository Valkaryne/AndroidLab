package com.epam;

import com.epam.api.*;
import com.epam.impl.SimpleNavigator;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        final GpsNavigator navigator = new SimpleNavigator();
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
        }
    }
}
