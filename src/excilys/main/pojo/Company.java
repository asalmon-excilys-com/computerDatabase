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
	
	public Company(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company() {
		super();
	}

}
