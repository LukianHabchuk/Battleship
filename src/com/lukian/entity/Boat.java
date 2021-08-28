package com.lukian.entity;

public class Boat {

    private final Point begin;
    private final Point end;
    private int health;

    public Boat(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
        this.health = getHealthValue();
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
        for(int x = begin.getX(); x <= end.getX(); x++) {
            for(int y = begin.getY(); y <= end.getY(); y++) {
                    if (y == p.getY() && x == p.getX())
                        return true;
                }
        }
        return false;
    }

    public void decreaseHealth() {
        this.health--;
    }

    private int getHealthValue() {
        if (isNotTheSameX()) {
            return  lengthBetween(end.getX(), begin.getX());
        } else if (isNotTheSameY()) {
            return lengthBetween(end.getY(), begin.getY());
        }
        return 1;
    }

    private boolean isNotTheSameX() {
        return begin.getX() != end.getX();
    }

    private boolean isNotTheSameY() {
        return begin.getY() != end.getY();
    }

    private int lengthBetween(int end, int begin) {
        return end - begin + 1;
    }
    
}
