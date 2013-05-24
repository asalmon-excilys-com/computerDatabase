package main.java.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import main.java.service.InterfaceService;

/**
 * Servlet implementation class SaveComputerServlet
 */
@WebServlet("/saveComputer")
public class SaveComputerServlet extends HttpServlet {
	
	private ApplicationContext context = null;
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
		
				if (context == null){
		            context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		            System.out.println(context);
		        }if (context != null){
		        }
		InterfaceService implServ = context.getBean(main.java.service.InterfaceService.class);
		boolean error;
		try {
			error = implServ.SaveComputer(request);
			if (error) {
				getServletContext().getRequestDispatcher("/ModifyOrAdd")
						.forward(request, response);
			} else {
				response.sendRedirect("TableauComputerServlet");
			}
		} catch (Exception e) {
			request.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/jsp/errorPage.jsp").forward(
					request, response);
		}

	}

}
