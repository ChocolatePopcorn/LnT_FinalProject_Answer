package FinalProject.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public Connection connect;
	public Statement statement;
	public PreparedStatement preparedStatement;
	public ResultSet resultSet;

	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/final_project_lnt";
			String username = "root";
			String password = "";
			
			connect = DriverManager.getConnection(url, username, password);

			statement = connect.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//select all
	public ResultSet getAllData() {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM menu");
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {}
		return resultSet;
	}
	
	//insert
	public void insertUser(String code, String name, int price, int stock) {
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO menu (code, name, price, stock) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, code);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, price);
			preparedStatement.setInt(4, stock);

			preparedStatement.execute();
		} catch (SQLException e) {}
	}

	//update
	public void updateUser(String code, int price, int stock) {
		try {
			preparedStatement = connect.prepareStatement("UPDATE menu SET price = ?, stock = ? WHERE code = ?");
			preparedStatement.setInt(1, price);
			preparedStatement.setInt(2, stock);
			preparedStatement.setString(3, code);

			preparedStatement.execute();
		} catch (SQLException e) {}
	}

	//delete
	public void deleteUser(String code) {
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM menu WHERE code = ?");
			preparedStatement.setString(1, code);

			preparedStatement.execute();
		} catch (SQLException e) {}
	}
}
