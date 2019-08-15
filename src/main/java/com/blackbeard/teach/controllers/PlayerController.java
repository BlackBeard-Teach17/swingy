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
	private PlayerModel			p2;
	private GameView gameView;
	private PlayerView 			playerView;
	private GameController controller;
	private WindowManager mainWindowManager;
	private SwingyDB swingyDB;

	/**
	 * Player Controller constructor, initializes instances of SWINGYDB, PlayerView and window manager
	 * @param windowManager - Initializes instance of the views to be displayed
	 */
	public PlayerController(WindowManager windowManager) {
		swingyDB = new SwingyDB();
		playerView = (PlayerView) windowManager;
		this.mainWindowManager = windowManager;
	}

	/**
	 * PlayerController constructor that takes two player model objects to be used as player and enemy
	 * @param player1 - Will be used as either player or enemy
	 * @param player2 - Will be used as either player or enemy
	 * @param gameView - Instance of game view
	 * @param gameController - Game Controller to handle the game dynamics
	 */
	PlayerController(PlayerModel player1, PlayerModel player2,
					 GameView gameView, GameController gameController) {
		Random rand = new Random();
		swingyDB= new SwingyDB();

		if (rand.nextInt(2) != 0) {
			p1 = player2;
			p2 = player1;
		} else {
			p1 = player1;
			p2 = player2;
		}
		this.gameView = gameView;
		this.mainWindowManager = null;
		controller = gameController;
	}

	/**
	 * This method  simulates the fight between player and enemy each
	 * taking turn as long as the other has Health
	 */
	void	simulateFight() {
		int playerNumber;
		int attackValue;

		attackValue = attack(p1);
		takeDmg(p2, attackValue);
		playerNumber = 2;
		while (p1.getHP() != 0 && p2.getHP() != 0) {
			if (playerNumber == 1) {
				attackValue = attack(p1);
				takeDmg(p2, attackValue);
				playerNumber = 2;
			} else {
				attackValue = attack(p2);
				takeDmg(p1, attackValue);
				playerNumber = 1;
			}
		}
		controller.fightOver();
	}

	/**
	 * This method shows player attack and logs the attack type
	 * @param tempPlayer - Player model used to get Attack
	 * @return - returns the attack value
	 */
	private int attack(PlayerModel tempPlayer) {
		int		attackValue;
		String	attackMessage;

		attackValue = tempPlayer.getAttack();
		attackMessage = tempPlayer.getName() + " attacks with " + tempPlayer.getAttack() + " points";
		this.gameView.updateFight(attackMessage);
		return (attackValue);
	}

	/**
	 * Adds XP based on the enemy level
	 * @param playerWon - Updates the Hero Stats and adds XP
	 * @param enemyDefeated - Adds XP to the winning player
	 */
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

	/**
	 * This methods is responsible for inflicting player damage.
	 * @param tempPlayer - This gets HP and Def(to negate attack)
	 * @param attackValue - amount of damage to take
	 */
	private void takeDmg(PlayerModel tempPlayer, int attackValue) {
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

	public void selectArtifact() {
	}

	/**
	 * This method retrieves Heroes from the DB
	 * @return - returns a list of heroes from the DB.
	 */
	public PlayerModel	getLastPlayer() {

		List<PlayerModel> localPlayers = swingyDB.getHeros();
		return (localPlayers.get(localPlayers.size()  - 1));
	}

	/**
	 * Inserts hero into database
	 * @return - Success on successful insertion or throws a SQLException if any
	 * errors are found.
	 */
	public boolean	savePlayer() {
		return (swingyDB.insertHero(this.player));
	}
//	public boolean deletePlayer()
//	{
//		return (swingyDB.deletePlayer(this.player));
//	}
	/**
	 * This sets and validates hero class
	 * @param errors - returns errors if any are found
	 * @return -  returns errors if any are found
	 */
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

	/**
	 * Create a new player to be inserted to the DB
	 */
	public void createPlayer() {
		this.player = new PlayerModel();
		this.playerView.createPlayer(player);
	}

	/**
	 * Initializes the player selected and starts the game
	 * @param playerModel - instance of the player selected
	 */
	public void setPlayer(PlayerModel playerModel) {
		this.player = playerModel;
		this.controller = new GameController(this, this.mainWindowManager);
		this.controller.startGame();
	}

	/**
	 * Retrieves the players that are available in the DB.
	 */
	public void	selectPlayer() {
		List<PlayerModel> players = swingyDB.getHeros();
		this.playerView.selectPlayer(players);
	}

	/**
	 * This method takes handles the choice selected and executes the required method
	 */
	public void choosePlayer() {
		int choice;

		if ((choice = this.playerView.choosePlayer(this)) == 1) {
			this.createPlayer();
		}
		else if (choice == 2) {
			this.selectPlayer();
		}
		else if (choice == 3)
		{
			SwingyDB swingyDB = new SwingyDB();
			swingyDB.deleteTable();
		}
		else if (playerView instanceof ConsoleViewPlayer) {
			System.out.println(ANSI_GREEN+"Thank You For Playing!!");
		}
	}

	PlayerModel	getPlayer() {
		return (this.player);
	}
}
