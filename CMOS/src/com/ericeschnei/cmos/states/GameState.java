package com.ericeschnei.cmos.states;

public interface GameState {

    String getName();

    void update();

    void render();

    default void lmPress(int x, int y) {
    }

    default void lmRelease(int x, int y) {
    }

    default void rmPress(int x, int y) {
    }

    default void rmRelease(int x, int y) {
    }

    default void keyPress(int key) {
    }

    default void resize(int x, int y) {
    }

    default void scroll(double y) {
    }
}
