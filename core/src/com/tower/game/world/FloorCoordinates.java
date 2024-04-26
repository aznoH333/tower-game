package com.tower.game.world;

import java.util.Objects;

public class FloorCoordinates {
    private final int x;
    private final int y;
    private final int hashCode;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FloorCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        hashCode = Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FloorCoordinates that = (FloorCoordinates) o;
        return hashCode == that.hashCode;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
