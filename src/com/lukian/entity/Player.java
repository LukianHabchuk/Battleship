package com.lukian.entity;

import com.lukian.Map;

public class Player {

    private int id;
    private String name;
    private Map map;

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
