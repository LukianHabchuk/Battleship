package com.lukian;

public class Constants {

    Constants() {
    }
    public static final int WITH = 11;
    public static final int HEIGHT = 11;
    public static final char BOAT_SYMBOL = '■';
    public static final char HIT_BOAT_SYMBOL = 'X';
    public static final char MISSED_BOAT_SYMBOL = 'O';
    public static final char EMPTY_CELL_SYMBOL = ' ';
    public static final char CELL_AROUND_THE_BOAT_SYMBOL = '+';
    public static final String COORDINATE_SCALE = "1   2   3   4   5   6   7   8   9   10  Y";
    public static final String PLAYER_INFO = """
                  1 ship - a row of 4 cells ("four-deck"; battleship)
                  2 ships - a row of 3 cells ("three-deck"; cruisers)
                  3 ships - a row of 2 cells ("double-deck"; destroyers)
                  4 ships - 1 cell ("single-deck"; torpedo boats)
                """;

}
