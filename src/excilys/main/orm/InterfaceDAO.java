package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;

import excilys.main.pojo.Computer;

public interface InterfaceDAO {
	// Listes
	public ResultSet getListComputersSlice(Connection conn, Integer starter,
			Integer s, String clause);

	public ResultSet getListCompanies(Connection conn);

	public ResultSet getComputerByID(Connection conn, Integer ID);

	// Size
	public Integer getSizeComputers(Connection conn, String clause);

	// Save
	public void saveComputer(Connection conn, Computer cp, boolean newCp);

	// Delete
	public void deleteComputerByID(Connection conn, Integer id);

}