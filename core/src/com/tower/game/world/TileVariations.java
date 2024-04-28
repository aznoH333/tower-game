package com.tower.game.world;

import java.util.ArrayList;

public class TileVariations {

    private final ArrayList<String> sprites;
    public TileVariations(String sprite){
        this.sprites = new ArrayList<>();
        sprites.add(sprite);
    }


    public void addSprite(String sprite){
        this.sprites.add(sprite);
    }

    public String getSprite(int tileRandomness){
        return sprites.get(tileRandomness % sprites.size());
    }

}
