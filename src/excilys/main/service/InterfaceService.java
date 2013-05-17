package excilys.main.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import excilys.main.pojo.Page;


public interface InterfaceService {
	
	public Page ConstructionTableauAccueil(HttpServletRequest request) throws SQLException;
	public void DeleteComputer(HttpServletRequest request) throws SQLException;
	public Page ModifyOrAddComputer(HttpServletRequest request) throws SQLException;
	public boolean SaveComputer(HttpServletRequest request) throws SQLException;
}
