package com.tower.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.tower.game.drawing.Drawing;
import com.tower.game.objects.entites.AnimatorFactory;
import com.tower.game.objects.objectBody.ObjectBody;
import com.tower.game.objects.world.WorldObjectManager;
import com.tower.game.ui.hud.Hud;
import com.tower.game.utils.DebugUtils;
import com.tower.game.world.World;
import com.tower.game.world.enums.WorldDirection;

public class TowerGame extends ApplicationAdapter {

	
	@Override
	public void create () {
		// init singletons
		Drawing.getInstance();
		World.getInstance();
		Hud.getInstance();
		World.getInstance().getCurrentFloor().init();
	}
	ObjectBody player = new ObjectBody(new Vector2(128, 128), WorldDirection.UP, AnimatorFactory.getAnimatorForPlayer());
	@Override
	public void render () {
		WorldObjectManager.getInstance().update();
		World.getInstance().render();
		Drawing.getInstance().renderUpdate();
		Hud.getInstance().update();
		player.update();
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.move(WorldDirection.UP, 1, 15);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			player.move(WorldDirection.DOWN, 1, 15);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			player.move(WorldDirection.LEFT, 1, 15);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			player.move(WorldDirection.RIGHT, 1, 15);
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
