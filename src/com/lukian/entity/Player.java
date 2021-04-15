package com.lukian.entity;

import java.util.LinkedList;

public class Player {

    private int id;
    private String name;
    private LinkedList<Boat> boats;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        boats = new LinkedList<Boat>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Boat> getBoats() {
        return boats;
    }

    public void setBoats(LinkedList<Boat> boats) {
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
    }
}
