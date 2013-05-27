package main.java.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.java.pojo.Page;
import main.java.service.InterfaceService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controleur {

	private ApplicationContext context = null;
	private InterfaceService implServ = null;

	@RequestMapping(value = { "/index.html", "/TableauComputer.html" })
	public String affichageTableau(ModelMap model,
			@RequestParam(defaultValue = "0") Integer s,
			@RequestParam(defaultValue = "0") Integer p,
			@RequestParam(defaultValue = "") String f) {

		implServ = getImplServ();

		Page page = new Page();
		page.setS(s);
		page.setP(p);
		page.setF(f);

		try {
			model.addAttribute("page",
					implServ.ConstructionTableauAccueil(page));
			return "TableauComputer";
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/ModifyOrAdd.html" }, method = RequestMethod.GET)
	public String modifierAjouterComputer(ModelMap model,
			@RequestParam(defaultValue = "0") Integer id) {

		implServ = getImplServ();

		try {
			Page page = implServ.ModifyOrAddComputer(id);
			model.addAttribute("page", page);
			return page.getUrl();
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/delete.html" }, method = RequestMethod.POST)
	public String supprimerComputer(ModelMap model,
			@RequestParam(defaultValue = "0") Integer id) {
		implServ = getImplServ();
		try {
			implServ.DeleteComputer(id);
			return "redirect:/TableauComputer.html";
		} catch (Exception e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
	}

	@RequestMapping(value = { "/saveComputer.html" }, method = RequestMethod.POST)
	public String sauverComputer(
			ModelMap model,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Integer id,
			@RequestParam(value = "introduced", defaultValue = "") String introducedS,
			@RequestParam(value = "discontinued", defaultValue = "") String discontinuedS,
			@RequestParam(value = "company", defaultValue = "") String company_id) {

		// Verif name
		if (name.isEmpty()) {
			model.addAttribute("nameError", "error");
			return modifierAjouterComputer(model, id);
		}

		// Verif dates
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		df.setLenient(false);
		Calendar introduced = Calendar.getInstance();
		Calendar discontinued = Calendar.getInstance();

		if (introducedS.isEmpty()) {
			introduced = null;
		} else {
			try {
				introduced.setTime(df.parse(introducedS));
			} catch (ParseException e) {
				model.addAttribute("introducedError", "error");
				return modifierAjouterComputer(model, id);
			}
		}

		if (discontinuedS.isEmpty()) {
			discontinued = null;
		} else {
			try {
				discontinued.setTime(df.parse(discontinuedS));
			} catch (ParseException e) {
				model.addAttribute("discontinuedError", "error");
				return modifierAjouterComputer(model, id);
			}
		}

		implServ = getImplServ();
		try {
			implServ.SaveComputer(id, name, introduced, discontinued,
					company_id);
			return "redirect:/TableauComputer.html";

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
