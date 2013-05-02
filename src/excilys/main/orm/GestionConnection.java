package excilys.main.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestionConnection {

	private GestionConnection() {
	}

	private static GestionConnection INSTANCE = null;

	public static GestionConnection getGestionConnection() {
		if (INSTANCE == null) {
			INSTANCE = new GestionConnection();
		}
		return INSTANCE;
	}

	public Connection setConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/computerDatabase";
			conn = DriverManager.getConnection(url, "root", "root");

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			System.out.println("Problèmes rencontrés GestionConnection.setConnection: " + e);
		}

		return conn;

	}

	public static void closeConnection(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problèmes rencontrés: " + e);
		}

	}

}
