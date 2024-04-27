package com.tower.game.world;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;

public class TowerRoom {
    private static final int TILES_IN_ROOM = 20;
    private static final int TILE_SIZE = 16;
    private final WorldTile[][] tiles = new WorldTile[TILES_IN_ROOM][TILES_IN_ROOM];
    private final RoomTileset tileset = new RoomTileset(TilesetType.GREEN_DEFAULT);

    public TowerRoom(){
        // temporary generation
        for (int i = 0; i < TILES_IN_ROOM; i++){
            for (int j = 0; j < TILES_IN_ROOM; j++){
                if (i == 0 || j == 0 || i == 19 || j == 19){
                    tiles[i][j] = WorldTile.WALL_UP;
                }else {
                    tiles[i][j] = WorldTile.FLOOR;
                }
            }
        }
    }

    public void renderRoom(float cameraOffsetX, float cameraOffsetY){
        Drawing drawing = Drawing.getInstance();
        for (int x = 0; x < TILES_IN_ROOM; x++){
            for (int y = 0; y < TILES_IN_ROOM; y++) {
                drawTile(x, y, cameraOffsetX, cameraOffsetY, drawing);
            }
        }
    }

    private void drawTile(int x, int y, float cameraOffsetX, float cameraOffsetY, Drawing drawing){
        WorldTile tile = tiles[x][y];
        if (tile == WorldTile.NONE){
            return;
        }

        DrawingLayers layer = tile.isWall ? DrawingLayers.WALLS : DrawingLayers.FLOOR;
        drawing.drawTexture(tileset.getSpriteForTile(tiles[x][y]), new Vector2(x * TILE_SIZE - cameraOffsetX + tile.xOffset, y * TILE_SIZE - cameraOffsetY + tile.yOffset), layer);
    }
}
