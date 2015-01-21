/**
 * @author Boban Jevtic
 * Version: 1.0
 * Task: Distributed PI Calculator
 */
package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Compute extends Remote {
	
	<T> T executeTask(Task<T> t) throws RemoteException;

}
