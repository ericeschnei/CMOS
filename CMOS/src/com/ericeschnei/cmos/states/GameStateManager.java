package com.ericeschnei.cmos.states;

import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> states;
    private GameState currentState;

    public GameStateManager() {
        states = new ArrayList<>();
        currentState = null;
    }

    public void addState(GameState state) {
        for (GameState s : states) {
            if (s.getName().equals(state.getName())) return;
        }
        states.add(state);
    }

    public void setState(String id) {
        for (GameState s : states) {
            if (id.equals(s.getName())) {
                currentState = s;
                return;
            }
        }
    }

    public void lmPress(int x, int y) {
        if (currentState != null) currentState.lmPress(x, y);
    }

    public void lmRelease(int x, int y) {
        if (currentState != null) currentState.lmRelease(x, y);
    }

    public void rmPress(int x, int y) {
        if (currentState != null) currentState.rmPress(x, y);
    }

    public void rmRelease(int x, int y) {
        if (currentState != null) currentState.rmRelease(x, y);
    }

    public void keyPress(int key) {
        if (currentState != null) currentState.keyPress(key);
    }

    public void resize(int x, int y) {
        if (currentState != null) currentState.resize(x, y);
    }

    public void scroll(double y) {
        if (currentState != null) currentState.scroll(y);
    }
}
