package com.ericeschnei.cmos.breadboard;

public class Coordinate {
    private int x, y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x == that.getX() && y == that.getY();
    }

    @Override
    public int hashCode() {
        int result = 123527;
        result = 124247 * result + x;
        result = 124247 * result + y;
        return result;
    }
}
