package com.blackbeard.teach.views;

import com.blackbeard.teach.controllers.GameController;
import com.blackbeard.teach.models.PlayerModel;

import java.util.Scanner;

import static com.blackbeard.teach.views.colors.*;

/**
 * The ConsoleView Class extends abstract class GameView. This is the class that will show the map on the
 * terminal, get user input, then update the map based on the choices of the user.
 */
public class ConsoleView extends GameView implements WindowManager {
	private char[][] map;
	private GameController gameController;
	private PlayerModel playerModel;

	private int		getNumber() {
		String	sChoice;
		int		choice;
		Scanner	sc = new Scanner(System.in);

		try {
			sChoice = sc.nextLine();
			choice = Integer.parseInt(sChoice);
			return (choice);
		}
		catch (Exception err) {
			System.out.println(ANSI_RED +"Invalid choice" + ANSI_RESET);
			return (getNumber());
		}
	}

	/**
	 * Shows the player on the map, the hero stats and available commands
	 */
	private void	setMapVisible() {
		int		height;
		int		width;
		int		choice;
		boolean	keyOkay;

		height = this.map.length;
		width = this.map[0].length;
		System.out.print(ANSI_BLUE+"Hero: " + this.playerModel.getName());
		System.out.print(ANSI_GREEN+" Health: " + this.playerModel.getHP());
		System.out.print(ANSI_CYAN+" Level: " + this.playerModel.getLevel());
		System.out.print(ANSI_PURPLE+" Class: "+ this.playerModel.getPClass());
		System.out.println(ANSI_RED+" Exp: " + this.playerModel.getXP() + ANSI_RESET);
		for (int y = 0; y < height; y++) { //This is where the map is being printed. It ain't fancy but works
			for (int x = 0; x < width; x++) {
				System.out.print(ANSI_BLUE+map[y][x]+ANSI_RESET);
			}
			System.out.println();
		}
        new Scanner(System.in);
		do {
			System.out.println("Commands:");
			System.out.println("==========");
			System.out.println(ANSI_YELLOW+"1 > North");
			System.out.println(ANSI_PURPLE+"2 > East");
			System.out.println(ANSI_CYAN+"3 > South");
			System.out.println(ANSI_BLUE+"4 > West");
			System.out.println("========");
			System.out.println(ANSI_GREEN+"10 > Change to GUI");
			System.out.println("========");
			System.out.println(ANSI_RED+"0 > EXIT");
			System.out.print("Choice : ");
			choice = this.getNumber();
			keyOkay = ((choice >= 0 && choice <= 4) ||
					(choice == 10));
		} while (!keyOkay);
		this.gameController.setSelection(choice);
	}

	/**
	 * Updates the map after player movement and/or after player wins fight
	 * @param map - Takes the current map and displays it.
	 */
	public void updateMap(char map[][]) {
		this.map = map;
		System.out.print("\033[H\033[2J");
		this.setMapVisible();
	}

	/**
	 * Sets the map, gameController and playerModel in order to determine the size of the map
	 * the number of enemies to appear on the map
	 * @param map - map to be displayed
	 * @param gameController - game controller to be used to handle game dynamics
	 * @param playerModel -
	 */
	public void showMap(char map[][], GameController gameController, PlayerModel playerModel) {
		this.map = map;
		this.playerModel = playerModel;
		this.gameController = gameController;
		System.out.print("\033[H\033[2J");
		this.setMapVisible();
	}

	public void prepareFight(String prepareString) {
		System.out.print("\033[H\033[2J");
		System.out.println(prepareString);
	}

	public void updateFight(String attackMessage) {
		System.out.println(attackMessage);
	}

	public void showMessage(String message, boolean exitProgram) {
		Scanner sc = new Scanner(System.in);

		System.out.println(message);
		sc.nextLine();
		if (exitProgram) {
			System.exit(0);
		}
	}

	public void	makeChoice(PlayerModel enemy, GameController gameControllerTemp) {
		int choice;

        System.out.print("\033[H\033[2J");
        System.out.println(   "┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑\n"
                + "│                                │\n"
                + "│                                │\n"+ANSI_RED
                + "│  Do you want to start battle with  │\n"+ANSI_CYAN
                + "│" + enemy.getName()+" level:"+enemy.getLevel() +"?│\n"
                + "│                                │\n"
                + "┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");

		System.out.println(ANSI_RED+"1 > Fight");
		System.out.println(ANSI_YELLOW+"2 > Run");
		choice = this.getNumber();
		if (choice == 1) {
			gameControllerTemp.simulateFight(enemy);
			return ;
		}
        System.out.print("\033[H\033[2J");
        System.out.println(   "┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑\n"
                + "│                                │\n"
                + "│                                │\n"
                + "│  This is no world for cowards!!│\n" + ANSI_CYAN
                + "│  Next time we will force you   │\n"
                + "│  into battle.  COWARD!!        │\n" + ANSI_RESET
                + "┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
        Scanner	sc = new Scanner(System.in);
        String w = sc.nextLine();
		gameControllerTemp.reverseChoice();
	}

	public void refresh() {
	}
}
