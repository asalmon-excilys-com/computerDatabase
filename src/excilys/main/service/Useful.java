package excilys.main.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import excilys.main.pojo.Company;
import excilys.main.pojo.Computer;

@Component
public class Useful {

	final static Logger logger = LoggerFactory
			.getLogger(Useful.class);

	public static String gestionTri(Integer s) {
		 String[] type = {"c.name","c.name","c.name", "c.introduced", "c.discontinued", "cie.name"};
		String[] sens = {"ASC", "DESC"};
		
		StringBuilder sb = new StringBuilder();
		if(s<0){
			sb.append(type[-s]).append(" ").append(sens[1]);
		}else{
			sb.append(type[s]).append(" ").append(sens[0]);
		}
//		String order = "c.name ASC";
//		
//		switch (s) {
//		case -2:
//			order = "c.name DESC";
//			break;
//		case -3:
//			order = "c.introduced DESC";
//			break;
//		case 3:
//			order = "c.introduced ASC";
//			break;
//		case -4:
//			order = "c.discontinued DESC";
//			break;
//		case 4:
//			order = "c.discontinued ASC";
//			break;
//		case -5:
//			order = "cie.name DESC";
//			break;
//		case 5:
//			order = "cie.name ASC";
//			break;
//		}

		return sb.toString();
	}

	public static Date gestionNull(Calendar cal) {
		Date d;
		if (cal != null) {
			d = new Date(cal.getTimeInMillis());
		} else {
			d = null;
		}
		return d;
	}

	public static Integer gestionNull(String s) {
		Integer i = 0;
		if (s != null) {
			i = Integer.parseInt(s);
		}
		return i;
	}

	public static Integer gestionNull(Integer s) {
		Integer i = 0;
		if (s != null) {
			i = s;
		}
		return i;
	}

	public static String gestionNullClause(String s) {
		String st = "";
		if (s != null) {
			st = s;
		}
		return st;
	}

	public static Integer gestionStarter(Integer p) {
		if (p == 0) {
			return 0;
		} else {
			return p * 10;
		}
	}

	public static List<Computer> ResultSetToComputers(ResultSet rs) {

		List<Computer> computers = new ArrayList<Computer>();

		try {
			while (rs.next()) {
				Computer cp = new Computer();
				cp.setId(rs.getInt(1));
				cp.setName(rs.getString(4));
				cp.setIntroduced(rs.getDate(2));
				cp.setDiscontinued(rs.getDate(3));
				cp.setCompany(rs.getInt(5), rs.getString(6));
				computers.add(cp);
			}

		} catch (SQLException e) {
			logger.error("Erreur de conversion liste ordinateurs"
					+ e.getMessage());
		}
		return computers;

	}

	public static Computer ResultSetToComputer(ResultSet rs) {

			Computer cp = new Computer();

			try {
				while(rs.next()){
				rs.first();
				cp.setId(rs.getInt(1));
				cp.setName(rs.getString(4));
				cp.setIntroduced(rs.getDate(2));
				cp.setDiscontinued(rs.getDate(3));
				cp.setCompany(rs.getInt(5), rs.getString(6));
				}

			} catch (SQLException e) {
				logger.error("Erreur de conversion ordinateur"
						+ e.getMessage());
			}
			return cp;
	}

	public static List<Company> ResultSetToCompanies(ResultSet rs) {

		List<Company> companies = new ArrayList<Company>();

		try {
			while (rs.next()) {
				Company cp = new Company(rs.getInt(1), rs.getString(2));
				companies.add(cp);
			}

		} catch (SQLException e) {
			logger.error("Erreur de conversion liste compagnies"
					+ e.getMessage());
		}
		return companies;

	}

}
