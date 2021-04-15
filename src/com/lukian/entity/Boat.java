package com.lukian.entity;

public class Boat {

    private Point begin;
    private Point end;
    private int helth;

    public Boat(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
        this.helth = begin.getX() != end.getX() ? (end.getX() - begin.getX() + 1)
                : begin.getY() != end.getY() ? (end.getY() - begin.getY() + 1)
                : 1;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public int getHelth() {
        return helth;
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
        this.helth--;
    }
    
}
