package com.lukian;

import com.lukian.entity.Boat;
import com.lukian.entity.Point;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map map = new Map();
        map.draw();
        map.generateTheBoat(new Boat(new Point(1,2), new Point(1, 3)));
        map.draw();
        Scanner scanner = new Scanner(System.in);
        while (!map.isGameOver()) {
            map.hit(new Point(scanner.nextInt(), scanner.nextInt()));
        }
        System.out.println("GG");
    }
}
