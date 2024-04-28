package com.tower.game.world;

import com.tower.game.utils.DebugUtils;

import java.util.ArrayList;

public class RoomContents {
    public static final int TILES_IN_ROOM = 20;
    public static final int TILE_SIZE = 16;
    public final WorldTile[][] tiles = new WorldTile[TILES_IN_ROOM][TILES_IN_ROOM];

    public RoomContents(ArrayList<ArrayList<String>> csv){
        try {
            for (int x = 0; x < TILES_IN_ROOM; x++){
                for (int y = 0; y < TILES_IN_ROOM; y++){
                    // weird axis shenanigans to make array math the csv file contents
                    tiles[x][y] = convertCsvValueToWorldTile(csv.get(TILES_IN_ROOM - y - 1).get(x));
                }
            }
        }catch (Exception e){
            DebugUtils.fatalCrash("loading level crash : " + e.getMessage());
        }

    }

    private WorldTile convertCsvValueToWorldTile(String value){
        char firstChar = value.charAt(0);

        switch (firstChar) {
            case '0':
                return WorldTile.NONE;
            case '1':
                return WorldTile.WALL_UP;
            default:
                DebugUtils.fatalCrash("Unexpected char found when loading level : " + firstChar);
                return null;

        }
    }
}
