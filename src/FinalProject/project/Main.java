package FinalProject.project;

import java.util.Vector;

import FinalProject.Util.User;
import FinalProject.config.DBConnection;

//belom connect db

public class Main {

	public Vector<User> user = new Vector<User>();
	public DBConnection dbConnection;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		//create a connection to db
		dbConnection = new DBConnection();

		//load all data from db
		dbConnection.getAllData();

		//store all data to vector
		try {
			while(dbConnection.resultSet.next()) {
				String code = dbConnection.resultSet.getString(1);	
				String name = dbConnection.resultSet.getString(2);	
				int price = dbConnection.resultSet.getInt(3);	
				int stock = dbConnection.resultSet.getInt(4);
				
				//add to vector
				user.add(new User(code, name, price, stock));
			}
		} catch (Exception e) {}

		new Home(user, dbConnection);
	}
}
