package excilys.main.orm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;

import excilys.main.pojo.Computer;
import excilys.main.service.Useful;

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
	public ResultSet getListComputersSlice(Connection conn, Integer starter,
			Integer s, String clause) {

		PreparedStatement ps;
		ResultSet results = null;

		String order = Useful.gestionTri(s);

		String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.name like ? order by "
				+ order + " limit ? , 10;";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, clause);
			ps.setInt(2, starter);

			results = ps.executeQuery();

		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListComputersSlice: "
							+ e);
		}

		return results;
	}

	@Override
	public ResultSet getListCompanies(Connection conn) {

		ResultSet results = null;
		Statement stmt;
		String sql = "Select cie.id, cie.name from company cie order by cie.id;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListCompanies: "
							+ e);
		}

		return results;
	}

	@Override
	public ResultSet getComputerByID(Connection conn, Integer ID) {

		ResultSet results = null;
		Statement stmt;
		String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.id = "
				+ ID + ";";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getComputerByID: "
							+ e);
		}

		return results;
	}

	@Override
	public Integer getSizeComputers(Connection conn, String clause) {
		ResultSet results = null;
		PreparedStatement ps;

		int result = 0;
		String sql = "SELECT count(c.id) FROM computer c where c.name like ?;";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, clause);
			results = ps.executeQuery();
			while (results.next()) {
				result = results.getInt(1);
			}

		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getSizeComputers: "
							+ e);
		}

		return result;

	}

	@Override
	public void saveComputer(Connection conn, Computer cp, boolean newCp) {

		PreparedStatement ps;

		String sql;
		if (newCp == true) {
			sql = "INSERT INTO `computerDatabase`.`computer` (`name`, `introduced`, `discontinued`, `company_id`) VALUES (?, ?, ?, ?);";
		} else {
			sql = "UPDATE `computerDatabase`.`computer` SET `name`=?, `introduced`=?, `discontinued`=?, `company_id`=? WHERE `id`=?;";
		}

		try {
			ps = conn.prepareStatement(sql);
			Date introducedSQL = Useful.gestionNull(cp.getIntroduced());
			Date discontinuedSQL = Useful.gestionNull(cp.getDiscontinued());

			ps.setString(1, cp.getName());
			ps.setDate(2, introducedSQL);
			ps.setDate(3, discontinuedSQL);

			if (cp.getCompany().getId() == null) {
				ps.setNull(4, Types.TIMESTAMP);
			} else {
				ps.setInt(4, cp.getCompany().getId());
			}
			
			if (newCp == false) {
				ps.setInt(5, cp.getId());
			}
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteComputerByID(Connection conn, Integer id) {

		PreparedStatement ps;

		String sql = "DELETE FROM `computerDatabase`.`computer` WHERE id = ?;";

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
