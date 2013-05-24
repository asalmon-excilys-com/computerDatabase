package excilys.main.orm;

import java.util.List;

import excilys.main.pojo.Company;

public interface InterfaceCompanyDAO {

	public abstract List<Company> getListCompanies() throws Exception;

}