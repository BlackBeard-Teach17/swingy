package com.blackbeard.teach.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import com.blackbeard.teach.views.WindowManager;
import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.views.ConsoleViewPlayer;
import com.blackbeard.teach.views.GameView;
import com.blackbeard.teach.views.ConsoleView;
import com.blackbeard.teach.views.GuiView;


public class GameController {
	private PlayerController		hero;
	private PlayerModel				playerModel;
	private PlayerModel				enemyModel;
	private List<PlayerModel>		enemies;
	private static int enemyEncounter;
	private WindowManager windowManager;
	private int						width;
	private int						height;
	private char[][] mapView;
	private GameView gameView;
	private PlayerController		fightController;
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
        updatePlayState();
        this.gameView.showMap(this.mapView, this, this.playerModel);
	}

    private void updatePlayState() {
        int sizeMap;
        int level;

        level = (this.hero.getPlayer().getLevel() == 0) ? 1 : this.hero.getPlayer().getLevel();
        sizeMap = (((level - 1) * 5) + 10) - (level % 2);
        this.width = sizeMap;
        this.height = sizeMap;
        this.playerModel.setX(sizeMap / 2);
        this.playerModel.setY(sizeMap / 2);
        this.createEnemies();
        this.drawMap();
    }

    private void			updatePlay() {
        updatePlayState();
    }

	private void drawMap() {
		int		wholeArea;

		wholeArea = this.width + 2;
		mapView = new char[wholeArea][wholeArea];
		for (int y = 0; y < wholeArea; y++) {
			for (int x = 0; x < wholeArea; x++) {
				mapView[y][x] = ' ';
			}
		}
		mapView[this.playerModel.getY() + 1][this.playerModel.getX() + 1] = 'P';
		for (PlayerModel tempEnemy : this.enemies) {
			mapView[tempEnemy.getY() + 1][tempEnemy.getX() + 1] = 'E';
		}
		for (int y = 0; y < wholeArea; y++) {
			mapView[y][0] = '#';
			mapView[y][wholeArea - 1] = '#';
			if (y == 0 || (y == wholeArea - 1)) {
				for (int x = 1; x < (wholeArea - 1); x++) {
					mapView[y][x] = '#' + ' ';
				}
			}
		}
	}

	private String			getRandomName() {
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
				tempPlayer.setDefence(5);
				break ;
			case "Mage":
				tempPlayer.setXP(300);
				tempPlayer.setAttack(rand.nextInt(30));
				tempPlayer.setDefence(rand.nextInt(30));
				break ;
			case "Pirate":
				tempPlayer.setXP(100);
				tempPlayer.setAttack(40);
				tempPlayer.setDefence(40);
		}
		tempPlayer.setXP(tempPlayer.getXP() * tempPlayer.getLevel());
		tempPlayer.setAttack(tempPlayer.getAttack() * tempPlayer.getLevel());
		tempPlayer.setDefence(tempPlayer.getDefence() * (tempPlayer.getLevel() / 2));
	}

	private void			positionPlayerRandomly(PlayerModel tempEnemy) {
		Random	rand = new Random();
		boolean	conflict;
		int		i;

        i = -1;
        conflict = isConflict(tempEnemy, rand, i);
        while (conflict) {
			i = -1;
			conflict = isConflict(tempEnemy, rand, i);
        }
	}

    private boolean isConflict(PlayerModel tempEnemy, Random rand, int i) {
        int maxEnemies;
        boolean conflict;
        maxEnemies = this.enemies.size();
        tempEnemy.setPosition(rand.nextInt(this.width), rand.nextInt(this.height));
        conflict = ((tempEnemy.getX() == this.playerModel.getX()) &&
                (tempEnemy.getY() == this.playerModel.getY()));
        while (!conflict && ++i < maxEnemies) {
            conflict = ((tempEnemy.getX() == this.enemies.get(i).getX()) &&
                    tempEnemy.getY() == this.enemies.get(i).getY());
        }
        return conflict;
    }

    private void			createEnemies() {
		int	numEnemies;
		Random rand = new Random();

		numEnemies = width;
		for (int x = 0; x < numEnemies; x++) {
			PlayerModel tempEnemy = new PlayerModel(getRandomName(), getClassName());
			setPlayerStats(tempEnemy);
			positionPlayerRandomly(tempEnemy);
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
		String 				prepareFight;

        enemyEncounter = 0;
		enemyModel = fightEnemy;
		prepareFight = this.playerModel.getName() + " vs " +
			fightEnemy.getName() + "\n";
		prepareFight += "======================================";
		this.gameView.prepareFight(prepareFight);
		fightController = new PlayerController(this.playerModel, fightEnemy, this.gameView, this);
		fightController.simulateFight();
	}

	void			fightOver() {
		if (playerModel.getHP() > 0) {
			fightController.addExperience(this.playerModel, enemyModel);
		}
		if (enemyModel.getHP() <= 0) {
			for (Iterator<PlayerModel> iter = enemies.listIterator(); iter.hasNext();) {
				PlayerModel tempEnemy = iter.next();
				if (enemyModel == tempEnemy) {
					iter.remove();
				}
			}
		}
		if (this.playerModel.getHP() > 0) {
			this.gameView.showMessage(this.playerModel.getName() + " won the fight ", false);
			if (currentLevel != this.playerModel.getLevel()) {
				currentLevel = this.playerModel.getLevel();
				this.updatePlay();
				this.gameView.updateMap(mapView);
				return ;
			}
			this.drawMap();
			this.gameView.updateMap(mapView);
			return ;
		}
		this.gameView.showMessage(this.playerModel.getName() + " lost fight", true);
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
			enemyEncounter++;
			if (enemyEncounter == 2) {
				enemyEncounter = 0;
				simulateFight(fightEnemy);
				return ;
			}
			this.gameView.makeChoice(fightEnemy, this);
			return ;
		}
		if (this.playerModel.getX() == -1 || this.playerModel.getX() == (this.width) ||
				this.playerModel.getY() == -1 || this.playerModel.getY() == (this.width)) {

			this.gameView.showMessage(this.playerModel.getName() + " WON", true);
			return ;
		}
		this.drawMap();
		this.gameView.updateMap(mapView);
	}

	public void		reverseChoice() {
		this.setSelection(reverseChoice);
	}
}
