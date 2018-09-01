package com.ericeschnei.cmos.breadboard.gameobject;

import com.ericeschnei.cmos.breadboard.Coordinate;
import com.ericeschnei.cmos.breadboard.gamecoordinates.GameCoordinate;
import com.ericeschnei.cmos.breadboard.logicobject.LogicObject;

import java.util.ArrayList;

public abstract class GameObject {
    protected ArrayList<GameCoordinate> pieces;
    protected LogicObject logic;

    public abstract ArrayList<Coordinate> deleteObject();
    public abstract ArrayList<GameObject> parse();
    public abstract void deleteElement(GameObject go);
    public abstract void delete();
}
