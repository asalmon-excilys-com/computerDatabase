package excilys.main.orm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import excilys.main.pojo.Computer;
import excilys.main.service.Useful;

@Repository
@Scope("singleton")
@Transactional
public class ImplementationComputerDAO implements InterfaceComputerDAO {
	private static final String DELETE_COMPUTER = "DELETE FROM `computerDatabase`.`computer` WHERE id = ?;";
	private static final String INSERT_COMPUTER = "INSERT INTO `computerDatabase`.`computer` (`name`, `introduced`, `discontinued`, `company_id`) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_COMPUTER = "UPDATE `computerDatabase`.`computer` SET `name`=?, `introduced`=?, `discontinued`=?, `company_id`=? WHERE `id`=?;";
	private static final String COUNT_COMPUTERS = "SELECT count(c.id) FROM computer c where c.name like ?;";
	private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.name like ? order by ";
	private static final String LIMIT_SELECT = " limit ? , 10;";
	private static final String SELECT_ONE_COMPUTER_BY_ID = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.id = ";

	final static Logger logger = LoggerFactory
			.getLogger(ImplementationComputerDAO.class);

//	@Autowired
//	private ImplementationComputerDAO() {
//		if (INSTANCE == null) {
//			INSTANCE = new ImplementationComputerDAO();
//		}
//	}
//	@Autowired
//	private static ImplementationComputerDAO INSTANCE = null;
//
////	@Autowired
////	public static ImplementationComputerDAO getInstance() {
////		if (INSTANCE == null) {
////			INSTANCE = new ImplementationComputerDAO();
////		}
////		return INSTANCE;
////	}
//	
	@Autowired
	GestionConnection gesco;

	@Override
	@Transactional
	public List<Computer> getListComputersSlice(Integer starter, Integer s,
			String clause) throws SQLException {

		PreparedStatement ps = null;
		List<Computer> computers = new ArrayList<Computer>();

		String order = Useful.gestionTri(s);

		StringBuilder sb = new StringBuilder(SELECT_ALL_COMPUTERS)
				.append(order).append(LIMIT_SELECT);

		try {
			ps = gesco.getConnection()
					.prepareStatement(sb.toString());
			ps.setString(1, clause);
			ps.setInt(2, starter);
			computers = Useful.ResultSetToComputers(ps.executeQuery());

		} catch (SQLException e) {
			logger.error("Erreur de recuperation de la liste des computers"
					+ e.getMessage());
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e1) {
					logger.error("Erreur de fermeture du statement"
							+ e1.getMessage());
					throw e1;
				}
			}
		}

		return computers;
	}

	@Override
	@Transactional
	public Computer getComputerByID(Integer ID) throws SQLException {
		Computer computer = null;
		Statement stmt = null;
		StringBuilder sb = new StringBuilder(SELECT_ONE_COMPUTER_BY_ID).append(
				ID).append(";");

		try {
			stmt = gesco.getConnection()
					.createStatement();
			computer = Useful.ResultSetToComputer(stmt.executeQuery(sb.toString()));
		} catch (SQLException e) {
			logger.error("Erreur de recuperation du computer par ID"
					+ e.getMessage());
			throw e;
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					logger.error("Erreur de fermeture du statement"
							+ e1.getMessage());
					throw e1;
				}
			}
		}

		return computer;
	}

	@Override
	@Transactional
	public Integer getSizeComputers(String clause) throws SQLException {
		ResultSet results = null;
		PreparedStatement ps = null;

		int result = 0;
		String sql = COUNT_COMPUTERS;

		try {
			ps = gesco.getConnection().prepareStatement(sql);
			ps.setString(1, clause);
			results = ps.executeQuery();
			while (results.next()) {
				result = results.getInt(1);
			}

		} catch (SQLException e) {
			logger.error("Erreur de recuperation de la taille de la table des computers"
					+ e.getMessage());
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("Erreur de fermeture du statement"
							+ e.getMessage());
					throw e;
				}
			}
		}

		return result;

	}

	@Override
	@Transactional
	public void saveComputer(Computer cp, boolean newCp) throws SQLException{

		PreparedStatement ps = null;

		String sql;
		if (newCp == true) {
			sql = INSERT_COMPUTER;
		} else {
			sql = UPDATE_COMPUTER;
		}

		try {
			ps = gesco.getConnection()
					.prepareStatement(sql);
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

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("Erreur de fermeture du statement"
							+ e.getMessage());
					throw e;
				}
			}
		}
	}

	@Override
	@Transactional
	public void deleteComputerByID(Integer id) throws SQLException {

		PreparedStatement ps = null;

		String sql = DELETE_COMPUTER;

		try {
			ps = gesco.getConnection()
					.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("Erreur de fermeture du statement"
							+ e.getMessage());
					throw e;
				}
			}
		}
	}

}
