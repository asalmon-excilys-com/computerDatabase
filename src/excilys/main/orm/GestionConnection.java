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

	public static Connection setConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/computerDatabase";
			conn = DriverManager.getConnection(url, "root", "root");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Problèmes rencontrés: " + e);
		}

		return conn;

	}

	public static void closeConnection(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problèmes rencontrés: " + e);
		}

	}

}
