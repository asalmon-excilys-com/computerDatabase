package excilys.main.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excilys.main.orm.GestionConnection;
import excilys.main.orm.ImplementationCompanyDAO;
import excilys.main.orm.ImplementationComputerDAO;
import excilys.main.orm.InterfaceCompanyDAO;
import excilys.main.pojo.Computer;
import excilys.main.pojo.Page;

public class ImplementationService implements InterfaceService {

	private static final Logger logger = LoggerFactory
			.getLogger(ImplementationService.class);

	// Singleton
	private ImplementationService() {
	}

	private static ImplementationService INSTANCE = null;

	public static ImplementationService getImplementationService() {
		if (INSTANCE == null) {
			INSTANCE = new ImplementationService();
		}
		return INSTANCE;
	}

	// Méthodes

	@Override
	public Page ConstructionTableauAccueil(HttpServletRequest request) {

		Page page = new Page();

		ImplementationComputerDAO implDAO = ImplementationComputerDAO
				.getInstance();
		page.setS(Useful.gestionNull(request.getParameter("s")));
		page.setP(Useful.gestionNull(request.getParameter("p")));
		page.setStarter(Useful.gestionStarter(page.getP()));
		page.setF(Useful.gestionNullClause(request.getParameter("f")));

		page.setTailleTable(implDAO.getSizeComputers("%" + page.getF() + "%"));

		page.setComputers(implDAO.getListComputersSlice(page.getStarter(),
				page.getS(), "%" + page.getF() + "%"));

		GestionConnection.getInstance().closeConnection();

		return page;
	}

	@Override
	public void DeleteComputer(HttpServletRequest request) {

		ImplementationComputerDAO implDAO = ImplementationComputerDAO
				.getInstance();

		Integer id = Integer.parseInt(request.getParameter("id"));
		try {
			implDAO.deleteComputerByID(id);
		} catch (SQLException e) {
			logger.error("Erreur de suppresion d'un computer" + e.getMessage());
			try {
				GestionConnection.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur de rollback" + e1.getMessage());
			}
		} finally {
			GestionConnection.getInstance().closeConnection();
		}

	}

	@Override
	public Page ModifyOrAddComputer(HttpServletRequest request) {

		ImplementationComputerDAO implDAO = ImplementationComputerDAO
				.getInstance();
		InterfaceCompanyDAO DAOcompany = ImplementationCompanyDAO.getInstance();

		Page page = new Page();

		page.setCompanies(DAOcompany.getListCompanies());

		if (request.getParameter("id") == null) {
			page.setUrl("/NewComputer.jsp");
		} else {
			Integer id = Integer.parseInt(request.getParameter("id"));
			page.setCp(implDAO.getComputerByID(id));
			page.setUrl("/Computer.jsp");
		}

		GestionConnection.getInstance().closeConnection();
		return page;
	}

	public boolean SaveComputer(HttpServletRequest request) {

		boolean error = false;
		String name = null;
		// name
		if (request.getParameter("name") == null
				|| request.getParameter("name").trim().length() == 0) {
			error = true;
			request.setAttribute("nameError", "error");
		} else {
			name = request.getParameter("name");
		}

		// compagnie
		// Inutile car ne dépend pas de l'utilisateur il ne peut faire
		// d'injection
		String company_id = request.getParameter("company");

		// dates
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		df.setLenient(false);
		Calendar introduced = Calendar.getInstance();
		Calendar discontinued = Calendar.getInstance();

		if (request.getParameter("introduced").isEmpty()) {
			introduced = null;
		} else {
			try {
				introduced
						.setTime(df.parse(request.getParameter("introduced")));
			} catch (ParseException e) {
				error = true;
				request.setAttribute("introducedError", "error");
			}
		}

		if (request.getParameter("discontinued").isEmpty()) {
			discontinued = null;
		} else {
			try {
				discontinued.setTime(df.parse(request
						.getParameter("discontinued")));
			} catch (ParseException e) {
				error = true;
				request.setAttribute("discontinuedError", "error");
			}
		}

		ImplementationComputerDAO implDAO = ImplementationComputerDAO
				.getInstance();

		if (!error) {
			Computer cp;

			boolean newCp;

			if (request.getParameter("id") == null) {
				cp = new Computer(name, introduced, discontinued, company_id);
				newCp = true;
			} else {
				String id = request.getParameter("id");
				cp = new Computer(id, name, introduced, discontinued,
						company_id);
				newCp = false;

			}

			try {
				implDAO.saveComputer(cp, newCp);
			} catch (SQLException e) {
				logger.error("Erreur de sauvegarde des ordinateurs"
						+ e.getMessage());
				try {
					GestionConnection.getInstance().getConnection().rollback();
				} catch (SQLException e1) {
					logger.error("Erreur de rollback" + e1.getMessage());
				}
			} finally {
				GestionConnection.getInstance().closeConnection();
			}

		}

		return error;

	}

}
