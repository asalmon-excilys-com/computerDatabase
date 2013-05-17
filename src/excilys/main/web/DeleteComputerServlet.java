package excilys.main.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excilys.main.service.ImplementationService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/delete")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ImplementationService implServ = ImplementationService
				.getImplementationService();
		try {
			implServ.DeleteComputer(request);
			response.sendRedirect("TableauComputerServlet");
		} catch (SQLException e) {
			request.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(
					request, response);
		}


	}

}
