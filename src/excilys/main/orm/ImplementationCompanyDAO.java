package excilys.main.orm;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excilys.main.pojo.Company;
import excilys.main.service.Useful;


public class ImplementationCompanyDAO implements InterfaceCompanyDAO {
	
	final static Logger logger = LoggerFactory
			.getLogger(ImplementationCompanyDAO.class);
	
	private static final String SELECT_ALL_COMPANIES = "Select cie.id, cie.name from company cie order by cie.id;";
	
	private ImplementationCompanyDAO() {
	}

	private static ImplementationCompanyDAO INSTANCE = null;

	public static ImplementationCompanyDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ImplementationCompanyDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Company> getListCompanies() {
		List<Company> companies = new ArrayList<Company>();
		
		Statement stmt = null;

		try {
			stmt = GestionConnection.getInstance().getConnection().createStatement();
			companies = Useful.ResultSetToCompanies(stmt.executeQuery(SELECT_ALL_COMPANIES));
		} catch (SQLException e) {
			logger.error("Erreur de recuperation de la liste des compagnies"
					+ e.getMessage());
			return null;
		}finally{
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error("Erreur de fermeture du statement"
							+ e.getMessage());
				}
			}
		}
		return companies;
		
	}
}
