/**
 * @author Boban Jevtic
 * Version: 1.0
 * Task: Distributed PI Calculator
 */
package client;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import compute.Compute;


public class Client {

	public static void main(String args[]) throws Exception {
		
		/**
		 * Erzeuge ein neues Objekt Client
		 */
		Client client = new Client();

		client.checkArguments(args);
		client.ToSetProperty();
		client.toRegistry(args);
		

	}
	
	/**
	 * Methode, die Argumente prueft
	 */
	public boolean checkArguments(String args[]) {
		
		/** Es wird verlangt, dass man Argumente angibt
		 * Entweder, wenn der erste Argument, der zweite Argument oder 3 Argument null (leer) ist, 
		 * dann wird kein Argument ausgegeben
		 * 
		 */
		
		if(args[0] == null || args[1] == null || args[2] == null) {

			System.out.println("There are no arguments!");
			System.exit(-1);
			return false;
			
		} else {
			
			return true;
		}
	}
	
	/**
	 * In dieser Methode wird die Eigenschaft fuer security policy fuer Pi gesetzt
	 */
	public void ToSetProperty() {
		if (System.getSecurityManager() == null) {
			System.setProperty("java.security.policy", "file:./policy/pi.policy");
			System.setSecurityManager(new SecurityManager());
		}
	}
	
	/**
	 * Diese Methode bereitet fuer die Ausnahmebehandlung in der naechsten Methode vor, die
	 * in der toRegistry () - Methode aufgerufen wird
	 */
	public void toRegistryExceptionPreaparationClient(String args[]) throws Exception {
		String name = "Compute";
		
		/** IP Adresse und Port fuer die Registrierung
		 * 
		 */
		Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
		Compute compute = (Compute) registry.lookup(name);
		
		/**
		 *  Laenge von PI ermitteln
		 */
		Pi task = new Pi(Integer.parseInt(args[2]));
		
		/**
		 * Fuehre den Task aus und gib PI aus
		 */
		BigDecimal pi = compute.executeTask(task);
		System.out.println(pi);
	}
	
	/**
	 * Ausnahmebehandlung, wenn eine Exception von Client kommt wie SecurityException
	 */
	public void toRegistry(String args[]) {
		try {
			toRegistryExceptionPreaparationClient(args);
		} catch (Exception e) {
			System.err.println("Client exception:");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
