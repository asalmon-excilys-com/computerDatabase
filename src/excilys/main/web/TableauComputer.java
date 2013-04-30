package excilys.main.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excilys.main.orm.GestionConnection;
import excilys.main.orm.ImplementationDAO;

/**
 * Servlet implementation class TableauComputer
 */
@WebServlet("/TableauComputer")
public class TableauComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TableauComputer() {
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GestionConnection gesCo = GestionConnection.getGestionConnection();
		ImplementationDAO implDAO = new ImplementationDAO();
		
		Connection conn;
			conn = gesCo.setConnection();
			ResultSet rs = implDAO.getListProduits(conn);


		Integer nbStart = 1;
		Integer nbEnd = 20;
		
		
		request.setAttribute("resultat", rs);
		request.setAttribute("nbStart", nbStart);
		request.setAttribute("nbEnd", nbEnd);
		
		getServletContext().getRequestDispatcher("/TableauComputer.jsp").forward(request, response);
		
		
		
	}

}
