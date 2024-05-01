package com.tower.game.world;

import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.utils.GameConstants;

public class TowerRoom {
    private final RoomContents roomContents;
    private final RoomTileset tileset = new RoomTileset(TilesetType.GREEN_DEFAULT);
    private final RoomArchetype archetype;
    private boolean entered = false;
    private boolean discovered = false;



    public RoomTileset getTileset() {
        return tileset;
    }


    public TowerRoom(RoomContents roomContents, RoomArchetype archetype){
        this.roomContents = roomContents;
        this.archetype = archetype;
    }

    public void renderRoom(float cameraOffsetX, float cameraOffsetY){
        Drawing drawing = Drawing.getInstance();
        for (int x = 0; x < GameConstants.TILES_IN_ROOM; x++){
            for (int y = 0; y < GameConstants.TILES_IN_ROOM; y++) {
                drawTile(x, y, cameraOffsetX, cameraOffsetY, drawing);
            }
        }
    }

    private void drawTile(int x, int y, float cameraOffsetX, float cameraOffsetY, Drawing drawing){
        WorldTile tile = roomContents.tiles[x][y];
        DrawingLayers layer = tile.isWall ? DrawingLayers.WALLS : DrawingLayers.FLOOR;
        drawing.drawTexture(tileset.getSpriteForTile(tile, roomContents.tileRandomization[x][y]), new Vector2(x * GameConstants.TILE_SIZE - cameraOffsetX, y * GameConstants.TILE_SIZE - cameraOffsetY), layer);
    }

    public String getMinimapSprite(){
        if (entered){
            return archetype.mapMarker;
        }
        return "minimap_markers_3";
    }

    public void markAsDiscovered(){
        this.discovered = true;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void markAsEntered(){
        this.discovered = true;
        this.entered = true;
    }
}
