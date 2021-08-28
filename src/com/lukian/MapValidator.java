package com.lukian;

import com.lukian.entity.Boat;

import static com.lukian.Constants.*;

public class MapValidator {

    private MapValidator() {
    }

    public static boolean isGreaterBorder(int value) {
        return value >= 1;
    }

    public static boolean isWidthLessBorder(int x) {
        return x < WITH;
    }

    public static boolean isHeightLessBorder(int y) {
        return y < HEIGHT;
    }

    public static boolean hasBoatOrEmpty(char cell) {
        return isBoatInCell(cell) || isEmptyCell(cell);
    }

    public static boolean isBoatInCell(char cell) {
        return cell == BOAT_SYMBOL;
    }

    public static boolean isEmptyCell(char cell) {
        return cell == EMPTY_CELL_SYMBOL;
    }

    public static boolean isValid(Boat boat, char[][] cells) {
        return arePointsHitTheCellBoundaries(boat)
                && areAllBoatCagesEmpty(boat, cells);
    }

    //the method checks if the beginning and end of the boat are within the boundaries of the cells
    private static boolean arePointsHitTheCellBoundaries(Boat boat) {
        return (isGreaterBorder(boat.getBegin().getX()) && isGreaterBorder(boat.getBegin().getY())
                && isWidthLessBorder(boat.getBegin().getX()) && isHeightLessBorder(boat.getBegin().getY()))
                && (isGreaterBorder(boat.getEnd().getX()) && isGreaterBorder(boat.getEnd().getY())
                && isWidthLessBorder(boat.getEnd().getX()) && isHeightLessBorder(boat.getEnd().getY()));
    }

    //method checks if ALL boat points can be placed in EMPTY cells
    private static boolean areAllBoatCagesEmpty(Boat boat, char[][] cells) {
        for (int x = boat.getBegin().getX(); x <= boat.getEnd().getX(); x++) {
            for (int y = boat.getBegin().getY(); y <= boat.getEnd().getY(); y++) {
                if (!isEmptyCell(cells[x][y])) {
                    return false;
                }
            }
        }
        return true;
    }
}
