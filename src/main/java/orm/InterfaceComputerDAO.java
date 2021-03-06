package main.java.orm;

import java.util.List;

import main.java.pojo.Computer;

public interface InterfaceComputerDAO {
	// Listes
	public List<Computer> getListComputersSlice(Integer starter,
			Integer s, String clause) throws Exception;

	public Computer getComputerByID(Integer ID) throws Exception;

	// Size
	public Integer getSizeComputers(String clause) throws Exception;

	// Save
	public void saveComputer(Computer cp, boolean newCp) throws Exception;

	// Delete
	public void deleteComputerByID(Integer id) throws Exception;

}