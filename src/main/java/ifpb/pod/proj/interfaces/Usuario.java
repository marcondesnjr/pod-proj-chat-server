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
public interface Usuario extends Remote{
    String getEmail() throws RemoteException;
    String getSenha() throws RemoteException;
    boolean notificar(String str) throws RemoteException;
}
