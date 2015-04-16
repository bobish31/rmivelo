

public class LancerServeurRmi {
	
	public static void main(String[] args) {
		
	try
	{		
		// Création du registre
		LocateRegistry.createRegistry(5588);
		
		// Création de l'objet
		ServeurGeneralImpl obj = new ServeurGeneralImpl();
		
		// Création de l'adresse URL
		String url = "rmi://127.0.0.1:5588/ServeurGeneral";
		
		// Enregistrement de l'adresse
		Naming.rebind (url, obj);
		
		// On confirme que tout est ok 
		System.out.println ("Serveur Lancé");
	}
	catch (RemoteException e){
			e.printStackTrace();
	}
	catch (MalformedURLException e){
			e.printStackTrace();
	} 
	catch (UnknownHostException e) {
		e.printStackTrace();
	}
	
		
	}
}

