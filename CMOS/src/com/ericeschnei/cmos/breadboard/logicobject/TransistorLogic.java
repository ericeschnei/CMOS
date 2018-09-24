package com.ericeschnei.cmos.breadboard.logicobject;

public class TransistorLogic extends LogicObject {
    public static int PMOS = 0;
    public static int NMOS = 1;

    private LogicNode[] nodes;
    private int myType;

    private TransistorLogic(int type) {
        super();
        nodes = new LogicNode[3];
        nodes[PinType.INPUT.getPin()] = LogicNode.newInstance();
        nodes[PinType.OUTPUT.getPin()] = LogicNode.newInstance();
        nodes[PinType.SWITCH.getPin()] = LogicNode.newInstance();

        this.myType = type;
    }

    public TransistorLogic newInstance(int type) {
        return new TransistorLogic(type);
    }

    public Status getNodeStatus(int pin) {
        return this.nodes[pin].getStatus();
    }

    public LogicNode getNode(int pin) {
        return this.nodes[pin];
    }

    public void addConnection(TransistorLogic t1, int fromPin, int myPin) {
        t1.getNode(fromPin).addConnection(this.getNode(myPin));
    }

    @Override
    public void addConnection(LogicObject l, int fromPin, int myPin) {
        //TODO
    }

    public void update() {
        //TODO
    }

}