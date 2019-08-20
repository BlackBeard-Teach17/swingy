package com.blackbeard.teach.views;

import com.blackbeard.teach.controllers.PlayerController;
import com.blackbeard.teach.models.PlayerModel;

import java.util.List;

public abstract class PlayerView {
	public abstract int choosePlayer(PlayerController controller);
	public abstract void createPlayer(PlayerModel playerModel);
	public abstract void selectPlayer(List<PlayerModel> players);
}
