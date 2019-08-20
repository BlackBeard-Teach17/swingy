package com.blackbeard.teach.views;

import com.blackbeard.teach.controllers.GameController;
import com.blackbeard.teach.models.PlayerModel;

/**
 * Why did I Use an abstract class instead of and Interface? IDK most likely because I wanted non static field (Which I ended up
 * not doing) I wanted these fields to be accessible and be able to modify the object they belong to.
 * Oh well this is not the best of explanations but will work for now
 */
public abstract class GameView {
	public abstract void showMap(char[][] map, GameController gameController, PlayerModel playerModel);
	public abstract void updateMap(char[][] map);
	public abstract void prepareFight(String prepareString);
	public abstract void updateFight(String updateString);
	public abstract void gameLoop(String message, boolean exitProg);
	public abstract void makeChoice(PlayerModel enemy, GameController gameController);
}
