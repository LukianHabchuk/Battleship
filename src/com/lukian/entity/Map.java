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
        StringBuilder scale = new StringBuilder()
                .append("X")
                .append(RIGHT)
                .append(COORDINATE_SCALE)
                .append(DOWN);

        System.out.println(scale);
        int arrayLength = cells.length;
        int subArrayLength = cells[1].length;
        for (var y = 1; y < subArrayLength; y++) {
            System.out.print("| ");
            for (var x = 1; x < arrayLength; x++) {
                if (isBattleMode && hasBoatOrEmpty(cells[x][y])) {
                    System.out.print(CELL_AROUND_THE_BOAT_SYMBOL + " | ");
                } else {
                    System.out.print(cells[x][y] + " | ");
                }
            }
            System.out.println(y);
        }
        System.out.println(scale);
    }

    public boolean generateTheBoat(Boat boat) {

        if (!isValid(boat, cells)) {
            return false;
        }

        int beginX = boat.getBegin().getX();
        int endX = boat.getEnd().getX();
        int beginY = boat.getBegin().getY();
        int endY = boat.getEnd().getY();

        if (boat.isStartNotMatchesEndX()) {
            for (int x = beginX; x <= endX; x++) {
                setPointValue(new Point(x, beginY));
            }
        } else if (boat.isStartNotMatchesEndY()) {
            for (int y = beginY; y <= endY; y++) {
                setPointValue(new Point(beginX, y));
            }
        } else {
            setPointValue(new Point(beginX, beginY));
        }
        boats.add(boat);
        return true;
    }

    public boolean hit(Point point) {
        try {
            int valueX = point.getX();
            int valueY = point.getY();
            if (isBoatInCell(cells[valueX][valueY])) {
                System.out.println("DAMAGE");
                damageTheBoat(point);
                cells[valueX][valueY] = HIT_BOAT_SYMBOL;
                return true;
            } else {
                System.out.println("You missed.");
                cells[valueX][valueY] = MISSED_BOAT_SYMBOL;
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
        int valueX = point.getX();
        int valueY = point.getY();
        //checks if the value is below the minimum acceptable and if the cell is empty on the y scale
        //marks area over the point
        if (isGreaterBorder(valueY - 1)
                && isEmptyCell(cells[valueX][valueY - 1])) {
            cells[valueX][valueY - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the left
        if (isGreaterBorder(valueX - 1)
                && isEmptyCell(cells[valueX - 1][valueY - 1])) {
            cells[valueX - 1][valueY - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area over the point from the right
        if (isWidthLessBorder(valueX + 1)
                && isEmptyCell(cells[valueX + 1][valueY - 1])) {
            cells[valueX + 1][valueY - 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaUnderThePoint(Point point) {
        int valueX = point.getX();
        int valueY = point.getY();
        //checks if the value is higher than the maximum allowed and if the cell is empty on the y scale
        //marks area under the point
        if (isHeightLessBorder(valueY + 1)
                && isEmptyCell(cells[valueX][valueY + 1])) {
            cells[valueX][valueY + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the left
        if (isGreaterBorder(valueX - 1)
                && isHeightLessBorder(valueY + 1)
                && isEmptyCell(cells[valueX - 1][valueY + 1])) {
            cells[valueX - 1][valueY + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
        //marks area under the point from the right
        if (isWidthLessBorder(valueX + 1)
                && isHeightLessBorder(valueY + 1)
                && isEmptyCell(cells[valueX + 1][valueY + 1])) {
            cells[valueX + 1][valueY + 1] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheLeftOfThePoint(Point point) {
        int valueX = point.getX();
        int valueY = point.getY();
        //marks area from the left of the point
        if (isGreaterBorder(valueX - 1) && isEmptyCell(cells[valueX - 1][valueY])) {
            cells[valueX - 1][valueY] = CELL_AROUND_THE_BOAT_SYMBOL;
        }
    }

    private void markAreaFromTheRightOfThePoint(Point point) {
        int valueX = point.getX();
        int valueY = point.getY();
        //marks area from the right of the point
        if (isWidthLessBorder(valueX + 1) && isEmptyCell(cells[valueX + 1][valueY])) {
            cells[valueX + 1][valueY] = CELL_AROUND_THE_BOAT_SYMBOL;
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
