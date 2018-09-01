package com.ericeschnei.cmos.breadboard;

import com.ericeschnei.cmos.breadboard.gamecoordinates.GameCoordinate;

import java.util.HashMap;

public class GameMap {
    private GameMap() {
        map = new HashMap<Coordinate, GameCoordinateList>();
    }
    private static GameMap instance = null;

    public static GameMap getInstance() {
        if (instance == null) instance = new GameMap();
        return instance;
    }

    private HashMap<Coordinate, GameCoordinateList> map;

    public void add(GameCoordinate gc, Coordinate c) {
        map.putIfAbsent(c, new GameCoordinateList());
        map.get(c).add(gc);
    }
}
