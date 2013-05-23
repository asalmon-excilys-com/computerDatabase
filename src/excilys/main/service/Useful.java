package excilys.main.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import excilys.main.pojo.Computer;

@Component
public class Useful {

	final static Logger logger = LoggerFactory.getLogger(Useful.class);

	public static String gestionTri(Integer s) {
		String[] type = { "c.name", "c.name", "c.name", "c.introduced",
				"c.discontinued", "cie.name" };
		String[] sens = { "ASC", "DESC" };

		StringBuilder sb = new StringBuilder();
		if (s < 0) {
			sb.append(type[-s]).append(" ").append(sens[1]);
		} else {
			sb.append(type[s]).append(" ").append(sens[0]);
		}
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

	public static List<Computer> ResultSetToComputers(
			List<Map<String, Object>> rows) {

		List<Computer> computers = new ArrayList<Computer>();

		for (Map<String, Object> row : rows) {
			Computer cp = new Computer();

			cp.setId((Integer) row.get("id"));
			cp.setName((String) row.get("name"));
			cp.setIntroduced((Date) row.get("introduced"));
			cp.setDiscontinued((Date) row.get("discontinued"));
			cp.setCompany((Integer) row.get("cid"), (String) row.get("cname"));
			computers.add(cp);
		}
		return computers;
	}

	public static Computer ResultSetToComputer(Map<String, Object> row) {

		Computer cp = new Computer();

		cp.setId((Integer) row.get("id"));
		cp.setName((String) row.get("name"));
		cp.setIntroduced((Date) row.get("introduced"));
		cp.setDiscontinued((Date) row.get("discontinued"));
		cp.setCompany((Integer) row.get("cid"), (String) row.get("cname"));

		return cp;
	}

}
