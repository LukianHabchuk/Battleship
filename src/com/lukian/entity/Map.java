package com.lukian.entity;

import java.util.LinkedList;
import java.util.List;

import static com.lukian.Constants.*;
import static com.lukian.MapValidator.*;

public class Map {

    private static final char RIGHT = 0x2192; //symbol for a beautiful primitive visualization of the arrow to the right
    private static final char DOWN = 0x2193; //symbol for a beautiful primitive visualization of an arrow pointing down

    private final List<Boat> boats;
    private final char[][] cells;

    public Map() {
        cells = new char[WITH][HEIGHT];
        boats = new LinkedList<>();
        generateEmptyCells();
    }

    //draw the playground
    public void draw(boolean isBattleMode) {
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
        for (var y = 1; y < cells[1].length; y++) {
            System.out.print("| ");
            for (var x = 1; x < cells.length; x++) {
                if (isBattleMode && hasBoatOrEmpty(cells[x][y])) {
                    System.out.print(CELL_AROUND_THE_BOAT_SYMBOL + " | ");
                } else {
                    System.out.print(cells[x][y] + " | ");
                }
            }
            System.out.println(y);
        }
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
    }

    public boolean generateTheBoat(Boat boat) {

        if (!isValid(boat, cells)) {
            return false;
        }

        if (boat.isStartNotMatchesEndX()) {
            for (int x = boat.getBegin().getX(); x <= boat.getEnd().getX(); x++) {
                setPointValue(new Point(x, boat.getBegin().getY()));
            }
        } else if (boat.isStartNotMatchesEndY()) {
            for (int y = boat.getBegin().getY(); y <= boat.getEnd().getY(); y++) {
                setPointValue(new Point(boat.getBegin().getX(), y));
            }
        } else {
            setPointValue(new Point(boat.getBegin().getX(), boat.getBegin().getY()));
        }
        boats.add(boat);
        return true;
    }

    public boolean hit(Point point) {
        try {
            if (isBoatInCell(cells[point.getX()][point.getY()])) {
                System.out.println("DAMAGE");
                damageTheBoat(point);
                cells[point.getX()][point.getY()] = HIT_BOAT_SYMBOL;
                return true;
            } else {
                System.out.println("You missed.");
                cells[point.getX()][point.getY()] = MISSED_BOAT_SYMBOL;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong number! You can choose between 1 and 10.");
        }
        draw(true);
        return false;
    }

    public boolean isGameOver() {
        return boats.isEmpty();
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
        if (isGreaterBorder(point.getY() - 1)
                && isEmptyCell(cells[point.getX()][point.getY() - 1])) {
            cells[point.getX()][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the left
        if (isGreaterBorder(point.getX() - 1)
                && isEmptyCell(cells[point.getX() - 1][point.getY() - 1])) {
            cells[point.getX() - 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the right
        if (isWidthLessBorder(point.getX() + 1)
                && isEmptyCell(cells[point.getX() + 1][point.getY() - 1])) {
            cells[point.getX() + 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaUnderThePoint(Point point) {
        //checks if the value is higher than the maximum allowed and if the cell is empty on the y scale
        //marks area under the point
        if (isHeightLessBorder(point.getY() + 1)
                && isEmptyCell(cells[point.getX()][point.getY() + 1])) {
            cells[point.getX()][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the left
        if (isGreaterBorder(point.getX() - 1)
                && isHeightLessBorder(point.getY() + 1)
                && isEmptyCell(cells[point.getX() - 1][point.getY() + 1])) {
            cells[point.getX() - 1][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the right
        if (isWidthLessBorder(point.getX() + 1)
                && isHeightLessBorder(point.getY() + 1)
                && isEmptyCell(cells[point.getX() + 1][point.getY() + 1])) {
            cells[point.getX() + 1][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheLeftOfThePoint(Point point) {
        //marks area from the left of the point
        if (isGreaterBorder(point.getX() - 1) && isEmptyCell(cells[point.getX() - 1][point.getY()])) {
            cells[point.getX() - 1][point.getY()] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheRightOfThePoint(Point point) {
        //marks area from the right of the point
        if (isWidthLessBorder(point.getX() + 1) && isEmptyCell(cells[point.getX() + 1][point.getY()])) {
            cells[point.getX() + 1][point.getY()] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void damageTheBoat(Point point) {
        try {
            for (Boat b : boats) {
                if (b.isPointBelongsToTheBoat(point)) {
                    if (b.getHealth() > 1) {
                        b.decreaseHealth();
                        System.out.println("Decreasing.");
                    } else {
                        System.out.println("The boat have been destroyed");
                        boats.remove(b);
                        draw(true);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateEmptyCells() {
        for (var y = 0; y < HEIGHT; y++) {
            for (var x = 0; x < HEIGHT; x++) {
                cells[x][y] = EMPTY_CELL_SYMBOL;
            }
        }
    }
}
