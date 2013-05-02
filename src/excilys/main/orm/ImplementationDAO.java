package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImplementationDAO implements InterfaceDAO {

	private ImplementationDAO() {
		super();
	}

	 private static ImplementationDAO INSTANCE = null;
	
	 public static ImplementationDAO getInstance() {
	 if (INSTANCE == null) {
	 INSTANCE = new ImplementationDAO();
	 }
	 return INSTANCE;
	 }

	@Override
	public ResultSet getListComputers(Connection conn) {

		ResultSet results = null;
		Statement stmt;
		String sql = "Select * from computer order by name;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListComputers: "
							+ e);
		}

		return results;
	}
	
	public ResultSet getListComputersSlice(Connection conn, Integer starter) {

		ResultSet results = null;
		Statement stmt;
		String sql = "Select * from computer order by name limit " + starter +" , 10;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListComputers: "
							+ e);
		}

		return results;
	}

	@Override
	public Integer getSizeComputers(Connection conn) {
		ResultSet results = null;
		int result = 0;
		Statement stmt;
		String sql = "SELECT count(*) FROM computer;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
			while (results.next()) {
				result = results.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("Problèmes rencontrés ImplementationDAO.getSizeComputers: "+ e);
		}

		return result;

	}


}
