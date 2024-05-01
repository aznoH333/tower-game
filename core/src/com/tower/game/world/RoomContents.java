package com.tower.game.world;

import com.badlogic.gdx.Game;
import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.GameConstants;
import com.tower.game.utils.Utils;

import java.util.ArrayList;

public class RoomContents {

    public final WorldTile[][] tiles = new WorldTile[GameConstants.TILES_IN_ROOM][GameConstants.TILES_IN_ROOM];
    public final int[][] tileRandomization = new int[GameConstants.TILES_IN_ROOM][GameConstants.TILES_IN_ROOM];

    public RoomContents(ArrayList<ArrayList<String>> csv){
        try {
            for (int x = 0; x < GameConstants.TILES_IN_ROOM; x++){
                for (int y = 0; y < GameConstants.TILES_IN_ROOM; y++){
                    // weird axis shenanigans to make array math the csv file contents
                    tiles[x][y] = convertCsvValueToWorldTile(csv.get(GameConstants.TILES_IN_ROOM - y - 1).get(x));

                    final int MIN_RANDOM = 0;
                    final int MAX_RANDOM = 9;
                    // tile randomization
                    tileRandomization[x][y] = Utils.getRandomInRange(MIN_RANDOM, MAX_RANDOM);
                }
            }
        }catch (Exception e){
            DebugUtils.fatalCrash("loading level crash : " + e.getMessage());
        }

    }

    private WorldTile convertCsvValueToWorldTile(String value){
        int val = Integer.parseInt(value) + 1;

        return WorldTile.values()[val];
    }
}
