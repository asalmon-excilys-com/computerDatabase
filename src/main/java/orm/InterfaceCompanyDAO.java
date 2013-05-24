package main.java.orm;

import java.util.List;

import main.java.pojo.Company;

public interface InterfaceCompanyDAO {

	public abstract List<Company> getListCompanies() throws Exception;

}