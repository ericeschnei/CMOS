package com.ericeschnei.cmos.breadboard.logicobject;

import com.ericeschnei.cmos.breadboard.gameobject.GOType;

public abstract class LogicObject {
    protected LogicObject(){}

    public static LogicObject newInstance(GOType GO){
        // TODO
        return null;
    };
    
    public abstract Status getNodeStatus(int pin);

    public abstract LogicNode getNode(int pin);

    public abstract void addConnection(LogicObject l, int fromPin, int myPin);
}