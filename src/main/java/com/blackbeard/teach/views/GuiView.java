package com.blackbeard.teach.views;

import com.blackbeard.teach.controllers.GameController;
import com.blackbeard.teach.models.PlayerModel;

import	java.awt.*;
import	javax.swing.*;
import	java.awt.event.*;

/**
 * GUI implementation using the SWING Library
 */
public class GuiView extends GameView implements WindowManager {
	private char[][] map;
	private PlayerModel playerModel;
	private GameController gameController;
	private JFrame			frame;
	private JPanel			mainPanel;
	private JPanel			btnPanel;
	private JPanel			btnHolder;
	private PlayerModel		fightEnemy;
	private JTextArea		txtArea;

    /**
     * Prints the heading on title bar and sets the dimensions
     * @return - the heading along with its alignment, dimensions and label.
     */
	private JLabel	getHeading() {
		JLabel	lblHeading;

		lblHeading = new JLabel("HERO");
		lblHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblHeading.setPreferredSize(new Dimension(200, 50));
		return (lblHeading);
	}

    /**
     * Gets the given label of the method
     * @param lbl - string parameter that will be used to construct a label
     * @param txt - string parameter that will be used to construct a label
     * @return - returns a given label
     */
	private JLabel	getLabel(String lbl, String txt) {
		JLabel	lblGiven;

		lblGiven = new JLabel(lbl + " : " + txt);
		lblGiven.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblGiven.setPreferredSize(new Dimension(200, 40));
		return (lblGiven);
	}

