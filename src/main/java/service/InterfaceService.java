package main.java.service;

import javax.servlet.http.HttpServletRequest;

import main.java.pojo.Page;


public interface InterfaceService {
	
	public Page ConstructionTableauAccueil(HttpServletRequest request) throws Exception;
	public void DeleteComputer(HttpServletRequest request) throws Exception;
	public Page ModifyOrAddComputer(HttpServletRequest request) throws Exception;
	public boolean SaveComputer(HttpServletRequest request) throws Exception;
}
