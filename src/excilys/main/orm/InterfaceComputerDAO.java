package excilys.main.orm;

import java.sql.SQLException;
import java.util.List;

import excilys.main.pojo.Computer;

public interface InterfaceComputerDAO {
	// Listes
	public List<Computer> getListComputersSlice(Integer starter,
			Integer s, String clause) throws SQLException;

	public Computer getComputerByID(Integer ID) throws SQLException;

	// Size
	public Integer getSizeComputers(String clause) throws SQLException;

	// Save
	public void saveComputer(Computer cp, boolean newCp) throws SQLException;

	// Delete
	public void deleteComputerByID(Integer id) throws SQLException;

}