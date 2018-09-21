public abstract class LogicObject {
    private LogicObject(){};

    public static LogicObject newInstance(GOType GO){
        return LogicObject();
    };
    
    public Status getNodeStatus(int pin){};

    public LogicNode getNode(int pin){};

    public void addConnection(LogicObject l, int fromPin, int myPin){};
}