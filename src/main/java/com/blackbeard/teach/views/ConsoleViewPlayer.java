
package com.blackbeard.teach.views;

import java.util.Scanner;

import com.blackbeard.teach.controllers.PlayerController;
import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.models.ValidationErrorModel;

import java.util.List;
import java.util.ArrayList;

import static com.blackbeard.teach.views.colors.*;

public class ConsoleViewPlayer extends PlayerView implements WindowManager {

	private PlayerController playerController;
	private PlayerModel playerModel;
	private Scanner				sc;

    public ConsoleViewPlayer() {
		super();
		sc = new Scanner(System.in);
	}

	/**
	 * This method is the main welcome screen allow you to choose what you intend to do
	 */
	private void showMenu() {
		System.out.print("\033[H\033[2J");
		System.out.println(colors.ANSI_CYAN + "┍----------------------------------------------------------------------------┑\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│          _______.____    __    ____  __  .__   __.   ___________    ____           │\n"
				+ "│         /       |\\   \\  /  \\  /   /  |  | |  \\ |  |  /  _____\\   \\  /   /           │\n"
				+ colors.ANSI_RED +  "│        |   (----`  \\   \\/    \\/   /   |  | |   \\|  | |  |  __  \\   \\/   /        │\n"
				+ colors.ANSI_YELLOW+"│         \\   \\      \\             /    |  | |  . `   | |  | |_  | \\_    _/          │\n"
				+ colors.ANSI_PURPLE+"│      .----)   |       \\    /\\    /     |  | |  |\\   | |  |__|  |    |  |            │\n"
				+ colors.ANSI_BLUE + "│      |_______/         \\__/  \\__/      |__| |__| \\__|  \\______|    |__|            │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                              PRESS ENTER TO CONTINUE                               │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "│                                                                                    │\n"
				+ "┕------------------------------------------------------------------------------------┙" + colors.ANSI_RESET);

	}

	/**
	 * The gets the user input for what class the user wants the player to be.
	 * @return - returns the selected hero class
	 */
	private String	getPlayerClass() {
        String	temp;
		temp = sc.nextLine();
		while (!temp.equals("1") && !temp.equals("2") && !temp.equals("3") && !temp.equals("4")) {
            System.out.print("\033[H\033[2J");
            System.out.println(   "┍---------------------------------------------------------------------------------┑\n"
                    + "│                                    CHOOSE CLASS                                    │\n"
                    + "│ ┍━━━━━━━━━━━━┑         ┍━━━━━━━━━━━━┑         ┍━━━━━━━━━━━━┑        ┍━━━━━━━━━━━━┑ │\n"
                    + "│ │            │         │            │         │            │        │         /  │ │\n"
                    + "│ │            │         │            │         │            │        │        / | │ │\n"
                    + "│ │            │         │            │         │            │        │       /  | │ │\n"
                    + "│ │            │         │       ` ` `│         │            │        │      /   | │ │\n"
                    + "│ │           /│         │      ` ` ` │         │            │        │     /    | │ │\n"
                    + "│ │          / │         │      ~ `---│         │            │        │    /     | │ │\n"
                    + "│ │         /  │         │    ~ ~ /  .│         │            │        │   /      | │ │\n"
                    + "│ │        /   │         │     ~ /. . │         │            │        │  /       | │ │\n"
                    + "│ │       |    │         │   ~  |  . .│         │            │        │ /        | │ │\n"
                    + "│ │       |    │         │     ~ \\  . │         │            │        │|         | │ │\n"
                    + "│ │       |    │         │    ~ ~ \\___│         │            │        │|         | │ │\n"
                    + "│ │       |    │         │       ` ` |│         │           /│        │|         | │ │\n"
                    + "│ │       |    │         │         ` |│         │          / │        │|         | │ │\n"
                    + "│ │       |    │         │           |│         │         /  │        │|         | │ │\n"
                    + "│ │       |    │         │           |│         │        /  |│        │|         | │ │\n"
                    + "│ │       |    │         │           |│         │        |  |│        │ \\        | │ │\n"
                    + "│ │       |    │         │           |│         │        |  |│        │  \\       | │ │\n"
                    + "│ │       |    │         │           |│         │        \\  |│        │   \\      | │ │\n"
                    + "│ │       |    │         │           |│         │         \\  │        │    \\     | │ │\n"
                    + "│ │       |    │         │           |│         │          | │        │     \\    | │ │\n"
                    + "│ │    c=======│         │           |│         │          | │        │      \\   | │ │\n"
                    + "│ │         |  │         │           |│         │          | │        │       \\  | │ │\n"
                    + "│ │         |  │         │           |│         │         ===│        │        \\ | │ │\n"
                    + "│ │         |  │         │           |│         │           |│        │         \\  │ │\n"
                    + "│ ┕━━━━━━━━━━━━┙         ┕━━━━━━━━━━━━┙         ┕━━━━━━━━━━━━┙        ┕━━━━━━━━━━━━┙ │\n"
                    + "│    1 > WarHero             2 > MAGE              3 > Shinobi            4 > Pirate     │\n"
                    + "│                                                                                    │\n"
                    + "│                                                                                    │\n"
                    + "│                                                                                    │\n"
                    + "│                                                                                    │\n"
                    + "┕------------------------------------------------------------------------------------┙");

            temp = sc.nextLine();
		}
		if (temp.equals("1"))
			return ("WarHero");
		else if (temp.equals("2"))
			return ("Mage");
		else if (temp.equals("3"))
			return ("Shinobi");
		return ("Pirate");
	}

