package excilys.main.service;

public class ImplementationService implements InterfaceService{

	//Singleton
	private ImplementationService(){}
	
	private static ImplementationService INSTANCE = null;

	public static ImplementationService getImplementationService() {
		if (INSTANCE == null) {
			INSTANCE = new ImplementationService();
		}
		return INSTANCE;
	}
	
	//Méthodes
	
	
	
	
	
	
}
