package com.tower.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tower.game.drawing.Drawing;
import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.ui.hud.Hud;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.World;

public class TowerGame extends ApplicationAdapter {

	
	@Override
	public void create () {
		// init singletons
		Drawing.getInstance();
		World.getInstance();
		Hud.getInstance();
		World.getInstance().getCurrentFloor().init();
	}

	@Override
	public void render () {
		WorldObjectManager.getInstance().update();
		World.getInstance().render();
		Drawing.getInstance().renderUpdate();
		Hud.getInstance().update();

		// temporary input
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)){
			World.getInstance().getCurrentFloor().moveCoordinatesBy(0, -1);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
			World.getInstance().getCurrentFloor().moveCoordinatesBy(0, +1);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
			World.getInstance().getCurrentFloor().moveCoordinatesBy(-1, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
			World.getInstance().getCurrentFloor().moveCoordinatesBy(1, 0);
		}
		DebugUtils.checkFps();
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
