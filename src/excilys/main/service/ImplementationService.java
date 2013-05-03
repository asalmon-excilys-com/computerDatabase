package excilys.main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excilys.main.pojo.Company;
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
			rs.first();
			if(rs.next()){
				rs.first();
			do {
				Computer cp = new Computer();
				cp.setId(rs.getInt(1));
				cp.setName(rs.getString(4));
				cp.setIntroduced(rs.getDate(2));
				cp.setDiscontinued(rs.getDate(3));
				cp.setCompany(rs.getInt(5), rs.getString(6));
				computers.add(cp);
			} while(rs.next());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("taille de l'array" + computers.size());
		return computers;
		
	}
	
public Computer ResultSetToComputer(ResultSet rs) {
		
		Computer cp = new Computer();
		
		try {
			rs.first();
			cp.setId(rs.getInt(1));
			cp.setName(rs.getString(4));
			cp.setIntroduced(rs.getDate(2));
			cp.setDiscontinued(rs.getDate(3));
			cp.setCompany(rs.getInt(5), rs.getString(6));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cp;
		
	}

public List<Company> ResultSetToCompanies(ResultSet rs) {
	
	List<Company> companies = new ArrayList<Company>();
	
	try {
		while(rs.next()) {
			Company cp = new Company(rs.getInt(1), rs.getString(2));
			companies.add(cp);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return companies;
	
}
	
	
	
	
	
	
}
