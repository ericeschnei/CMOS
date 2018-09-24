package com.ericeschnei.cmos.breadboard.logicobject;

import java.util.ArrayList;
import java.util.List;

public class LogicNode {
    private List<LogicNode> myConnections;
    private Status myStatus;

    private LogicNode() {
        myConnections = new ArrayList<>();
        myStatus = Status.LOW;
    }
    
    public static LogicNode newInstance() {
        return new LogicNode();
    }

    public void addConnection(LogicNode node) {
        myConnections.add(node);
    }

    public List<LogicNode> getConnections() {
        return myConnections;
    }

    public boolean updateStatus() {
        int[] statusCount = new int[3];
        for(int a = 0; a < 3; a++)
            statusCount[a] = 0;

        final int HIGH = 0;
        final int LOW = 1;
        final int FLOATING = 2;
        
        for(LogicNode n : myConnections) {
            if(n.getStatus() == Status.HIGH)
                statusCount[HIGH]++;
            else if(n.getStatus() == Status.LOW)
                statusCount[LOW]++;
            else
                statusCount[FLOATING]++;
        }

        if(statusCount[HIGH] > 0 && statusCount[LOW] > 0)
            return false;
        else if(statusCount[HIGH] > 0)
            myStatus = Status.HIGH;
        else if(statusCount[LOW] > 0)
            myStatus = Status.LOW;
        else
            myStatus = Status.FLOATING;
        return true;
    }

    public Status getStatus() {
        return myStatus;
    }
}