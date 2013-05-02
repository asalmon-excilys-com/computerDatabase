package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;

public interface InterfaceDAO {

	public ResultSet getListComputers(Connection conn);
	
	public Integer getSizeComputers(Connection conn);

	ResultSet getListComputersSlice(Connection conn, Integer starter);

}
