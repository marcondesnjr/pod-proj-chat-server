/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server;

import ifpb.pod.proj.interfaces.Server;
import ifpb.pod.proj.interfaces.Usuario;
import ifpb.pod.proj.server.socket.SocketClient;
import ifpb.pod.proj.sessiontoken.SessionToken;

import javax.naming.AuthenticationException;
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
                System.out.println("email: " + usr.getEmail());
                String token = SessionToken.generateToken(usr.getEmail());
                System.out.println("token gerado: " + token);
                //no lugar disso, deve adicionar o EMAIL DO USUARIO no objeto embutido no token.
                tokens.add(token);
                usuarios.add(usr);
                return token;
            }

            System.out.println("usuario ou senha errado");
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
    public void inscreverGrupo(Usuario user, String grupoId, String sessionToken) throws RemoteException, AuthenticationException {
        if (SessionToken.getEmailFromToken(sessionToken) == null) {
            throw new AuthenticationException("Usuário não está logado");
        }
        try {
            new SocketClient().entrarGrupo(user.getEmail(), grupoId);
        } catch (IOException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public void excluirUsuario(Usuario usr, String sessionToken) throws RemoteException, AuthenticationException {
        if (SessionToken.getEmailFromToken(sessionToken) == null) {
            throw new AuthenticationException("Usuário não está logado");
        }
        try {
            new SocketClient().excluirUsuario(usr);
        } catch (IOException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void logoff(Usuario usr, String token) throws RemoteException {
        tokens.remove(token);
        boolean resp = usuarios.remove(usr);
        if(resp == true)
            System.out.println("DESLOGOU USUARIO: " + usr.getEmail());
        else
            System.out.println("NAO REMOVEU USUARIO");

        System.out.println("restantes");
        System.out.println(usuarios);
        System.out.println(tokens);
    }

    @Override
    public void escreverMensagem(String usrEmail, String grupoId, String conteudo, String sessionToken) throws RemoteException, AuthenticationException {
        System.out.println("escreverMensagem ysertoken: " + sessionToken);
        if (SessionToken.getEmailFromToken(sessionToken) == null) {
            throw new AuthenticationException("Usuário não está logado");
        }
        String dataTime = LocalDateTime.now().toString();
        try {
            new SocketClient().escreverMensagem(usrEmail, dataTime, grupoId, conteudo);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
