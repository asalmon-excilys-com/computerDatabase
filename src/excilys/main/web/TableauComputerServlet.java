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

		Integer s = 0;
		if (request.getParameter("s") != null) {
			s = Integer.parseInt(request.getParameter("s"));
		}

		Integer p = 0;
		if (request.getParameter("p") != null) {
			p = Integer.parseInt(request.getParameter("p"));
		}

		String f = "";
		String clause ="%";
		if (request.getParameter("f") != null) {
			f = request.getParameter("f");
			clause = "%"+f+"%";
		}

		Connection conn;
		conn = gesCo.setConnection();
		ResultSet rs;
		Integer tailleTable = 0;
		Integer starter;

			if (p == 0) {
				starter = 0;
			} else {
				starter = p * 10;
			}
			
			rs = implDAO.getListComputersSlice(conn, starter, s, clause);
			tailleTable = implDAO.getSizeComputers(conn, clause);

		List<Computer> computers = implServ.ResultSetToComputers(rs);

		Integer nbStart = 1;
		Integer nbEnd = 10;

		request.setAttribute("computers", computers);
		request.setAttribute("tailleTable", tailleTable);
		request.setAttribute("p", p);
		request.setAttribute("s", s);
		request.setAttribute("f", f);
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
		doGet(request, response);
	}

}
