package com.blackbeard.teach.views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.List;

import com.blackbeard.teach.models.PlayerModel;

class PlayersTableGui extends JFrame {
	private List<PlayerModel> 	players;
	private GuiViewPlayer playerView;
	private JTable				playersTable;

	PlayersTableGui(List<PlayerModel> players, GuiViewPlayer playerView) {
		this.setTitle("Select Hero");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.players = players;
		this.playerView = playerView;
		this.addHeroes();
	}

	private void addHeroes() {
		String[] col = {"Rec", "Name", "Class", "Lvl", "XP", "Atk", "Def", "HP"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		JScrollPane scrollPane;
		JPanel		fillPanel;
		JButton		btnSelect;

		btnSelect = new JButton("SELECT");
		btnSelect.addActionListener(new viewBtnClass());
		playersTable = new JTable(tableModel);
		for (PlayerModel tempModel : players) {
			Object[] data = {
				tempModel.getRec(),
				tempModel.getName(),
				tempModel.getPClass(),
				tempModel.getLevel(),
				tempModel.getXP(),
				tempModel.getAttack(),
				tempModel.getDefence(),
				tempModel.getHP()
			};
			tableModel.addRow(data);
		}
		scrollPane = new JScrollPane(playersTable);
		playersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillPanel = new JPanel(new BorderLayout());
		fillPanel.add(scrollPane, BorderLayout.CENTER);
		fillPanel.add(btnSelect, BorderLayout.SOUTH);
		this.add(fillPanel);
		this.setPreferredSize(new Dimension(500, 300));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void selectPlayer() {
		int selRow;

		if (!playersTable.getSelectionModel().isSelectionEmpty()) {
			this.setVisible(false);
			this.dispose();
			selRow = playersTable.getSelectedRow();
			playerView.setPlayer(this.players.get(selRow));
		}
		else {
			JOptionPane.showMessageDialog(this, "No Hero Selected", "Selection Error",
					JOptionPane.ERROR_MESSAGE);
		}	
	}

	private class viewBtnClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            if ("SELECT".equalsIgnoreCase(e.getActionCommand())) {
                selectPlayer();
            }
		}
	}

}
