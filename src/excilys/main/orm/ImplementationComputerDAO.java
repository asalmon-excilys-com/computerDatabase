package excilys.main.orm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id cid, cie.name cname FROM computer c left outer join company cie on c.company_id = cie.id where c.name like ? order by ";
	private static final String LIMIT_SELECT = " limit ? , 10;";
	private static final String SELECT_ONE_COMPUTER_BY_ID = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id cid, cie.name cname FROM computer c left outer join company cie on c.company_id = cie.id where c.id = ?;";

	final static Logger logger = LoggerFactory
			.getLogger(ImplementationComputerDAO.class);

	@Autowired
	private DataSource ds;

	private JdbcTemplate jdbc;

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Computer> getListComputersSlice(Integer starter, Integer s,
			String clause) throws Exception {

		jdbc = new JdbcTemplate(ds);
		ArrayList<Object> insert = new ArrayList<Object>();
		insert.add(clause);
		insert.add(starter);

		StringBuilder sb = new StringBuilder(SELECT_ALL_COMPUTERS)
				.append(Useful.gestionTri(s)).append(LIMIT_SELECT);

		try{
		List<Map<String, Object>> rows = jdbc.queryForList(sb.toString(),
				insert.toArray());
		return Useful.ResultSetToComputers(rows);
		}catch(Exception e){
			logger.error("Erreur de recuperation de la liste des computers"
					+ e.getMessage());
			throw e;
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Computer getComputerByID(Integer ID) throws Exception {

		jdbc = new JdbcTemplate(ds);
		ArrayList<Object> insert = new ArrayList<Object>();
		insert.add(ID);
try{
		Map<String, Object> row = jdbc.queryForMap(SELECT_ONE_COMPUTER_BY_ID,
				insert.toArray());

		return Useful.ResultSetToComputer(row);
}catch(Exception e){
	logger.error("Erreur de recuperation du computer par ID"
			+ e.getMessage());
	throw e;
}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getSizeComputers(String clause) throws Exception {
		jdbc = new JdbcTemplate(ds);
		ArrayList<Object> insert = new ArrayList<Object>();
		insert.add(clause);
		
		try{
		@SuppressWarnings("deprecation")
		int result = jdbc.queryForInt(COUNT_COMPUTERS, insert.toArray());
		return result;
		} catch (Exception e) {
			logger.error("Erreur de recuperation de la taille de la table des computers"
					+ e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveComputer(Computer cp, boolean newCp) throws Exception {
		
		jdbc = new JdbcTemplate(ds);

		ArrayList<Object> insert = new ArrayList<Object>();
		insert.add(cp.getName());
		insert.add(Useful.gestionNull(cp.getIntroduced()));
		insert.add(Useful.gestionNull(cp.getDiscontinued()));

		if (cp.getCompany().getId() == null) {
			insert.add(null);
		} else {
			insert.add(cp.getCompany().getId());
		}
		
		try {
		if (newCp == true) {
			jdbc.update(INSERT_COMPUTER, insert.toArray());
		} else {
			insert.add(cp.getId());
			jdbc.update(UPDATE_COMPUTER, insert.toArray());
			//TODO TEST
//			throw new Exception();
		}
		} catch (Exception e) {
			logger.error("Erreur de sauvegarde des ordinateurs"
					+ e.getMessage());
			throw e;
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteComputerByID(Integer id) throws Exception {
		jdbc = new JdbcTemplate(ds);
		ArrayList<Object> insert = new ArrayList<Object>();
		insert.add(id);
		jdbc.update(DELETE_COMPUTER, insert.toArray());
	}

}
