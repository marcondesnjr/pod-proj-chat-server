
package ifpb.pod.proj;

import ifpb.pod.proj.server.ServerImpl;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(10800);
        registry.bind("server", new ServerImpl());
    }
}
