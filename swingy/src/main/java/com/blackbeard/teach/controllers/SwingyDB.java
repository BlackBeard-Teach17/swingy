package com.blackbeard.teach.controllers;

import com.blackbeard.teach.models.PlayerModel;
import com.blackbeard.teach.models.ValidationErrorModel;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class SwingyDB {
	private String 		fileName;
	private PlayerModel	hero;
	private ResultSet resultSet;
	private Statement			statement;
	private PreparedStatement preparedStatement;
	private String CREATE_DB;

	{
		CREATE_DB = "CREATE TABLE IF NOT EXISTS hero " +
				"(rec INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name Varchar(30), " +
				"pClass Varchar(30), " +
				"level int, " +
				"experience int, " +
				"attack int, " +
				"defence int, " +
				"hitPoints int " +
				")";
	}

	private String	INSERT_PLAYER;

	{
		INSERT_PLAYER = "INSERT INTO hero " +
				"(name, " +
				"pClass, " +
				"level, " +
				"experience, " +
				"attack, " +
				"defence, " +
				"hitPoints) VALUES " +
				"(?, ?, ?, ?, ?, ?, ?)";
	}

	private String  GET_PLAYERS;

	{
		GET_PLAYERS = "SELECT rec, name, pClass, level, experience, attack, defence, hitPoints from hero;";
	}

	private String	UPDATE_PLAYER;

	{
		UPDATE_PLAYER = "UPDATE hero set " +
				"level = ?, " +
				"experience = ?, " +
				"attack = ?, " +
				"defence = ? " +
				"where rec = ?";
	}

	private String	DELETE_PLAYER;
	private String	DROP_TABLE;

	{
		DELETE_PLAYER = "DELETE FROM hero where name = ?";
		DROP_TABLE = "DROP TABLE IF EXISTS hero";
	}

	private Connection	getConnection() {
		try {
			return (DriverManager.getConnection(fileName));
		}
		catch (SQLException err) {
			return (null);
		}
	}

	public SwingyDB() {
		fileName = "jdbc:sqlite:swingy.db";
		Connection conn;
		try {
			String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			conn = getConnection();
			if (conn != null) {
				statement = conn.createStatement();
				statement.execute(CREATE_DB);
				DatabaseMetaData meta = conn.getMetaData();
			}
		}
		catch (SQLException err) {
			System.out.println("Error connecting to database :: " + err.getMessage());
		}
		catch (ClassNotFoundException err) {
			System.out.println("Class not found : " + err.getMessage());
		}
	}

	public PlayerModel		getPlayer(int rec) {
		return (null);
	}

	List<PlayerModel> getHeroes() {
		Connection 					conn;
		List<PlayerModel>			players;
		List<ValidationErrorModel> 	errors;

		try {
			conn = this.getConnection();
			assert conn != null;
			statement = conn.createStatement();
			ResultSet	rs = statement.executeQuery(GET_PLAYERS);
			players = new ArrayList<>();
			errors = new ArrayList<>();
			while (rs.next()) {
				PlayerModel tempModel = new PlayerModel();
				tempModel.setRec(rs.getInt("rec"));
				tempModel.setName(rs.getString("name"));
				tempModel.setPClass(rs.getString("pClass"));
				tempModel.setLevel(rs.getInt("level"));
				tempModel.setXP(rs.getInt("experience"));
				tempModel.setAttack(rs.getInt("attack"));
				tempModel.setDefence(rs.getInt("defence"));
				tempModel.setHP(rs.getInt("hitPoints"));
				if (ValidateController.runValidator(errors, tempModel)) {
					players.add(tempModel);
				}
			}
		}
		catch (SQLException err) {
			System.out.println("Error reading data : " + err.getMessage());
			return (null);
		}
		return (players);
	}

	void updateHero(PlayerModel saveHero) {
		Connection	conn;
		try {
			conn = getConnection();
			assert conn != null;
			preparedStatement = conn.prepareStatement(UPDATE_PLAYER);
			preparedStatement.setInt(1, saveHero.getLevel());
			preparedStatement.setInt(2, saveHero.getXP());
			preparedStatement.setInt(3, saveHero.getAttack());
			preparedStatement.setInt(4, saveHero.getDefence());
			preparedStatement.setInt(5, saveHero.getRec());
			preparedStatement.executeUpdate();
		}
		catch (SQLException err) {
			err.printStackTrace();
		}

	}

	boolean insertHero(PlayerModel hero) {
		this.hero = hero;
		Connection conn;

		try {
			conn = getConnection();
		//	if (!isUniquePlayer(hero))
		//		System.out.println("Player already exists");
            assert conn != null;
            preparedStatement = conn.prepareStatement(INSERT_PLAYER);
			preparedStatement.setString(1, this.hero.getName());
			preparedStatement.setString(2, this.hero.getPClass());
			preparedStatement.setInt(3, this.hero.getLevel());
			preparedStatement.setInt(4, this.hero.getXP());
			preparedStatement.setInt(5, this.hero.getAttack());
			preparedStatement.setInt(6, this.hero.getDefence());
			preparedStatement.setInt(7, this.hero.getHP());
			preparedStatement.executeUpdate();
		}
		catch (SQLException err) {
			return (false);
		}
		return (true);
	}

	private boolean isUniquePlayer(PlayerModel player) throws SQLException
    {
        this.hero = player;
        Connection connection = null;
        preparedStatement = connection.prepareStatement(GET_PLAYERS);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
		{
			if (player.getName().equalsIgnoreCase(resultSet.getString("name")))
				return false;
		}
        return true;
    }

    private void deletePlayer(String name)
	{
		try
		{
			Connection connection = getConnection();
			preparedStatement = connection.prepareStatement(DELETE_PLAYER);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}

	private void closeDB() {
		try
		{
			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();
			if (statement != null && !statement.isClosed())
				statement.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void deleteTable() {
		try
		{
			Connection connection = getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(DROP_TABLE);
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
	}
}
