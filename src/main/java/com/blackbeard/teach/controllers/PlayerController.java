package com.blackbeard.teach.controllers;

import com.blackbeard.teach.views.*;
import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.models.ValidationErrorModel;

import java.util.List;
import java.util.Random;
import java.lang.*;

import static com.blackbeard.teach.views.colors.*;

public class PlayerController {
	private PlayerModel 		player;
	private PlayerModel			p1;
	private PlayerModel pEnemy;
	private GameView gameView;
	private SwingyDB swingyDB;
	private PlayerView 			playerView;
	private GameController controller;
	private WindowManager mainWindowManager;

	public PlayerController(WindowManager windowManager) {
		swingyDB = new SwingyDB();
		playerView = (PlayerView) windowManager;
		this.mainWindowManager = windowManager;
	}

	PlayerController(PlayerModel player1, PlayerModel enemy,
					 GameView gameView, GameController gameController) {
		Random rand = new Random();
		swingyDB = new SwingyDB();

		if (rand.nextInt(2) == 0) {
			p1 = player1;
			pEnemy = enemy;
		}
		else {
			p1 = enemy;
			pEnemy = player1;
		}
		this.gameView = gameView;
		this.mainWindowManager = null;
		controller = gameController;
	}

	public void	simulateFight() {
		int playerNumber;
		int attackValue;

		attackValue = attack(p1);
		takeHit(pEnemy, attackValue);
		playerNumber = 2;
		while (p1.getHP() != 0 && pEnemy.getHP() != 0) {
			if (playerNumber == 1) {
				attackValue = attack(p1);
				takeHit(pEnemy, attackValue);
				playerNumber = 2;
			} else {
				attackValue = attack(pEnemy);
				takeHit(p1, attackValue);
				playerNumber = 1;
			}
		}
		controller.fightOver();
	}

	private int attack(PlayerModel tempPlayer) {
		int		attackValue;
		String	attackMessage;

		attackValue = tempPlayer.getAttack();
		attackMessage = tempPlayer.getName() + " attacks with " + tempPlayer.getAttack() + " points";
		this.gameView.updateFight(attackMessage);
		return (attackValue);
	}

	void	addExperience(PlayerModel playerWon, PlayerModel enemyDefeated) {
		int totalExperience;
		int totalAttack;
		int	nextLevel;

		totalExperience = enemyDefeated.getXP() * 2;
		totalAttack = enemyDefeated.getAttack() / 2;
		playerWon.setXP(playerWon.getXP() + totalExperience);
		playerWon.setAttack(playerWon.getAttack() + totalAttack);
		nextLevel = ((playerWon.getLevel() + 1) * 1000);
	 	nextLevel += (Math.pow(playerWon.getLevel(), 2.0) * 450);
		if (playerWon.getXP() >= nextLevel) {
			playerWon.setLevel(playerWon.getLevel() + 1);
		}
		swingyDB.updateHero(playerWon);
	}

	private void	takeHit(PlayerModel tempPlayer, int attackValue) {
		int		hPoint;
		int		defence;
		String	defenceMessage;

		hPoint = tempPlayer.getHP();
		defence = tempPlayer.getDefence();
		defenceMessage = tempPlayer.getName() + " ";
		if (defence > 0) {
			defenceMessage += "blocks the attack " + defence;
			attackValue -= defence;
		}
		if (attackValue < 0) {
			defenceMessage += " dodges the attack";
			this.gameView.updateFight(defenceMessage);
			return ;
		}
		hPoint -= attackValue;
		if (hPoint <= 0) {
			showLoseMenu(tempPlayer);
			tempPlayer.setHP(0);
			this.gameView.updateFight(defenceMessage);
			return ;
		}
		defenceMessage += " takes " + attackValue + " hit";
		this.gameView.updateFight(defenceMessage);
		tempPlayer.setHP( tempPlayer.getHP() - attackValue);
	}

	private void showLoseMenu(PlayerModel player) {
		System.out.print("\033[H\033[2J");
		System.out.println(   "┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑\n"
				+ "│                                │\n"
				+ "│                                │\n"+ ANSI_RED
				+ "│          "+player.getName()+" HAS DIED         │\n"+ ANSI_YELLOW
				+ "│ There is no honor in dying...  │\n"+ ANSI_RESET
				+ "│                                │\n"
				+ "┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
		System.out.println("Press enter to continue");
	}

	public void drawWin() {
		System.out.print("\033[H\033[2J");
		System.out.println(   "┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑\n"
				+ "│                                │\n"
				+ "│                                │\n"+ANSI_BLUE
				+ "│            YOU WIN!!           │\n"
				+ "│     Soldier on to show you are │\n"
				+ "│     the strongest warrior!!    │\n"+ANSI_RESET
				+ "┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
		System.out.println("Press enter to continue");
	}

	public void selectArtifact() {
	}

	public PlayerModel	getLastPlayer() {

        List<PlayerModel> localPlayers = swingyDB.getHeros();
		return (localPlayers.get(localPlayers.size()  - 1));
	}

	public boolean	savePlayer() {
		return (swingyDB.insertHero(this.player));
	}

	public boolean validatePlayer(List<ValidationErrorModel> errors) {
		this.player.setLevel(1);
		this.player.setHP(100);
		switch (this.player.getPClass()) {
			case "WarHero":
				this.player.setXP(250);
				this.player.setAttack(15);
				this.player.setDefence(10);
				break ;
			case "Shinobi":
				this.player.setXP(150);
				this.player.setAttack(5);
				this.player.setDefence(5);
				break ;
			case "Mage":
				this.player.setXP(300);
				this.player.setAttack(25);
				this.player.setDefence(25);
				break ;
			case "Pirate":
				this.player.setXP(400);
				this.player.setAttack(10);
				this.player.setDefence(5);
				break;
		}
		return (!ValidateController.runValidator(errors, player));
	}

	public void createPlayer() {
		this.player = new PlayerModel();
		this.playerView.createPlayer(player);
	}

	public void setPlayer(PlayerModel playerModel) {
		this.player = playerModel;
		this.controller = new GameController(this, this.mainWindowManager);
		this.controller.startGame();
	}

	public void	selectPlayer() {
		List<PlayerModel> players = swingyDB.getHeros();
		this.playerView.selectPlayer(players);
	}

	public void choosePlayer() {
		int choice;

		if ((choice = this.playerView.choosePlayer(this)) == 1) {
			this.createPlayer();
		}
		else if (choice == 2) {
			this.selectPlayer();
		}
		else if (playerView instanceof ConsoleViewPlayer) {
			System.out.println(ANSI_GREEN+"Thank You For Playing!!");
		}
	}

	PlayerModel	getPlayer() {
		return (this.player);
	}
}
