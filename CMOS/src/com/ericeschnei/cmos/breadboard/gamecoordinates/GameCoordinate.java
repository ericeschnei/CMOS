package com.ericeschnei.cmos.breadboard.gamecoordinates;

import com.ericeschnei.cmos.breadboard.Coordinate;
import com.ericeschnei.cmos.breadboard.gameobject.GameObject;
import com.ericeschnei.cmos.renderer.SpriteBatch;

import java.util.ArrayList;

public abstract class GameCoordinate {
    private GameObject parent;
    private int textureX, textureY, width, height;
    protected float r,g,b,a;
    private int priority;
    private byte rotation;

    public GameCoordinate(GameObject parent, int textureX, int textureY, int width, int height, float r, float g, float b, float a, byte rotation, int priority) {
        this.parent = parent;
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rotation = rotation;
        this.priority = priority;
    }

    public void render(int x, int y, int scale) {
        SpriteBatch.getInstance().add(x, y, textureX, textureY, width, height, scale, rotation, r, g, b, a);
    }

    public abstract boolean isTouching(int x, int y);
    public abstract void add(GameCoordinate b);

    public GameObject getParent() {
        return parent;
    }

    public void delete() {
        parent.delete();
    }

    public int correctRotationX(int x, int y) {
        if (rotation == SpriteBatch.SPRITEBATCH_0DEG_NOFLIP) return x;
        if (rotation == SpriteBatch.SPRITEBATCH_0DEG_FLIP) return 15-x;
        if (rotation == SpriteBatch.SPRITEBATCH_90CW_NOFLIP) return y;
        if (rotation == SpriteBatch.SPRITEBATCH_90CW_FLIP) return 15-y;
        if (rotation == SpriteBatch.SPRITEBATCH_90CCW_NOFLIP) return 15-y;
        if (rotation == SpriteBatch.SPRITEBATCH_90CCW_FLIP) return y;
        if (rotation == SpriteBatch.SPRITEBATCH_180_NOFLIP) return 15-x;
        return x;


    }
    public int correctRotationY(int x, int y) {
        if (rotation == SpriteBatch.SPRITEBATCH_0DEG_NOFLIP) return y;
        if (rotation == SpriteBatch.SPRITEBATCH_0DEG_FLIP) return y;
        if (rotation == SpriteBatch.SPRITEBATCH_90CW_NOFLIP) return 15-x;
        if (rotation == SpriteBatch.SPRITEBATCH_90CW_FLIP) return 15-x;
        if (rotation == SpriteBatch.SPRITEBATCH_90CCW_NOFLIP) return x;
        if (rotation == SpriteBatch.SPRITEBATCH_90CCW_FLIP) return x;
        return 15-y;
    }

    public boolean isWirePoint() { return false; }
    public int getPriority() {
        return priority;
    }
}
