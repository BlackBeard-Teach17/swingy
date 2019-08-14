package com.blackbeard.teach.views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.blackbeard.teach.controllers.PlayerController;
import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.models.ValidationErrorModel;

import java.util.List;
import java.util.ArrayList;

public class GuiViewPlayer extends PlayerView implements WindowManager {

	private PlayerController playerController;
	private PlayerModel playerModel;
	private JFrame 	frame;
	private JPanel 	pnl = new JPanel();
	private int 	returnNum;
	private JTextField	nameTxt;
	private JComboBox<String> classType;
	private JLabel		errorsLabel;

	public GuiViewPlayer() {
		super();
		returnNum = 3;
		frame = new JFrame("Simple Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This creates a player instance, sets the bounds and shows the class
	 * options.
	 * @param playerModel - Instance of player
	 */
	public void	createPlayer(PlayerModel playerModel) {
		frame = new JFrame("New Player");
		JLabel		nameLabel;
		JLabel		classLabel;
		JButton		saveBtn;
		String[] options = {"WarHero", "Shinobi", "Mage", "Pirate"};
		
		this.playerModel = playerModel;
		nameTxt = new JTextField(20);
		nameLabel = new JLabel("Name");
		classLabel = new JLabel("Class");
		nameTxt = new JTextField(20);
		saveBtn = new JButton("SAVE");
		errorsLabel = new JLabel("Errors");
		errorsLabel.setVisible(false);
		classType = new JComboBox<>(options);
		classType.setSelectedIndex(0);
		nameLabel.setBounds(10, 50, 200, 30);
		nameTxt.setBounds(200, 50, 200, 30);
		classLabel.setBounds(10, 90, 200, 30);
		saveBtn.setBounds(225, 230, 100, 30);
		classType.setBounds(200, 90, 200, 30);
		errorsLabel.setBounds(10, 130, 400, 90);
		frame.add(nameLabel);
		frame.add(nameTxt);
		frame.add(classLabel);
		frame.add(classType);
		frame.add(errorsLabel);
		frame.add(saveBtn);
		saveBtn.addActionListener(new btnAction());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 300));
		frame.setLayout(null);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This enables the user to choose the player, btn to create player, select player and exit
	 * @param controller - instance of controller
	 * @return - an int if successful
	 */
	public int	choosePlayer(PlayerController controller) {
		this.playerController = controller;
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));
		JButton btnNew = new JButton("Create Player");
		JButton btnSelect = new JButton("Select Player");
		JButton btnExit = new JButton("Exit");
		btnNew.addActionListener(new btnEvent());
		btnSelect.addActionListener(new btnEvent());
		btnExit.addActionListener(new btnEvent());
		btnNew.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnl.add(Box.createRigidArea(new Dimension(10, 10)));
		pnl.add(btnNew);
		pnl.add(Box.createRigidArea(new Dimension(10, 10)));
		pnl.add(btnSelect);
		pnl.add(Box.createRigidArea(new Dimension(10, 10)));
		pnl.add(btnExit);
		frame.add(pnl);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		return (returnNum);
	}

	/**
	 * this event handles how different buttons react when clicked
	 */
	private class btnEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "Create Player":
					frame.setVisible(false);
					frame.dispose();
					playerController.createPlayer();
					break ;
				case "Select Player":
					frame.setVisible(false);
					frame.dispose();
					playerController.selectPlayer();
					break ;
				case "Exit":
					frame.setVisible(false);
					frame.dispose();
					break ;
			}
		}
	}

	private void savePlayerFields() {
		List<ValidationErrorModel>	errors;
		StringBuilder allErrors;


		playerModel.setName(nameTxt.getText());
		playerModel.setPClass(classType.getSelectedItem().toString());
		errors = new ArrayList<>();
		if (this.playerController.validatePlayer(errors)) {
			allErrors = new StringBuilder("<html>Errors:<br />");
			for (ValidationErrorModel tempError : errors) {
				allErrors.append(tempError.getField()).append("::").append(tempError.getErrorMessage()).append("<br/>");
			}
			allErrors.append("</html>");
			errorsLabel.setText(allErrors.toString());
			errorsLabel.setForeground(Color.red);
			errorsLabel.setVisible(true);
			return ;
		}
		if (this.playerController.savePlayer()) {
			JOptionPane.showMessageDialog(frame, "HERO SAVED");
			frame.setVisible(false);
			frame.dispose();
			this.playerModel = this.playerController.getLastPlayer();
			this.playerController.setPlayer(this.playerModel);
		}
		else {
			JOptionPane.showMessageDialog(frame, "ERROR SAVING HERO", "Save Error",
    JOptionPane.ERROR_MESSAGE);
		}
	}

	public class btnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			savePlayerFields();
		}
	}

	public void refresh() {}

	public void	selectPlayer(List<PlayerModel> players) {
		PlayersTableGui	tableView = new PlayersTableGui(players, this);
	}

	void setPlayer(PlayerModel playerModel) {
		playerController.setPlayer(playerModel);
	}
}
