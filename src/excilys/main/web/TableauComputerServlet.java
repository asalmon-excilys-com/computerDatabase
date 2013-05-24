package excilys.main.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import excilys.main.service.InterfaceService;

/**
 * Servlet implementation class TableauComputerServlet
 */
@WebServlet("/TableauComputerServlet")
public class TableauComputerServlet extends HttpServlet {
	private ApplicationContext context = null;
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public TableauComputerServlet() {
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
		if (context == null){
			context = new ClassPathXmlApplicationContext("spring-config.xml");
        }  if (context != null){
        }
		
		InterfaceService implServ = context.getBean(excilys.main.service.InterfaceService.class);
		
		try {
			request.setAttribute("page",
					implServ.ConstructionTableauAccueil(request));

			getServletContext().getRequestDispatcher("/jsp/TableauComputer.jsp")
					.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/jsp/errorPage.jsp").forward(request, response);
		}

	}

}
