package excilys.main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excilys.main.pojo.Computer;

public class ImplementationService implements InterfaceService{

	//Singleton
	private ImplementationService(){}
	
	private static ImplementationService INSTANCE = null;

	public static ImplementationService getImplementationService() {
		if (INSTANCE == null) {
			INSTANCE = new ImplementationService();
		}
		return INSTANCE;
	}
	
	//Méthodes
	
	public List<Computer> ResultSetToComputers(ResultSet rs) {
		
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			while(rs.next()) {
				Computer cp = new Computer();
				cp.setId(rs.getInt("id"));
				cp.setName(rs.getString("name"));
				cp.setIntroduced(rs.getDate("introduced"));
				cp.setDiscontinued(rs.getDate("discontinued"));
				cp.setCompany_id(rs.getInt("company_id"));
				computers.add(cp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computers;
		
	}
	
	
	
	
	
	
}
