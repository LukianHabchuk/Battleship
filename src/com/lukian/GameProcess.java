package com.lukian;

import com.lukian.entity.Boat;
import com.lukian.entity.Player;
import com.lukian.entity.Point;

import java.util.ArrayList;
import java.util.Scanner;

public class GameProcess {

    private ArrayList<Player> players;
    private Map m;
    private Scanner scanner = new Scanner(System.in);

    public GameProcess() {
        players = new ArrayList<Player>();
        m = new Map();
        Scanner sc = new Scanner(System.in);
        System.out.println("choose name for player 1");
        players.add(new Player(1, sc.nextLine()));
        System.out.println("choose name for player 2");
        players.add(new Player(2, sc.nextLine()));
    }

    public void play() {
//        for (int i = 0; i < players.size(); i++) {
//            System.out.println("set the boats for player " + i);
        setTheBoats(0);
//        }

        m.draw();

        while (!m.isGameOver()) {
            m.hit(new Point(scanner.nextInt(), scanner.nextInt()));
        }
        System.out.println("GG");
    }

    private void setTheBoats(int playerIndex) {
        Point begin, end;
        Boat b;

        System.out.println("1 ship - a row of 4 cells (\"four-deck\"; battleship)\n"
                + "2 ships - a row of 3 cells (\"three-deck\"; cruisers)\n"
                + "3 ships - a row of 2 cells (\"double-deck\"; destroyers)\n"
                + "4 ships - 1 cell (\"single-deck\"; torpedo boats)");

//            1 1 1 1
//            2 2 2
//            3 3
//            4
        for (int i = 1; i <= 4; i++) {
            System.out.println("please create " + (5-i) + " boat/s of " + i + " cell/s");
            for (int j = 1; j <= 5-i; j++) {
                System.out.println("Boat # " + j + " with " + i + " cells");
                m.draw();

                System.out.println("begin X and Y: ");
                begin = new Point(scanner.nextInt(), scanner.nextInt());

                //check is it 1-cell boat
                if (i == 1) {
                    end = begin;
                    b = new Boat(begin, end);
                    if (!m.generateTheBoat(b)) {
                        System.out.println("wrong boat generation! Please try again!");
                        j--;
                    } else {
                        players.get(playerIndex).addBoat(b);
                    }
                }
                //else it is many cells boat
                else {
                    System.out.println("end X and Y: ");
                    end = new Point(scanner.nextInt(), scanner.nextInt());
                    b = new Boat(begin, end);
                    if (!isSizeOk(b, i)) {
                        System.out.println("wrong boat size! Please try again");
                        j--;
                    }
                    else if (!m.generateTheBoat(b)) {
                        System.out.println("wrong boat generation! Please try again!");
                        j--;
                    } else {
                        players.get(playerIndex).addBoat(b);
                    }
                }

            }
        }
//        m.generateTheBoat(new Boat(new Point(2, 3), new Point(2, 4)));
    }

    //checking boat size
    private boolean isSizeOk(Boat boat, int size) {
        return boat.getBegin().getX() != boat.getEnd().getX() ? boat.getEnd().getX()-boat.getBegin().getX()+1 == size
                : boat.getEnd().getY()-boat.getBegin().getY()+1 == size;
    }

}
