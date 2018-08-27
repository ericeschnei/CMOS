package com.ericeschnei.cmos.main;

public class Input {
    private static Input ourInstance = new Input();
    private boolean[] keys;
    private boolean lMouse, rMouse;
    private int mouseX, mouseY, screenX, screenY;
    private Input() {
        keys = new boolean[349];
        lMouse = rMouse = false;
        mouseX = mouseY = screenX = screenY = 0;
    }

    public static Input getInstance() {
        return ourInstance;
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    public boolean islmPressed() {
        return lMouse;
    }

    public boolean isrmPressed() {
        return rMouse;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setlMouse(boolean lMouse) {
        this.lMouse = lMouse;
    }

    public void setrMouse(boolean rMouse) {
        this.rMouse = rMouse;
    }

    public void setMousePos(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void setScreenSize(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void setKey(int key, boolean down) {
        keys[key] = down;
    }
}
