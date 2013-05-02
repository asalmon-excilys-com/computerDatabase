package excilys.main.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import excilys.main.orm.GestionConnection;
import excilys.main.orm.ImplementationDAO;
import excilys.main.pojo.Computer;
import excilys.main.service.ImplementationService;

/**
 * Servlet implementation class TableauComputerServlet
 */
@WebServlet("/TableauComputerServlet")
public class TableauComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public TableauComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();
		ImplementationService implServ = ImplementationService
				.getImplementationService();

		Integer p;
		if (request.getParameter("p") == null) {
			p = 1;
		} else {
			p = Integer.parseInt(request.getParameter("p"));
		}

		Connection conn;
		conn = gesCo.setConnection();
		ResultSet rs;
		Integer starter;
		if (p == 1) {
			rs = implDAO.getListComputersSlice(conn, 1);
			starter = 1;
		} else {
			starter = p * 10 + 1;
			rs = implDAO.getListComputersSlice(conn, starter);
			
		}
		Integer tailleTable = implDAO.getSizeComputers(conn);

		List<Computer> computers = implServ.ResultSetToComputers(rs);

		Integer nbStart = 1;
		Integer nbEnd = 10;

		request.setAttribute("computers", computers);
		request.setAttribute("nbStart", nbStart);
		request.setAttribute("nbEnd", nbEnd);
		request.setAttribute("tailleTable", tailleTable);
		request.setAttribute("p", p);
		request.setAttribute("starter", starter);

		getServletContext().getRequestDispatcher("/TableauComputer.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = ImplementationDAO.getInstance();
		ImplementationService implServ = ImplementationService
				.getImplementationService();

		Integer p;
		if (request.getParameter("p") == null) {
			p = 1;
		} else {
			p = Integer.parseInt(request.getParameter("p"));
		}

		Connection conn;
		conn = gesCo.setConnection();
		ResultSet rs;
		Integer starter;
		if (p == 1) {
			rs = implDAO.getListComputersSlice(conn, 1);
			starter = 1;
		} else {
			rs = implDAO.getListComputersSlice(conn, p);
			starter = p * 10 + 1;
		}
		Integer tailleTable = implDAO.getSizeComputers(conn);

		List<Computer> computers = implServ.ResultSetToComputers(rs);

		Integer nbStart = 1;
		Integer nbEnd = 10;

		request.setAttribute("computers", computers);
		request.setAttribute("nbStart", nbStart);
		request.setAttribute("nbEnd", nbEnd);
		request.setAttribute("tailleTable", tailleTable);
		request.setAttribute("p", p);
		request.setAttribute("starter", starter);

		getServletContext().getRequestDispatcher("/TableauComputer.jsp")
				.forward(request, response);

	}

}
