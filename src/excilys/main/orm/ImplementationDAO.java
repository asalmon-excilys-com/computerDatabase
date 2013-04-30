package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImplementationDAO implements InterfaceDAO {

	private ImplementationDAO() {
	}

	private static ImplementationDAO INSTANCE = null;

	public static ImplementationDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ImplementationDAO();
		}
		return INSTANCE;
	}

	@Override
	public ResultSet getListProduits(Connection conn) {

		ResultSet results = null;
		Statement stmt;
		String sql = "Select * from computer";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Problèmes rencontrés: " + e);
		}

		return results;
	}

}
