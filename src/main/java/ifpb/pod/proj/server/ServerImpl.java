/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server;

import ifpb.pod.proj.interfaces.Server;
import ifpb.pod.proj.interfaces.Usuario;
import ifpb.pod.proj.server.socket.SocketClient;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
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
    private static List<Usuario> usuarios;

    static {
        tokens = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public ServerImpl() throws RemoteException {

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
                token = makeToken();
                tokens.add(token);
                usuarios.add(usr);
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
    public void inscreverGrupo(Usuario user, String grupoId, String token) throws RemoteException {
        if (isTokenValid(token)) {
            try {
                new SocketClient().entrarGrupo(user.getEmail(), grupoId);
            } catch (IOException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new RemoteException("Token Invalido");
        }
    }

    @Override
    public void escreverMensagem(String usrEmail, String grupoId, String conteudo, String token) throws RemoteException {
        if (isTokenValid(token)) {
            String dataTime = LocalDateTime.now().toString();
            try {
                new SocketClient().escreverMensagem(usrEmail, dataTime, grupoId, conteudo);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new RemoteException("Token Invalido");
        }
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public void excluirUsuario(Usuario usr, String token) throws RemoteException {
        if (isTokenValid(token)) {
            try {
                new SocketClient().excluirUsuario(usr);
            } catch (IOException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new RemoteException("Token Invalido");
        }
    }

    @Override
    public void logoff(Usuario usr, String token) throws RemoteException {
        tokens.remove(token);
        usuarios.remove(usr);
    }

    private String makeToken() {
        byte[] b = new byte[128];
        try {
            SecureRandom.getInstanceStrong().nextBytes(b);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(b);
    }

    private boolean isTokenValid(String token) {
        return tokens.contains(token);
    }

}
