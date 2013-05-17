package excilys.main.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excilys.main.service.ImplementationService;

/**
 * Servlet implementation class SaveComputerServlet
 */
@WebServlet("/saveComputer")
public class SaveComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveComputerServlet() {

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
		boolean error = implServ.SaveComputer(request);

		if(error) {
			getServletContext().getRequestDispatcher("/ModifyOrAdd").forward(
					request, response);
		}else{
		response.sendRedirect("TableauComputerServlet");
		}
	}

}
