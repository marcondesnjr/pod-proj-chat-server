package ifpb.pod.proj.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface Server extends Remote {

    String login(Usuario usr) throws RemoteException;

    String cadastrarUsuario(String nome, String email, String senha) throws RemoteException;

    void inscreverGrupo(Usuario user, String grupoId, String token) throws RemoteException;

    void escreverMensagem(String usrEmail, String grupoId, String conteudo, String token) throws RemoteException;
    
    void excluirUsuario(Usuario usr, String token) throws RemoteException;
    
    void logoff(Usuario usr, String token) throws RemoteException;
}
