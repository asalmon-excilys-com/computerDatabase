package excilys.main.orm;

import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import excilys.main.pojo.Computer;

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
	public ResultSet getListComputers(Connection conn) {

		ResultSet results = null;
		Statement stmt;
		String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id order by c.name;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListComputers: "
							+ e);
		}

		return results;
	}
	
	@Override
	public ResultSet getComputerByID(Connection conn, Integer ID) {

		ResultSet results = null;
		Statement stmt;
		String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.id = "+ ID +";";

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
	
	public ResultSet getListComputersSlice(Connection conn, Integer starter, Integer s) {

		PreparedStatement ps;
		ResultSet results = null;
		
		String order = gestionTri(s);
		

		String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id order by "+order+" limit ? , 10;";

		try {
			ps = conn.prepareStatement(sql);	
			ps.setInt(1, starter);
			
			results = ps.executeQuery();
			
			
		} catch (SQLException e) {
			System.out
					.println("Problèmes rencontrés ImplementationDAO.getListComputersSlice: "
							+ e);
		}

		return results;
	}

	private String gestionTri(Integer s) {
		String order = "c.name ASC";
		switch(s){
		case -2: order = "c.name DESC"; break;
		case -3: order = "c.introduced DESC"; break;
		case 3: order = "c.introduced ASC"; break;
		case -4: order = "c.discontinued DESC"; break;
		case 4: order = "c.discontinued ASC"; break;
		case -5: order = "cie.name DESC"; break;
		case 5: order = "cie.name ASC"; break;
		}
		
		return order;
	}

	@Override
	public Integer getSizeComputers(Connection conn) {
		ResultSet results = null;
		int result = 0;
		Statement stmt;
		String sql = "SELECT count(*) FROM computer;";

		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(sql);
			while (results.next()) {
				result = results.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("Problèmes rencontrés ImplementationDAO.getSizeComputers: "+ e);
		}

		return result;

	}
	
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
	
	public void saveComputer(Connection conn, Computer cp) {
		
		PreparedStatement ps;
		
		String sql = "UPDATE `computerDatabase`.`computer` SET `name`=?, `introduced`=?, `discontinued`=?, `company_id`=? WHERE `id`=?;";
		
		try {
			ps = conn.prepareStatement(sql);
			Date introducedSQL;
			Date discontinuedSQL;
			
			if(cp.getIntroduced()!=null){
			introducedSQL = new Date(cp.getIntroduced().getTimeInMillis());
			}else
			{
				introducedSQL = null;
			}
			
			if(cp.getDiscontinued()!=null){
				discontinuedSQL = new Date(cp.getDiscontinued().getTimeInMillis());
				}else
				{
				discontinuedSQL = null;
				}
			
			
			ps.setString(1, cp.getName());
			ps.setDate(2, introducedSQL);
			ps.setDate(3, discontinuedSQL);
			if(cp.getCompany().getId()==null){
				ps.setNull(4, Types.TIMESTAMP);
			} else {
				ps.setInt(4, cp.getCompany().getId());	
			}
			ps.setInt(5, cp.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void saveNewComputer(Connection conn, Computer cp) {
		
		PreparedStatement ps;
		ResultSet results;
		
String sql ="INSERT INTO `computerDatabase`.`computer` (`name`, `introduced`, `discontinued`, `company_id`) VALUES (?, ?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(sql);
			Date introducedSQL;
			Date discontinuedSQL;
			
			if(cp.getIntroduced()!=null){
			introducedSQL = new Date(cp.getIntroduced().getTimeInMillis());
			}else
			{
				introducedSQL = null;
			}
			
			if(cp.getDiscontinued()!=null){
				discontinuedSQL = new Date(cp.getDiscontinued().getTimeInMillis());
				}else
				{
				discontinuedSQL = null;
				}
			
			
			ps.setString(1, cp.getName());
			ps.setDate(2, introducedSQL);
			ps.setDate(3, discontinuedSQL);
			
			if(cp.getCompany().getId()==null){
				ps.setNull(4, Types.TIMESTAMP);
			} else {
				ps.setInt(4, cp.getCompany().getId());	
			}
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public void deleteComputerByID(Connection conn, Integer id) {
	
	PreparedStatement ps;
	ResultSet results;
	
String sql ="DELETE FROM `computerDatabase`.`computer` WHERE id = ?;";
	
	try {
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ps.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public ResultSet getListComputersSlice(Connection conn, Integer starter, Integer s, String clause) {

	PreparedStatement ps;
	ResultSet results = null;
	
	String order = gestionTri(s);
	

	String sql = "SELECT c.id, c.introduced, c.discontinued, c.name, cie.id, cie.name c FROM computer c left outer join company cie on c.company_id = cie.id where c.name like ? order by "+order+" limit ? , 10;";

	try {
		ps = conn.prepareStatement(sql);	
		ps.setString(1, clause);
		ps.setInt(2, starter);
		
		results = ps.executeQuery();
		
//		Avoir la taille de results
//		results.last();
//		System.out.println("starter donné"+starter);
//		System.out.println("taille du resultSet"+results.getRow());
//		results.first();
		
		
	} catch (SQLException e) {
		System.out
				.println("Problèmes rencontrés ImplementationDAO.getListComputersSlice: "
						+ e);
	}

	return results;
}

public Integer getSizeComputers(Connection conn, String clause) {
	ResultSet results = null;
	PreparedStatement ps;
	
	int result = 0;
	Statement stmt;
	String sql = "SELECT count(c.id) FROM computer c where c.name like ?;";


	try {
		
		ps = conn.prepareStatement(sql);	
		ps.setString(1, clause);
		results = ps.executeQuery();
		while (results.next()) {
			result = results.getInt(1);
		}

	} catch (SQLException e) {
		System.out.println("Problèmes rencontrés ImplementationDAO.getSizeComputers: "+ e);
	}

	return result;

}


}
