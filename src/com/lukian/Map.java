package com.lukian;

public class Map {

    private static final int WITH = 11;
    private static final int HEIGHT = 11;
    private char[][] cells;

    public Map() {
        cells = new char[WITH][HEIGHT];

        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < HEIGHT; x++) {
                cells[x][y] = ' ';
            }
        }
    }

    //draw the playground
    public void draw() {
        char right = 0x2192; //symbol for a beautiful primitive visualization of the arrow to the right
        char down = 0x2193; //symbol for a beautiful primitive visualization of an arrow pointing down
        System.out.println("X"+right+"1   2   3   4   5   6   7   8   9   10  Y"+down);
        for(int y = 1; y < cells[1].length; y++) {
            System.out.print("| ");
            for(int x = 1; x < cells.length; x++) {
                System.out.print(cells[x][y] + " | ");
            }
            System.out.println(y);
        }
        System.out.println("X" +right+ "1   2   3   4   5   6   7   8   9   10  Y"+down);
    }

}
