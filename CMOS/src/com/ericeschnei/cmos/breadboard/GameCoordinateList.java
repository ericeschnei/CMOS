package com.ericeschnei.cmos.breadboard;

import com.ericeschnei.cmos.breadboard.gamecoordinates.GameCoordinate;
import com.ericeschnei.cmos.breadboard.gameobject.GameObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameCoordinateList {
    private GameCoordinate[] elems;
    private LinkedList<GameCoordinate> wire;
    GameObject parentWire;

    public GameCoordinateList() {
        elems = new GameCoordinate[5];
    }
    public void add(GameCoordinate gc) {
        if (gc.getPriority() == -1) {
            addWire(gc);
            return;
        }
        else if (gc.getPriority() < 0 || gc.getPriority() > 4) {
            return;
        }
        elems[gc.getPriority()] = gc;
    }
    public void remove(GameCoordinate gc) {
        if (gc.getPriority() == -1) {
            removeWire(gc);
        }
        if (gc.getPriority() < 0 || gc.getPriority() > 4) {
            return;
        }
        if (elems[gc.getPriority()] != gc) return;
        elems[gc.getPriority()] = null;

    }
    public GameObject getParentWire() {
        if (parentWire == null) return null;
        return parentWire;
    }
    private void addWire(GameCoordinate gc) {
        wire.add(gc);
        if (gc.isWirePoint()) {
            if (parentWire == null) parentWire = gc.getParent();
            if (parentWire != gc.getParent()) System.out.println("Parent Wires do not match. Handle Later"); //TODO
        }

    }

    private void removeWire(GameCoordinate gc) {
        wire.remove(gc);
        boolean resetParent = true;
        for (GameCoordinate g : wire) {
            if (g.isWirePoint()) {
                resetParent = false;
                break;
            }
        }
        if (resetParent) parentWire = null;
    }


}
