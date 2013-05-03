package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;

public interface InterfaceDAO {

	public ResultSet getListComputers(Connection conn);
	
	public Integer getSizeComputers(Connection conn);

	public ResultSet getListComputersSlice(Connection conn, Integer starter, Integer s);
	public ResultSet getListCompanies(Connection conn);

	ResultSet getComputerByID(Connection conn, Integer ID);
}
