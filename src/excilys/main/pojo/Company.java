/**
 * 
 */
package excilys.main.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author excilys
 *
 */

//@Entity
//@Table(name="company")
public class Company {
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
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
		this.id = id;
		this.name = name;
	}
	
	public Company() {
	}

}
