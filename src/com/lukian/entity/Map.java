package com.lukian.entity;

import java.util.LinkedList;
import java.util.List;

import static com.lukian.Constants.*;

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

    private void generateEmptyCells() {
        for (var y = 0; y < HEIGHT; y++) {
            for (var x = 0; x < HEIGHT; x++) {
                cells[x][y] = EMPTY_CELL_SYMBOL;
            }
        }
    }

    //draw the playground
    public void draw() {
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
        for (var y = 1; y < cells[1].length; y++) {
            System.out.print("| ");
            for (var x = 1; x < cells.length; x++) {
                System.out.print(cells[x][y] + " | ");
            }
            System.out.println(y);
        }
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
    }

    //draw the playground for battle
    public void drawBattleField() {
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
        for (var y = 1; y < cells[1].length; y++) {
            System.out.print("| ");
            for (var x = 1; x < cells.length; x++) {
                if (cells[x][y] == BOAT_SYMBOL || cells[x][y] == EMPTY_CELL_SYMBOL)
                    System.out.print(CELL_AROUND_THE_BOAT_SYMBOL + " | ");
                else System.out.print(cells[x][y] + " | ");
            }
            System.out.println(y);
        }
        System.out.println("X" + RIGHT + COORDINATE_SCALE + DOWN);
    }

    public boolean generateTheBoat(Boat boat) {

        if (!isValid(boat)) {
            return false;
        }

        if (boat.getBegin().getX() != boat.getEnd().getX()) {
            for (int x = boat.getBegin().getX(); x <= boat.getEnd().getX(); x++) {
                setPointValue(new Point(x, boat.getBegin().getY()));
            }
        } else if (boat.getBegin().getY() != boat.getEnd().getY()) {
            for (int y = boat.getBegin().getY(); y <= boat.getEnd().getY(); y++) {
                setPointValue(new Point(boat.getBegin().getX(), y));
            }
        } else {
            setPointValue(new Point(boat.getBegin().getX(), boat.getBegin().getY()));
        }
        boats.add(boat);
        return true;
    }

    private boolean isValid(Boat boat) {
        return arePointsHitTheCellBoundaries(boat)
                && areAllBoatCagesEmpty(boat);
    }

    //the method checks if the beginning and end of the boat are within the boundaries of the cells
    private boolean arePointsHitTheCellBoundaries(Boat boat) {
        return (boat.getBegin().getX() >= 1 && boat.getBegin().getY() >= 1
                && boat.getBegin().getX() < WITH && boat.getBegin().getY() < HEIGHT)
                && (boat.getEnd().getX() >= 1 && boat.getEnd().getY() >= 1
                && boat.getEnd().getX() < WITH && boat.getEnd().getY() < HEIGHT);
    }

    //method checks if ALL boat points can be placed in EMPTY cells
    private boolean areAllBoatCagesEmpty(Boat boat) {
        for (int x = boat.getBegin().getX(); x <= boat.getEnd().getX(); x++) {
            for (int y = boat.getBegin().getY(); y <= boat.getEnd().getY(); y++) {
                if (cells[x][y] != EMPTY_CELL_SYMBOL) {
                    return false;
                }
            }
        }
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
        if (point.getX() - 1 > 0 && cells[point.getX() - 1][point.getY() - 1] == ' ') {
            cells[point.getX() - 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the right
        if (point.getX() + 1 < WITH && cells[point.getX() + 1][point.getY() - 1] == ' ') {
            cells[point.getX() + 1][point.getY() - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaUnderThePoint(Point point) {
        //checks if the value is higher than the maximum allowed and if the cell is empty on the y scale
        //marks area under the point
        if (point.getY() + 1 < HEIGHT  && cells[point.getX()][point.getY() + 1] == ' ') {
            cells[point.getX()][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the left
        if (point.getX() - 1 > 0 && point.getY() + 1 < HEIGHT && cells[point.getX() - 1][point.getY() + 1] == ' ') {
            cells[point.getX() - 1][point.getY() + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the right
        if (point.getX() + 1 < WITH && point.getY() + 1 < HEIGHT && cells[point.getX() + 1][point.getY() + 1] == ' ') {
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
        if (point.getX() + 1 < WITH && cells[point.getX() + 1][point.getY()] == ' ') {
            cells[point.getX() + 1][point.getY()] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    public boolean hit(Point p) {
        try {
            if (cells[p.getX()][p.getY()] == BOAT_SYMBOL) {
                System.out.println("DAMAGE");
                damageTheBoat(p);
                cells[p.getX()][p.getY()] = HIT_BOAT_SYMBOL;
                return true;
            } else {
                System.out.println("You missed");
                cells[p.getX()][p.getY()] = MISSED_BOAT_SYMBOL;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("wrong number! You can choose between 1 and 10");
        }
        drawBattleField();
        return false;
    }

    private void damageTheBoat(Point p) {
        try {
            for (Boat b : boats) {
                if (b.isPointBelongsToTheBoat(p)) {
                    if (b.getHealth() > 1) {
                        b.decreaseHealth();
                        System.out.println("decreasing");
                    } else {
                        System.out.println("The boat have been destroyed");
                        boats.remove(b);
                        drawBattleField();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isGameOver() {
        return boats.isEmpty();
    }
}
