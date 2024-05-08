package com.tower.game.objects.world;

public class WorldSearchResult {
    public int maxAvailableDistance;
    public boolean isObject;

    public WorldSearchResult(int maxAvailableDistance, boolean isObject) {
        this.maxAvailableDistance = maxAvailableDistance;
        this.isObject = isObject;
    }
}
