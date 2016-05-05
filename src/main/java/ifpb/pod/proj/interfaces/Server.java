/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface Server extends Remote{
    String login(String email, String senha) throws RemoteException;
    String cadastrarUsuario(String nome, String email, String senha) throws RemoteException;
}
