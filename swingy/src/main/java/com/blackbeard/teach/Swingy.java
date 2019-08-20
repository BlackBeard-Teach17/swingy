package com.blackbeard.teach;

import com.blackbeard.teach.controllers.PlayerController;
import com.blackbeard.teach.controllers.ValidateController;
import com.blackbeard.teach.views.ConsoleViewPlayer;
import com.blackbeard.teach.views.GuiViewPlayer;
import com.blackbeard.teach.views.WindowManager;

/**
 * Swingy main function.
 * This is the entry point
 */
public class Swingy {

	private static void showUsage()
	{
		System.out.println("Usage: java -jar swingy.jar [console or gui]");
	}
	
	public static void		main(String[] args) {
		PlayerController playerController;
		WindowManager windowManager;

		/**
		 * This will check the first argument if its Console, GUI or Drop DB options
		 */
		new ValidateController();
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("gui")) {
				windowManager = new GuiViewPlayer();
			}
			else if (args[0].equalsIgnoreCase("console")) {
				windowManager = new ConsoleViewPlayer();
			}
			else {
				showUsage();
				return ;
			}
		}
		else {
			System.out.println("Defaulting to console");
			windowManager = new ConsoleViewPlayer();
		}
		playerController = new PlayerController(windowManager);
		playerController.choosePlayer();
	}
}
