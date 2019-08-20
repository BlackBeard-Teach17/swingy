package com.blackbeard.teach.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.blackbeard.teach.models.MapModel;
import com.blackbeard.teach.views.WindowManager;
import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.views.ConsoleViewPlayer;
import com.blackbeard.teach.views.GameView;
import com.blackbeard.teach.views.ConsoleView;
import com.blackbeard.teach.views.GuiView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameController {
	private final FightController fightController = new FightController(this);
	private final MapModel mapModel = new MapModel(this);
	private PlayerController		hero;
	private PlayerModel				playerModel;
	private PlayerModel				enemyModel;
	private List<PlayerModel>		enemies;
	private WindowManager windowManager;
	private int						width;
	private int						height;
	private char[][] mapView;
	private GameView gameView;
	private int						currentLevel;
	private int						reverseChoice;

	GameController(PlayerController controller, WindowManager pWindowManager) {
		hero = controller;
		enemies = new ArrayList<>();

		this.playerModel = controller.getPlayer();
		if (pWindowManager instanceof ConsoleViewPlayer) {
			windowManager = new ConsoleView();
			gameView = (GameView) windowManager;
		}
		else {
			windowManager = new GuiView();
			gameView = (GameView) windowManager;
		}
	}

	void startGame() {
        initPlayer();
        this.gameView.showMap(this.mapView, this, this.playerModel);
	}

    private void initPlayer() {
        int mapSize;
        int level;

        level = (this.hero.getPlayer().getLevel() == 0) ? 1 : this.hero.getPlayer().getLevel();
        mapSize = (((level - 1) * 5) + 10) - (level % 2);
        this.width = mapSize;
        this.height = mapSize;
        this.playerModel.setX(mapSize / 2);
        this.playerModel.setY(mapSize / 2);
        this.createEnemies();
		mapModel.drawMap();
    }

    void			updatePlay() {
        initPlayer();
    }

	void drawMap() {

		mapModel.drawMap();
	}

	private String getRandomEnemy() {
		Random rand = new Random();
		String[] names = {
			"Admiral Akainu",
			"BlackBeard",
			"Doflamingo",
			"Orochimaru",
			"Pain",
			"HeroKillerStain"
		};
		return (names[rand.nextInt(6)]);
	}

	private String			getClassName() {
		Random rand = new Random();
		String[] pClasses = {
			"WarHero","Shinobi", "Mage", "Pirate"
		};
		return (pClasses[rand.nextInt(4)]);
	}

	private void			setPlayerStats(PlayerModel tempPlayer) {
		Random rand = new Random();

		tempPlayer.setLevel(rand.nextInt(playerModel.getLevel() + 2) + 1);
		tempPlayer.setHP(100);
		switch (tempPlayer.getPClass()) {
			case "WarHero":
				tempPlayer.setXP(450);
				tempPlayer.setAttack(rand.nextInt(70));
				tempPlayer.setDefence(rand.nextInt(70));
				break ;
			case "Shinobi":
				tempPlayer.setXP(150);
				tempPlayer.setAttack(rand.nextInt(50));
				tempPlayer.setDefence(rand.nextInt(5));
				break ;
			case "Mage":
				tempPlayer.setXP(300);
				tempPlayer.setAttack(rand.nextInt(30));
				tempPlayer.setDefence(rand.nextInt(30));
				break ;
			case "Pirate":
				tempPlayer.setXP(100);
				tempPlayer.setAttack(rand.nextInt(50));
				tempPlayer.setDefence(rand.nextInt(50));
		}
		tempPlayer.setXP(tempPlayer.getXP() * tempPlayer.getLevel());
		tempPlayer.setAttack(tempPlayer.getAttack() * tempPlayer.getLevel());
		tempPlayer.setDefence(tempPlayer.getDefence() * (tempPlayer.getLevel() / 2));
	}

	private void			positionPlayerRandomly(PlayerModel tempEnemy) {

		fightController.positionPlayerRandomly(tempEnemy);
	}

	private void			createEnemies() {
		int	numEnemies;

		numEnemies = width;
		for (int x = 0; x < numEnemies; x++) {
			PlayerModel tempEnemy = new PlayerModel(getRandomEnemy(), getClassName());
			setPlayerStats(tempEnemy);
			fightController.positionPlayerRandomly(tempEnemy);
			this.registerEnemy(tempEnemy);
		}
	}

	private void				registerEnemy(PlayerModel enemy) {
		this.enemies.add(enemy);
	}

	public void				setSelection(int choice) {
		switch (choice) {
			case 1:
				this.playerModel.setY(this.playerModel.getY() - 1);
				reverseChoice = 3;
				break ;
			case 2:
				this.playerModel.setX(this.playerModel.getX() + 1);
				reverseChoice = 4;
				break ;
			case 3:
				this.playerModel.setY(this.playerModel.getY() + 1);
				reverseChoice = 1;
				break ;
			case 4:
				this.playerModel.setX(this.playerModel.getX() - 1);
				reverseChoice = 2;
				break ;
			case 10:
				if (windowManager instanceof ConsoleView) {
					this.windowManager = new GuiView();
					this.gameView = (GameView) windowManager;
					this.gameView.showMap(mapView, this, this.playerModel);
				}
				else if (windowManager instanceof GuiView) {
					this.windowManager = new ConsoleView();
					this.gameView = (GameView) windowManager;
					this.gameView.showMap(mapView, this, this.playerModel);
				}
				break ;
		}
		if (choice != 0) {
			this.checkPlayer();
		}
	}

	public void		simulateFight(PlayerModel fightEnemy) {

		fightController.simulateFight(fightEnemy);
	}

	void			fightOver() {
		fightController.fightOver();
	}

	private void		checkPlayer() {
		boolean 	collision;
		PlayerModel	fightEnemy;

		fightEnemy = null;
		collision = false;
		for (PlayerModel tempEnemy : this.enemies) {
			if ((tempEnemy.getX() == this.playerModel.getX()) &&
					(tempEnemy.getY() == this.playerModel.getY())) {
				collision = true;
				fightEnemy = tempEnemy;
				break ;
			}
		}
		if (collision) {
			FightController.enemyEncounter++;
			if (FightController.enemyEncounter == 2) {
				FightController.enemyEncounter = 0;
				fightController.simulateFight(fightEnemy);
				return ;
			}
			this.gameView.makeChoice(fightEnemy, this);
			return ;
		}
		if (this.playerModel.getX() == -1 || this.playerModel.getX() == (this.width) ||
				this.playerModel.getY() == -1 || this.playerModel.getY() == (this.width)) {
			this.hero.drawWin();
			this.gameView.gameLoop(this.playerModel.getName(), true);
			return ;
		}
		mapModel.drawMap();
		this.gameView.updateMap(mapView);
	}

	public void		reverseChoice() {
		this.setSelection(reverseChoice);
	}

}
