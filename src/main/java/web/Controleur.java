package main.java.web;

import javax.servlet.http.HttpServletRequest;

import main.java.pojo.Page;
import main.java.service.InterfaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Controller
public class Controleur {

	private ApplicationContext context = null;
	private InterfaceService implServ = null;

	@RequestMapping(value = { "/index.html", "/TableauComputer.html" }, method = RequestMethod.GET)
	public String affichageTableau(ModelMap model, HttpServletRequest request) {
		implServ = getImplServ();

		try {
			model.addAttribute("page",
					implServ.ConstructionTableauAccueil(request));
			return "TableauComputer";
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/ModifyOrAdd.html" }, method = RequestMethod.GET)
	public String ModifierAjouterComputer(ModelMap model,
			HttpServletRequest request) {
		implServ = getImplServ();
		try {
			Page page = implServ.ModifyOrAddComputer(request);
			model.addAttribute("page", page);
			return page.getUrl();
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/delete.html" }, method = RequestMethod.POST)
	public String SupprimerComputer(ModelMap model, HttpServletRequest request) {
		implServ = getImplServ();
		try {
			implServ.DeleteComputer(request);
			return "redirect:/TableauComputer.html";
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/saveComputer.html" }, method = RequestMethod.POST)
	public String SauverComputer(ModelMap model, HttpServletRequest request) {
		implServ = getImplServ();
		try {
			boolean error;
			error = implServ.SaveComputer(request);
			if (error) {
				return ModifierAjouterComputer(model, request);
				
				
			} else {
				return "redirect:/TableauComputer.html";
			}

		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}

	}
	
	
	

	private InterfaceService getImplServ() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("spring-config.xml");
		}
		return context.getBean(main.java.service.InterfaceService.class);
	}

}