    /**
     * Adds the player info attributes to the panel and returns the panel populated with the info
     * @return - returns the panel populated with player
     */
	private JPanel	playerInfo() {
		JPanel		playerInfoPanel;

		playerInfoPanel = new JPanel();
		playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.PAGE_AXIS));
		playerInfoPanel.setPreferredSize(new Dimension(200, 400));
		playerInfoPanel.setBackground(Color.white);
		playerInfoPanel.add(getHeading());
		playerInfoPanel.add(getLabel("Name", this.playerModel.getName()));
		playerInfoPanel.add(getLabel("Player Class", this.playerModel.getPClass()));
		playerInfoPanel.add(getLabel("Experience", String.valueOf(this.playerModel.getXP())));
		playerInfoPanel.add(getLabel("Level", String.valueOf(this.playerModel.getLevel())));
		playerInfoPanel.add(getLabel("Attack", String.valueOf(this.playerModel.getAttack())));
		playerInfoPanel.add(getLabel("Defence", String.valueOf(this.playerModel.getDefence())));
		return (playerInfoPanel);
	}

    /**
     * Builds the map
     * @return - returns the y-axis of the map
     */
	private String	stringMap() {
		StringBuilder oneString;
		int 	height;

		height = map.length;
		oneString = new StringBuilder();
		String tempString;
		for (int y = 0; y < height; y++) {
			tempString = String.valueOf(map[y]);
			oneString.append(tempString).append("\n");
		}
		return (oneString.toString());
	}

    /**
     * Creates a generic button that can be used tailored to specific needs
     * @param btnString - name of the button(what is displayed)
     * @return - returns button object with name
     */
	private JButton	btnGiven(String btnString) {
		JButton	tempBtn;

		tempBtn = new JButton(btnString);
		tempBtn.addActionListener(new btnPressed());
		return (tempBtn);
	}

    /**
     * Creates the four directional buttons that will be displayed. Then adds them to the panel
     * @return - returns the panel with the given buttons
     */
	private	JPanel	setMoveBtn() {
		JPanel	btnTempPanel;

		btnTempPanel = new JPanel();
		btnTempPanel.setLayout(new BorderLayout());
		btnTempPanel.setPreferredSize(new Dimension(200, 150));
		btnTempPanel.add(btnGiven("NORTH"), BorderLayout.NORTH);
		btnTempPanel.add(btnGiven("WEST"), BorderLayout.WEST);
		btnTempPanel.add(btnGiven("EAST"), BorderLayout.EAST);
		btnTempPanel.add(btnGiven("SOUTH"), BorderLayout.SOUTH);
		return (btnTempPanel);
	}

    /**
     * Creates the fight or run button one under the other
     * @return - returns the created button objects
     */
	private JPanel	setChoiceBtn() {
		JPanel	btnTempPanel;

		btnTempPanel = new JPanel();
		btnTempPanel.setLayout(new BorderLayout());
		btnTempPanel.setPreferredSize(new Dimension(200, 150));
		btnTempPanel.add(btnGiven("FIGHT"), BorderLayout.NORTH);
		btnTempPanel.add(btnGiven("RUN"), BorderLayout.SOUTH);
		return (btnTempPanel);
	}

    /**
     *
     * @return
     */
	private JPanel	movePanel() {
		JPanel	flowPanel;

		flowPanel = new JPanel(new BorderLayout());
		btnPanel = setMoveBtn();
		flowPanel.add(btnPanel, BorderLayout.NORTH);
		flowPanel.add(btnGiven("Switch to Console"), BorderLayout.SOUTH);
		btnHolder = flowPanel;
		return (flowPanel);
	}

    /**
     * Adds functionality to the btn to perform certain actions when clicked
	 *
     */
	private class btnPressed implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "NORTH":
					gameController.setSelection(1);
					break ;
				case "WEST":
					gameController.setSelection(4);
					break ;
				case "EAST":
					gameController.setSelection(2);
					break ;
				case "SOUTH":
					gameController.setSelection(3);
					break ;
				case "FIGHT":
					gameController.simulateFight(fightEnemy);
					break ;
				case "RUN":
					gameController.reverseChoice();
					break ;
				case "Switch to Console":
					frame.setVisible(false);
					frame.dispose();
					gameController.setSelection(10);
					break ;
			}
		}
	}

    /**
     * Displays the map, player info, directional move panel and a text scroll
     */
	private void	viewMap() {
		JScrollPane	txtScroll;

		this.txtArea = new JTextArea(stringMap());
		this.txtArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		this.txtArea.setEditable(false);
		this.mainPanel.add(playerInfo(), BorderLayout.WEST);
		this.mainPanel.add(movePanel(), BorderLayout.EAST);
		txtScroll = new JScrollPane(this.txtArea);
		this.mainPanel.add(txtScroll, BorderLayout.CENTER);	
	}

    /**
     * Opens the game play window with the provided title
     */
	private void	init() {
		this.frame = new JFrame("Colosseum");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainPanel = new JPanel(new BorderLayout());
	}

    /**
     * Sets the window visibility of the objects
     */
	private void	setVisible() {
		this.frame.add(mainPanel);
		this.frame.setPreferredSize(new Dimension(800, 800));
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	public void		showMap(char[][] map, GameController gameController, PlayerModel playerModel) {
		this.map = map;
		this.playerModel = playerModel;
		this.gameController = gameController;
		this.init();
		this.viewMap();
		this.setVisible();
	}

	/**
	 * Updates the map after and redraws it on the panel
	 * @param map - The current map state
	 */
	public void	updateMap(char[][] map) {
		this.map = map;
		this.mainPanel.removeAll();
		this.viewMap();
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}

	/**
	 * This methods updates the display to display fight sequence
	 * @param prepareString - String to show who is fighting who
	 */
	public void	prepareFight(String prepareString) {
		this.btnHolder.removeAll();
		this.txtArea.setText(prepareString);
		this.txtArea.update(this.txtArea.getGraphics());
		this.btnHolder.revalidate();
		this.btnHolder.repaint();
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}

	/**
	 * Updates the user of the current situation of the fight and displays the relevant
	 * fight messages
	 * @param updateString - String parameter to be displayed in context of the fight
	 */
	public void	updateFight(String updateString) {
		String currentString;

		currentString = this.txtArea.getText() + "\n";
		currentString += updateString;
		this.txtArea.setText(currentString);
		this.txtArea.update(this.txtArea.getGraphics());
	}

	public void	showMessage(String message, boolean exitProgram) {
		JOptionPane.showMessageDialog(this.frame, message, "Information",
				JOptionPane.INFORMATION_MESSAGE);
		if (exitProgram) {
			this.frame.setVisible(false);
			this.frame.dispose();
		}
	}

	/**
	 * This gets the enemy stats and displays the when an enemy is encountered
	 * @return - returns the enemy with its stats to be displayed
	 */
	private String	getEnemyStats() {
		String enm;

		enm = "Do you want to fight " + this.fightEnemy.getName() + "\n"+
			"Experience : " + this.fightEnemy.getXP() + "\n" +
			"Level : " + this.fightEnemy.getLevel() + "\n" +
			"Attack : " + this.fightEnemy.getAttack() + "\n" +
			"Defence : " + this.fightEnemy.getDefence();
		return (enm);
	}

	/**
	 * This will get the user input choice
	 * @param enemy - Instance of enemy encountered
	 * @param gameController - Instance of gameController
	 */
	public void	makeChoice(PlayerModel enemy, GameController gameController) {
		this.gameController = gameController;
		this.fightEnemy = enemy;
		this.txtArea.setText(getEnemyStats());
		this.txtArea.update(this.txtArea.getGraphics());
		btnHolder.removeAll();
		btnPanel = setChoiceBtn();
		btnHolder.add(btnPanel, BorderLayout.NORTH);
		btnHolder.revalidate();
		btnHolder.repaint();
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}

	public void refresh() {

	}
}
