package com.lukian.entity;

public class Boat {

    private final Point begin;
    private final Point end;
    private int health;

    public Boat(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
        this.health = getPointCount();
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public int getHealth() {
        return health;
    }

    public boolean isPointBelongsToTheBoat(Point p) {
        int beginX = begin.getX();
        int beginY = begin.getY();
        int endX = end.getX();
        int endY = end.getY();
        for (int x = beginX; x <= endX; x++) {
            for (int y = beginY; y <= endY; y++) {
                if (y == p.getY() && x == p.getX()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void decreaseHealth() {
        this.health--;
    }

    public int getPointCount() {
        if (isStartNotMatchesEndX()) {
            return lengthBetween(end.getX(), begin.getX());
        } else if (isStartNotMatchesEndY()) {
            return lengthBetween(end.getY(), begin.getY());
        }
        return 1;
    }

    public boolean isStartNotMatchesEndX() {
        return begin.getX() != end.getX();
    }

    public boolean isStartNotMatchesEndY() {
        return begin.getY() != end.getY();
    }

    private int lengthBetween(int end, int begin) {
        return end - begin + 1;
    }

}
