package com.ericeschnei.cmos.breadboard.gamecoordinates;

import com.ericeschnei.cmos.breadboard.gameobject.GameObject;
import com.ericeschnei.cmos.breadboard.gameobject.NMOSObject;
import com.ericeschnei.cmos.renderer.SpriteBatch;

public class NMOSInput extends GameCoordinate {

    public static NMOSInput newInstance(NMOSObject parent, int rotation) {
        if (rotation > 3 || rotation < 0) return null;
        switch (rotation) {
            case 0:
                return new NMOSInput(parent, SpriteBatch.SPRITEBATCH_90CW_FLIP, false);
            case 1:
                return new NMOSInput(parent, SpriteBatch.SPRITEBATCH_90CW_FLIP, true);
            case 2:
                return new NMOSInput(parent, SpriteBatch.SPRITEBATCH_0DEG_NOFLIP, true);
            case 3:
            default:
                return new NMOSInput(parent, SpriteBatch.SPRITEBATCH_0DEG_NOFLIP, false);
        }
    }

    private NMOSInput(GameObject parent, byte rotation, boolean isFlip) {
        super(parent, isFlip ? 7 : 2, isFlip ? 3 : 1, 1, 1, 1, 1, 1, 1, rotation, 1);
    }

    @Override
    public boolean isTouching(int x, int y) {
        if (y >= 11 && x >= 3 && x <= 12) return true;
        if (x >= 7 && x <= 9 && y >= 8) return true;
        return false;
    }

    @Override
    public void add(GameCoordinate b) {

    }
}
