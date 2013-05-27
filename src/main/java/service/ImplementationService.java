package main.java.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.orm.InterfaceCompanyDAO;
import main.java.orm.InterfaceComputerDAO;
import main.java.pojo.Computer;
import main.java.pojo.Page;

@Service
@Scope("singleton")
@Transactional(readOnly = true)
public class ImplementationService implements InterfaceService {

	private static final Logger logger = LoggerFactory
			.getLogger(ImplementationService.class);

	@Autowired
	InterfaceCompanyDAO companyDAO;
	@Autowired
	InterfaceComputerDAO computerDAO;

	public ImplementationService() {
	}

	public InterfaceCompanyDAO getDAOcompany() {
		return companyDAO;
	}

	public void setDAOcompany(InterfaceCompanyDAO dAOcompany) {
		companyDAO = dAOcompany;
	}

	public InterfaceComputerDAO getImplDAO() {
		return computerDAO;
	}

	public void setImplDAO(InterfaceComputerDAO implDAO) {
		this.computerDAO = implDAO;
	}

	@Override
	public Page ConstructionTableauAccueil(Page page) throws Exception {

		page.setTailleTable(computerDAO.getSizeComputers("%" + page.getF()
				+ "%"));

		page.setComputers(computerDAO.getListComputersSlice(page.getP() * 10,
				page.getS(), "%" + page.getF() + "%"));
		return page;
	}

	@Override
	@Transactional(readOnly = false)
	public void DeleteComputer(Integer id) throws Exception {
		try {
			computerDAO.deleteComputerByID(id);
		} catch (Exception e) {
			logger.error("Erreur de suppression d'un computer" + e.getMessage());
			throw e;
		}

	}

	@Override
	public Page ModifyOrAddComputer(Integer id) throws Exception {

		Page page = new Page();

		page.setCompanies(companyDAO.getListCompanies());

		// TODO et si l'id n'est pas dasn le range de la base? genre négative

		if (id == 0) {
			page.setUrl("NewComputer");
		} else {
			page.setCp(computerDAO.getComputerByID(id));
			page.setUrl("Computer");
		}
		return page;
	}

	@Override
	@Transactional(readOnly = false)
	public void SaveComputer(Integer id, String name, Calendar introduced,
			Calendar discontinued, String company_id) throws Exception {

		Computer cp;

		boolean newCp;

		if (id == 0) {
			cp = new Computer(name, introduced, discontinued, company_id);
			newCp = true;
		} else {
			cp = new Computer(id, name, introduced, discontinued, company_id);
			newCp = false;

		}

		try {
			computerDAO.saveComputer(cp, newCp);
		} catch (Exception e) {
			logger.error("Erreur de sauvegarde des ordinateurs"
					+ e.getMessage());
			throw e;
		}

	}

}
