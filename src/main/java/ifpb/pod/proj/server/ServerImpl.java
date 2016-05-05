/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import ifpb.pod.proj.interfaces.Server;
import ifpb.pod.proj.server.socket.SocketClient;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static List<String> tokens;

    public ServerImpl() throws RemoteException {
        tokens = new ArrayList<>();
    }

    @Override
    public String login(String email, String senha) throws RemoteException {
        try {
            boolean valid = new SocketClient().hasUsuario(email, senha);
            if (valid) {
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[20];
                random.nextBytes(bytes);
                String token = new String(bytes);
                tokens.add(token);
                System.out.println(tokens);
                return token;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String cadastrarUsuario(String nome, String email, String senha) throws RemoteException {
        try {
            new SocketClient().cadastrarUsuario(nome, email, senha);

        } catch (IOException ex) {
            Logger.getLogger(ServerImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Sucess";
    }

}
