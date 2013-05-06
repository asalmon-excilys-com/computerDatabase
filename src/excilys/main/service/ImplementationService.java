package excilys.main.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import excilys.main.orm.GestionConnection;
import excilys.main.orm.ImplementationDAO;
import excilys.main.pojo.Company;
import excilys.main.pojo.Computer;
import excilys.main.pojo.Page;

public class ImplementationService implements InterfaceService {

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

		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();

		Connection conn;
		ResultSet rs;
		conn = gesCo.setConnection();

		page.setS(Useful.gestionNull(request.getParameter("s")));
		page.setP(Useful.gestionNull(request.getParameter("p")));
		page.setStarter(Useful.gestionStarter(page.getP()));
		page.setF(Useful.gestionNullClause(request.getParameter("f")));
		page.setTailleTable(implDAO.getSizeComputers(conn, "%" + page.getF()
				+ "%"));

		rs = implDAO.getListComputersSlice(conn, page.getStarter(),
				page.getS(), "%" + page.getF() + "%");
		page.setComputers(Useful.ResultSetToComputers(rs));

		return page;
	}

	@Override
	public void DeleteComputer(HttpServletRequest request) {
		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();

		Connection conn;
		conn = gesCo.setConnection();

		Integer id = Integer.parseInt(request.getParameter("id"));

		implDAO.deleteComputerByID(conn, id);

	}
	
	@Override
	public Page ModifyOrAddComputer(HttpServletRequest request) {

		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();
		
		Page page = new Page();

		Connection conn;
		conn = gesCo.setConnection();
		ResultSet rs;
		rs = implDAO.getListCompanies(conn);

		page.setCompanies(Useful.ResultSetToCompanies(rs));

		if (request.getParameter("id") == null) {
			page.setUrl("/NewComputer.jsp");
		} else {
			Integer id = Integer.parseInt(request.getParameter("id"));
			rs = implDAO.getComputerByID(conn, id);
			page.setCp(Useful.ResultSetToComputer(rs));
			page.setUrl("/Computer.jsp");
		}
		return page;
	}
	
	public void SaveComputer(HttpServletRequest request) {
		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();

		Connection conn;
		conn = gesCo.setConnection();
		
		Computer cp;

		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company_id = request.getParameter("company");
		boolean newCp;
		
		if (request.getParameter("id") == null) {
			cp = new Computer(name, introduced, discontinued, company_id);
			newCp = true;
		} else {
			String id = request.getParameter("id");
			cp = new Computer(id, name, introduced, discontinued, company_id);
			newCp = false;
			
		}

		implDAO.saveComputer(conn, cp, newCp);
	}

}
