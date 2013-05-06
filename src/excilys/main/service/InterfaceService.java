package excilys.main.service;

import javax.servlet.http.HttpServletRequest;

import excilys.main.pojo.Page;


public interface InterfaceService {
	
	public Page ConstructionTableauAccueil(HttpServletRequest request);
	public void DeleteComputer(HttpServletRequest request);
	public Page ModifyOrAddComputer(HttpServletRequest request);

}
