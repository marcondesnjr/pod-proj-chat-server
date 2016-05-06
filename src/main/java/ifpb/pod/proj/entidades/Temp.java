/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.entidades;

import ifpb.pod.proj.interfaces.Usuario;
import java.util.List;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Temp {
    private static List<Usuario>  usuarios;
    
    static{
        
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(List<Usuario> usuarios) {
        Temp.usuarios = usuarios;
    }
    
    
}
