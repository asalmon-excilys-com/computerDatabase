package excilys.main.pojo;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Computer {

	private Integer id;
	private String name;
	// Date de JodaTime
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

	public String getIntroducedToString() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.introduced.getTime());
	}
	
	public void setIntroduced(Calendar introduced) {
		this.introduced = introduced;
	}

	public void setIntroduced(Date introduced) {
		if(introduced!=null){
		Calendar cal = dateToCalendar(introduced);
		setIntroduced(cal);
		}else{
			this.introduced=null;
		}
	}

	public Calendar getDiscontinued() {
		return discontinued;
	}
	
	public String getDiscontinuedToString() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.discontinued.getTime());
	}

	public void setDiscontinued(Calendar discontinued) {
		this.discontinued = discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		if(discontinued!=null){
		Calendar cal = dateToCalendar(discontinued);
		setDiscontinued(cal);
		}else{
			this.discontinued=null;
		}
		
	}

	private Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
				
				return cal;
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

	public void setId(Integer id) {
		this.id = id;
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
