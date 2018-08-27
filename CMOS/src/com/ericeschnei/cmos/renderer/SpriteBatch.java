package com.ericeschnei.cmos.renderer;

import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public class SpriteBatch {
    public static final byte SPRITEBATCH_0DEG_NOFLIP = (byte)0b01001011;
    public static final byte SPRITEBATCH_90CW_NOFLIP = (byte)0b00101101;
    public static final byte SPRITEBATCH_180_NOFLIP = (byte)0b10110100;
    public static final byte SPRITEBATCH_90CCW_NOFLIP = (byte)0b11010010;
    public static final byte SPRITEBATCH_0DEG_FLIP = (byte)0b00011110;
    public static final byte SPRITEBATCH_90CW_FLIP = (byte)0b10000111;
    public static final byte SPRITEBATCH_180_FLIP = (byte)0b11100001;
    public static final byte SPRITEBATCH_90CCW_FLIP = (byte)0b01111000;



    private static SpriteBatch ourInstance = null;

    private Texture sheet;
    private LinkedList<Sprite> sprites;

    private SpriteBatch() {
        sprites = new LinkedList<>();
        sheet = Texture.loadTexture("assets/ss.png");
        sheet.bind();
    }

    public static SpriteBatch getInstance() {
        if (ourInstance == null) {
            ourInstance = new SpriteBatch();
        }
        return ourInstance;
    }


    public void add(int screenX, int screenY, int textureX, int textureY, int width, int height,
                    int scale, byte rotation, float r, float g, float b, float a) {
        sprites.add(new Sprite(screenX, screenY, textureX, textureY, width, height, scale, r, g, b, a, rotation));
    }

    public void add(int screenX, int screenY, int textureX, int textureY, int width, int height, int scale, byte rotation) {
        sprites.add(new Sprite(screenX, screenY, textureX, textureY, width, height, scale, 1f, 1f, 1f, 1f, rotation));
    }

    private int getCharX(char c) {
        return ((c - 32) % 32) * 8;
    }
    private int getCharY(char c) {
        return ((c - 32)/32) * 8 + 96;
    }

    private int getXfromRot(int screenX, int length, int scale, byte rotation) {
        if (rotation == SPRITEBATCH_0DEG_NOFLIP || rotation == SPRITEBATCH_180_FLIP) return screenX;
        if (rotation == SPRITEBATCH_0DEG_FLIP || rotation == SPRITEBATCH_180_NOFLIP) return screenX + scale*(length-1)*6-3*scale;
        if (rotation == SPRITEBATCH_90CW_NOFLIP || rotation == SPRITEBATCH_90CCW_FLIP) return screenX - scale*3;
        if (rotation == SPRITEBATCH_90CCW_NOFLIP || rotation == SPRITEBATCH_90CW_FLIP) return screenX;
        return screenX;
    }
    private int getYfromRot(int screenY, int length, int scale, byte rotation) {
        if (rotation == SPRITEBATCH_90CW_NOFLIP || rotation == SPRITEBATCH_90CW_FLIP) return screenY;
        if (rotation == SPRITEBATCH_90CCW_NOFLIP || rotation == SPRITEBATCH_90CCW_FLIP) return screenY + scale*(length-1)*6-3*scale;
        if (rotation == SPRITEBATCH_0DEG_NOFLIP || rotation == SPRITEBATCH_0DEG_FLIP) return screenY;
        if (rotation == SPRITEBATCH_180_FLIP || rotation == SPRITEBATCH_180_NOFLIP) return screenY - scale*3;
        return screenY;
    }
    public void addText(String text, int screenX, int screenY, int scale, byte rotation, float r, float g, float b, float a) {
        int xDir = 0;
        int yDir = 0;
        if (rotation == SPRITEBATCH_0DEG_NOFLIP || rotation == SPRITEBATCH_180_FLIP) xDir = 1;
        else if (rotation == SPRITEBATCH_0DEG_FLIP || rotation == SPRITEBATCH_180_NOFLIP) xDir = -1;
        else if (rotation == SPRITEBATCH_90CW_NOFLIP || rotation == SPRITEBATCH_90CW_FLIP) yDir = 1;
        else if (rotation == SPRITEBATCH_90CCW_NOFLIP || rotation == SPRITEBATCH_90CCW_FLIP) yDir = -1;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 32 || c > 126) continue;
            add(getXfromRot(screenX, text.length(), scale, rotation) + xDir * i * scale * 6,
                    getYfromRot(screenY, text.length(), scale, rotation) + yDir * i * scale * 6,
                    getCharX(c), getCharY(c), 8, 8, scale, rotation, r,g,b,a);
        }
    }
    private boolean bitAt(byte b, int k) {
        return (b >> k & 1) == 1;
    }

    public void draw() {
        sheet.bind();
        glBegin(GL_QUADS);
        while (!sprites.isEmpty()) {
            Sprite s = sprites.remove();

            int screenX1 = s.getScreenX();
            int screenX2 = s.getScreenX() + s.getWidth() * s.getScale();

            int screenY1 = s.getScreenY();
            int screenY2 = s.getScreenY() + s.getHeight() * s.getScale();

            float sheetX1 = s.getTextureX() / (float)sheet.getWidth();
            float sheetX2 = (s.getTextureX() + s.getWidth()) / (float)sheet.getWidth();
            float sheetY1 = s.getTextureY() / (float)sheet.getHeight();
            float sheetY2 = (s.getTextureY() + s.getHeight()) / (float)sheet.getHeight();

            byte rotation = s.getRotation();


            glColor4f(s.getR(), s.getG(), s.getB(), s.getA());

            glTexCoord2d(bitAt(rotation, 0) ? sheetX1 : sheetX2, bitAt(rotation, 1) ? sheetY1 : sheetY2);
            glVertex2d(screenX1, screenY1);
            glTexCoord2d(bitAt(rotation, 2) ? sheetX1 : sheetX2, bitAt(rotation, 3) ? sheetY1 : sheetY2);
            glVertex2d(screenX2, screenY1);
            glTexCoord2d(bitAt(rotation, 4) ? sheetX1 : sheetX2, bitAt(rotation, 5) ? sheetY1 : sheetY2);
            glVertex2d(screenX2, screenY2);
            glTexCoord2d(bitAt(rotation, 6) ? sheetX1 : sheetX2, bitAt(rotation, 7) ? sheetY1 : sheetY2);
            glVertex2d(screenX1, screenY2);
        }
        glEnd();

    }

    private class Sprite {
        private int screenX, screenY, textureX, textureY, width, height, scale;
        private float r, g, b, a;
        private byte rotation;

        /*
        rotation is a bitvector containing the ordering of the corners in rendering.
        rendering a sprite is done clockwise from the top-left, like so:
                0 - 1
                |   |
                3 - 2

        the bitmask, then, contains XY information when rendering to each corner.
        in each block of two bits, we get the information about a corner--more significant is Y position, less is X.
        a 1 represents the bottom/right, and a 0 represents top/left.
        The least significant two bits represent the top left corner.
        So the default, non-rotated image would have a rotation value of 0b10110100 (paradiddle!)

         */
        public Sprite(int screenX, int screenY, int textureX, int textureY, int width, int height,
                      int scale, float r, float g, float b, float a, byte rotation) {
            this.screenX = screenX;
            this.screenY = screenY;
            this.textureX = textureX;
            this.textureY = textureY;
            this.width = width;
            this.height = height;
            this.scale = scale;
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;

            this.rotation = rotation;
        }

        public int getScreenX() {
            return screenX;
        }

        public int getScreenY() {
            return screenY;
        }

        public int getTextureX() {
            return textureX;
        }

        public int getTextureY() {
            return textureY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getScale() {
            return scale;
        }

        public float getR() {
            return r;
        }

        public float getG() {
            return g;
        }

        public float getB() {
            return b;
        }

        public float getA() {
            return a;
        }

        public byte getRotation() {
            return rotation;
        }
    }
}
