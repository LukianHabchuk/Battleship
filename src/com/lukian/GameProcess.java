package com.lukian;

import com.lukian.entity.Boat;
import com.lukian.entity.Player;
import com.lukian.entity.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.lukian.Constants.PLAYER_INFO;

public class GameProcess {

    private final Scanner scanner = new Scanner(System.in);
    private int currentPlayerIndex;
    private int targetPlayerIndex;
    private boolean wasHit;

    private final List<Player> players;

    public GameProcess() {
        players = Arrays.asList(
                new Player(currentPlayerIndex, playerName(currentPlayerIndex)),
                new Player(currentPlayerIndex, playerName(currentPlayerIndex)));
    }

    public void play() {
        int playersCount = players.size();
        for (var i = 0; i < playersCount; i++) {
            System.out.println("set the boats for player " + players.get(i).getName());
            currentPlayerIndex = i;
            setTheBoats();
        }

        currentPlayerIndex = players.get(0).getId();
        targetPlayerIndex = players.get(1).getId();
        wasHit = false;

        while (!players.get(targetPlayerIndex).getMap().isGameOver()) {
            damagingProcess();
        }
        System.out.println("GG! The player " + players.get(currentPlayerIndex).getName() + " WIN");
    }

    private String playerName(int playerId) {
        String name = "";
        while (name.isEmpty()) {
            System.out.println("choose name for player " + playerId);
            name = scanner.nextLine();
        }
        currentPlayerIndex++;
        return name;
    }

    private void setTheBoats() {
        Point begin;
        Point end;
        Boat boat;

        System.out.println(PLAYER_INFO);

        for (var i = 1; i < 5; i++) {
            System.out.printf("please create %d boat/s of %d cell/s%n", (5 - i), i);
            for (var j = 1; j <= 5 - i; j++) {
                System.out.printf("Boat # %d with %d cells%n", j, i);
                players.get(currentPlayerIndex).getMap().draw(false);

                System.out.println("begin X and Y: ");
                begin = new Point(scanner.nextInt(), scanner.nextInt());

                //check is it 1-cell boat
                if (i == 1) {
                    end = begin;
                } else {
                    System.out.println("end X and Y: ");
                    end = new Point(scanner.nextInt(), scanner.nextInt());
                }

                boat = new Boat(begin, end);
                if (!isSizeOk(boat, i)) {
                    System.out.println("wrong boat size! Please try again");
                    j--;
                } else if (!players.get(currentPlayerIndex).getMap().generateTheBoat(boat)) {
                    System.out.println("wrong boat generation! Please try again!");
                    j--;
                }
            }
        }
    }

    private void damagingProcess() {
        if (!wasHit) {
            currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        }
        targetPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;

        System.out.println("player: " + players.get(currentPlayerIndex).getName());

        players.get(targetPlayerIndex).getMap().draw(true);
        wasHit = players.get(targetPlayerIndex).getMap()
                .hit(new Point(scanner.nextInt(), scanner.nextInt()));
    }

    //checking boat size
    private boolean isSizeOk(Boat boat, int size) {
        return boat.getPointCount() == size;
    }

}
