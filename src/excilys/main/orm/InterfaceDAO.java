package excilys.main.orm;

import java.sql.Connection;
import java.sql.ResultSet;

public interface InterfaceDAO {

	public ResultSet getListProduits(Connection conn);

}
