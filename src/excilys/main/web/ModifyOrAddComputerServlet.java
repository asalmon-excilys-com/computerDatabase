package excilys.main.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import excilys.main.pojo.Page;
import excilys.main.service.InterfaceService;

/**
 * Servlet implementation class ModifyOrAddComputerServlet
 */
@WebServlet("/ModifyOrAdd")
public class ModifyOrAddComputerServlet extends HttpServlet {
	private ApplicationContext context = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyOrAddComputerServlet() {
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
		InterfaceService implServ = context.getBean(excilys.main.service.InterfaceService.class);

		Page page;
		try {
			page = implServ.ModifyOrAddComputer(request);
			request.setAttribute("page", page);
			getServletContext().getRequestDispatcher(page.getUrl()).forward(
					request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/jsp/errorPage.jsp").forward(
					request, response);
		}


	}

}
