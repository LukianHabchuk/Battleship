package com.lukian;

import com.lukian.entity.Boat;
import com.lukian.entity.Point;

import java.util.LinkedList;

import static com.lukian.Constants.*;
import static com.lukian.Constants.CELL_AROUND_THE_BOAT_SYMBOL;

public class Map {

    private static final int WITH = 11;
    private static final int HEIGHT = 11;

    private LinkedList<Boat> boats;
    private char[][] cells;

    public Map() {
        cells = new char[WITH][HEIGHT];
        boats = new LinkedList<Boat>();

        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < HEIGHT; x++) {
                cells[x][y] = EMPTY_CELL_SYMBOL;
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

    public boolean generateTheBoat(Boat boat) {
        if (boat.getBegin().getX() != boat.getEnd().getX()) {
            for(int x = boat.getBegin().getX(); x <= boat.getEnd().getX(); x++) {
                setPointValue(new Point(x, boat.getBegin().getY()));
            }
        } else if (boat.getBegin().getY() != boat.getEnd().getY()) {
            for(int y = boat.getBegin().getY(); y <= boat.getEnd().getY(); y++) {
                setPointValue(new Point(boat.getBegin().getX(), y));
            }
        } else {
            setPointValue(new Point(boat.getBegin().getX(), boat.getBegin().getY()));
        }
        boats.add(boat);
        return true;
    }

    //the method puts a point of the boat on the cell
    private void setPointValue(Point point) {
        cells[point.getX()][point.getY()] = BOAT_SYMBOL;
        markTheAreaAroundThePoint(point);
    }

    //marks the area around it in order to control the distance between the boats
    private void markTheAreaAroundThePoint(Point point) {
        markAreaFromTheLeftOfThePoint(point);
        markAreaFromTheRightOfThePoint(point);
        markAreaOverThePoint(point);
        markAreaUnderThePoint(point);
    }

    private void markAreaOverThePoint(Point point) {
        //checks if the value is below the minimum acceptable and if the cell is empty on the y scale
        //marks area over the point
        if (point.getY() - 1 > 0 && cells[point.getX()][point.getY() - 1] == ' ') {
            cells[point.getX()][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the left
        if (point.getX() - 1 > 0 && cells[point.getX() - 1][point.getY()-1] == ' ') {
            cells[point.getX() - 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the right
        if (point.getX() + 1 <= 10 && cells[point.getX() + 1][point.getY()-1] == ' ') {
            cells[point.getX() + 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaUnderThePoint(Point point) {
        //checks if the value is higher than the maximum allowed and if the cell is empty on the y scale
        //marks area under the point
        if (point.getY() + 1 <= 10  && cells[point.getX()][point.getY() + 1] == ' ') {
            cells[point.getX()][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the left
        if (point.getX() - 1 > 0 && cells[point.getX() - 1][point.getY() + 1] == ' ') {
            cells[point.getX() - 1][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the right
        if (point.getX() + 1 <= 10 && cells[point.getX() + 1][point.getY() + 1] == ' ') {
            cells[point.getX() + 1][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheLeftOfThePoint(Point point) {
        //marks area from the left of the point
        if (point.getX() - 1 > 0 && cells[point.getX() - 1][point.getY()] == ' ') {
            cells[point.getX() - 1][point.getY()] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheRightOfThePoint(Point point) {
        //marks area from the right of the point
        if (point.getX() + 1 <= 10 && cells[point.getX() + 1][point.getY()] == ' ') {
            cells[point.getX() + 1][point.getY()] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

}
