package com.lukian.entity;

public class Player {

    private final int id;
    private final String name;
    private final Map map;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        map = new Map();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }
}
