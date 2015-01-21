/**
 * @author Boban Jevtic
 * Version: 1.0
 * Task: Distributed PI Calculator
 */
package compute;

import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.*;

import client.Pi;

public class Server implements Compute {

	private String name;
	

	public Server() {
		super();
	}

	public <T> T executeTask(Task<T> t) {
		return t.execute();
	}

	public static void main(String[] args) {
		
		Server server = new Server();
		
		server.setName("Compute");

		server.checkFirstArgument(args);
		
		server.ToSetProperty();
		
		server.toRegistry(args);

	}
	
	/**
	 * Setting & Getting name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Methode, die Argumente checkt
	 * wenn nichts drin steht in den ersten Argument, dann wird false zurueckgeliefert, ansonsten true
	 */
	public boolean checkFirstArgument(String []args) {
		if(args[0] == null) {

			System.out.println("No arguments!");
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
	public void toRegistryExceptionPreaparationServer(String args[]) throws Exception {
		/**LocateRegistry.getRegistry().unbind(name);
		 * 
		 */
		Compute engine = new Server();

		Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
		Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
		registry.rebind(getName(), stub);
		System.out.println("Server bound");
	}
	
	/**
	 * Ausnahmebehandlung, wenn eine Exception von Client kommt wie SecurityException
	 */
	public void toRegistry(String args[]) {
		try {
			toRegistryExceptionPreaparationServer(args);
		} catch (Exception e) {
			System.err.println("Client exception:");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}