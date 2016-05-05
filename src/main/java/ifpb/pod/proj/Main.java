
package ifpb.pod.proj;

import ifpb.pod.proj.server.ServerImpl;
import ifpb.pod.proj.server.socket.SocketClient;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, IOException {
        Registry registry = LocateRegistry.createRegistry(10800);
        registry.bind("server", new ServerImpl());
        
        new SocketClient().escreverMensagem(null, null, null, null);
    }
}
