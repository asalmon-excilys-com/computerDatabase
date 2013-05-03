/**
 * 
 */
package excilys.main.pojo;

/**
 * @author excilys
 *
 */
public class Company {
	
	private Integer id;
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	
	// TODO Réfléchir sur les constructeurs et leur utilisation
	public Company(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company() {
		super();
	}

}
