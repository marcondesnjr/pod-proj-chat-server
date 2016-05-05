/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server;

import ifpb.pod.proj.entidades.Grupo;
import ifpb.pod.proj.interfaces.Server;
import ifpb.pod.proj.interfaces.Usuario;
import ifpb.pod.proj.server.socket.SocketClient;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
    private static List<Grupo> grupos;

    public ServerImpl() throws RemoteException {
        tokens = new ArrayList<>();
        grupos = new ArrayList<>();
        grupos.add(new Grupo("id", "grupo1"));
    }

    @Override
    public String login(Usuario usr) throws RemoteException {
        try {
            boolean valid = new SocketClient().hasUsuario(usr.getEmail(), usr.getSenha());
            if (valid) {
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[20];
                random.nextBytes(bytes);
                String token = new String(bytes);
                token = token + "&" + usr.getEmail();
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

    @Override
    public void inscreverGrupo(String token, Usuario user, String grupoId) {
        if (tokens.contains(token)) {
            Grupo gp = null;
            for (Grupo atual : grupos) {
                if (atual.getId().equals(grupoId)) {
                    gp = atual;
                }
            }
            if (gp != null) {
                gp.addUsuario(user);
            }
        }
    }

    @Override
    public void escreverMensagem(String grupoId) throws RemoteException {
        
    }

}