	/**
	 * This method creates a player.
	 * @param playerModel - Instance of player model
	 */
	public void	createPlayer(PlayerModel playerModel) {
		String						temp;
		List<ValidationErrorModel>	errors;

		this.playerModel = playerModel;
		System.out.print(ANSI_PURPLE+"Hero Name : "+ANSI_RESET);
		temp = sc.nextLine();
		playerModel.setName(temp);
		this.playerModel.setPClass(getPlayerClass());
		errors = new ArrayList<>();
		if (this.playerController.validatePlayer(errors)) {
			System.out.println(ANSI_RED +"\nERRORS:" + ANSI_RESET);
			for (ValidationErrorModel validate : errors) {
				System.out.println(validate.getField() + " : " +
						validate.getErrorMessage());
			}
			System.out.println(ANSI_RED +"Please try again\nEnter new Player: "+ANSI_RESET);
			createPlayer(playerModel);
			return ;
		}
		if (this.playerController.savePlayer()) {
			System.out.println("Hero Saved");
			this.playerModel = this.playerController.getLastPlayer();
			this.playerController.setPlayer(this.playerModel);
			return ;
		}
		System.out.println(ANSI_RED +"Error saving hero"+ ANSI_RESET);
		createPlayer(this.playerModel);
	}

	/**
	 * This allows the user to either create a player, choose player from existing or Exit the game.
	 * @param controller - instance of player controller
	 * @return - This returns the int for the choice
	 */
	public int	choosePlayer(PlayerController controller) {
		String	temp;
		boolean	withinRange;

		this.playerController = controller;
		showMenu();
		do {
			System.out.println(ANSI_GREEN +"1 > Create player");
			System.out.println(ANSI_BLUE + "2 > Choose saved player");
			System.out.println(ANSI_WHITE + "3 > Exit"+ANSI_RESET);
			System.out.print("Choice : ");
			temp = sc.nextLine();
			withinRange = (temp.equals("1") || temp.equals("2") || temp.equals("3"));
			if (!withinRange)
				System.out.println(ANSI_RED +"\nInvalid choice, choose between 1 - 3"+ANSI_RESET);
		} while (!withinRange);
		return (Integer.parseInt(temp));
	}

	public void refresh() {
	}

	private void printStats() {
		System.out.printf("%-5s", "Rec");
		System.out.printf("%-20s", "Name");
		System.out.printf("%-20s", "Class");
		System.out.printf("%-5s", "lvl");
		System.out.printf("%-5s", "exp");
		System.out.printf("%-5s", "Atck");
		System.out.printf("%-5s", "Df");
		System.out.printf("%-5s", "Hp");
		System.out.println();
		System.out.printf("%-5s", "===");
		System.out.printf("%-20s", "====");
		System.out.printf("%-20s", "=====");
		System.out.printf("%-5s", "===");
		System.out.printf("%-5s", "===");
		System.out.printf("%-5s", "====");
		System.out.printf("%-5s", "==");
		System.out.printf("%-5s", "==");
		System.out.println();
	}

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
			System.out.println(ANSI_RED + "Invalid choice" + colors.ANSI_RESET);
			return (getNumber());
		}
	}

	/**
	 * This displays the players in the DB
	 * @param players - List of players in the DB
	 */
	public void	selectPlayer(List<PlayerModel> players) {
		boolean validInput;
		int		choice;
		int		maxNum;
		int		index;

		if (players.size() == 0) {
			System.out.println(ANSI_RED +"Database is empty" + colors.ANSI_RESET);
			return ;
		}
		do {
			maxNum = 0;
			index = 1;
			System.out.println(colors.ANSI_GREEN + "\nCHOOSE PLAYER" + colors.ANSI_RESET);
			System.out.println("===============");
			printStats();
			for (PlayerModel tempPlayer : players) {
				maxNum = tempPlayer.getRec();
				System.out.printf("%-5d", index++);
				System.out.printf("%-20s", tempPlayer.getName());
				System.out.printf("%-20s", tempPlayer.getPClass());
				System.out.printf("%-5d", tempPlayer.getLevel());
				System.out.printf("%-5d", tempPlayer.getXP());
				System.out.printf("%-5d", tempPlayer.getAttack());
				System.out.printf("%-5d", tempPlayer.getDefence());
				System.out.printf("%-5d", tempPlayer.getHP());
				System.out.println();
			}
			System.out.println("0 (EXIT)");
			System.out.print("Choice : ");
			choice = this.getNumber();
			validInput = (choice >= 0 && choice <= maxNum);
			if (!validInput)
				System.out.println(ANSI_RED + "Invalid option, please try again"+ colors.ANSI_RESET);
		} while (!validInput);
		if (choice > 0) {
			playerController.setPlayer(players.get(choice - 1));
		}
	}
}
