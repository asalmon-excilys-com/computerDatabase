package excilys.main.pojo;

import java.util.Calendar;

public class Computer {
	
	private Integer id;
	private String name;
	//Date de JodaTime
	private Calendar introduced;
	private Calendar discontinued;
	private Integer company_id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Calendar introduced) {
		this.introduced = introduced;
	}
	public Calendar getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Calendar discontinued) {
		this.discontinued = discontinued;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getId() {
		return id;
	}
	
	// TODO Réfléchir sur les constructeurs et leur utilisation
	public Computer(String name, Calendar introduced, Calendar discontinued,
			Integer company_id) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	public Computer() {
		super();
	}
	
	
	
	
	

}
