package com.lukian;

import com.lukian.entity.Boat;
import com.lukian.entity.Player;
import com.lukian.entity.Point;

import java.util.ArrayList;
import java.util.Scanner;

public class GameProcess {

    private Scanner scanner = new Scanner(System.in);
    private int currentPlayerIndex;
    private boolean wasHit;

    private ArrayList<Player> players;

    public GameProcess() {
        players = new ArrayList<>();

        System.out.println("choose name for player 1");
        players.add(new Player(0, scanner.nextLine()));
        System.out.println("choose name for player 2");
        players.add(new Player(1, scanner.nextLine()));
    }

    public void play() {

        for (int i = 0; i < players.size(); i++) {
            System.out.println("set the boats for player " + players.get(i).getName());
            currentPlayerIndex = i;
            setTheBoats();
        }

        currentPlayerIndex = players.get(0).getId();
        wasHit = false;

        while (!players.get(0).getMap().isGameOver() || players.get(1).getMap().isGameOver()) {
            damagingProcess();
        }
        System.out.println("GG");
    }

    private void setTheBoats() {
        Point begin;
        Point end;
        Boat boat;

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
                players.get(currentPlayerIndex).getMap().draw();

                System.out.println("begin X and Y: ");
                begin = new Point(scanner.nextInt(), scanner.nextInt());

                //check is it 1-cell boat
                if (i == 1) {
                    end = begin;
                    boat = new Boat(begin, end);
                    if (!players.get(currentPlayerIndex).getMap().generateTheBoat(boat)) {
                        System.out.println("wrong boat generation! Please try again!");
                        j--;
                    }
                }
                //else it is many cells boat
                else {
                    System.out.println("end X and Y: ");
                    end = new Point(scanner.nextInt(), scanner.nextInt());
                    boat = new Boat(begin, end);
                    if (!isSizeOk(boat, i)) {
                        System.out.println("wrong boat size! Please try again");
                        j--;
                    }
                    else if (!players.get(currentPlayerIndex).getMap().generateTheBoat(boat)) {
                        System.out.println("wrong boat generation! Please try again!");
                        j--;
                    }
                }

            }
        }
//        players.get(currentPlayerIndex).getMap().generateTheBoat(new Boat(new Point(2+2*currentPlayerIndex, 3+2*currentPlayerIndex), new Point(2+2*currentPlayerIndex, 4+2*currentPlayerIndex)));
    }

    private void damagingProcess() {
        if (!wasHit) {
            currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        }
        System.out.println("player " + currentPlayerIndex);
        players.get(currentPlayerIndex).getMap().draw();
        wasHit = players.get(currentPlayerIndex).getMap().hit(new Point(scanner.nextInt(), scanner.nextInt()));
    }

    //checking boat size
    private boolean isSizeOk(Boat boat, int size) {
        return boat.getBegin().getX() != boat.getEnd().getX() ? boat.getEnd().getX()-boat.getBegin().getX()+1 == size
                : boat.getEnd().getY()-boat.getBegin().getY()+1 == size;
    }

}
