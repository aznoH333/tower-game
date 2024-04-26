package com.tower.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.drawing.DrawingLayers;
import com.tower.game.drawing.WorldViewportType;
import com.tower.game.world.World;

public class TowerGame extends ApplicationAdapter {

	
	@Override
	public void create () {
		// init singletons
		Drawing.getInstance();
		World.getInstance();

	}

	@Override
	public void render () {
		World.getInstance().render();
		Drawing.getInstance().renderUpdate();
		Drawing.getInstance().drawTexture("d", new Vector2(0.0f,0.0f), DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);
		Drawing.getInstance().drawTexture("d", new Vector2(0.0f,200.0f), DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);
		Drawing.getInstance().drawTexture("d", new Vector2(0.0f,-200.0f), DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);

		Drawing.getInstance().drawTexture("d", new Vector2(-200.0f,0.0f), DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);
		Drawing.getInstance().drawTexture("d", new Vector2(200.0f,0.0f), DrawingLayers.WALLS, WorldViewportType.HUD_LEFT);


		// kill app if escape is pressed
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
	
	@Override
	public void dispose () {
		Drawing.getInstance().dispose();
	}

	@Override
	public void resize(int width, int height){
		Drawing.getInstance().resize(width, height);
	}
}
