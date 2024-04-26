package com.tower.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tower.game.drawing.Drawing;
import com.tower.game.ui.hud.Hud;
import com.tower.game.world.World;

public class TowerGame extends ApplicationAdapter {

	
	@Override
	public void create () {
		// init singletons
		Drawing.getInstance();
		World.getInstance();
		Hud.getInstance();

	}

	@Override
	public void render () {
		World.getInstance().render();
		Drawing.getInstance().renderUpdate();
		Hud.getInstance().update();


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
