/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server.socket;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class SocketClient {

    public void cadastrarUsuario(String nome, String email, String senha) throws IOException {
        Socket socket = new Socket("localhost", 10999);
        String command = "cadastrarUsuario?nome=" + nome + "&email=" + email + "&senha=" + senha;
        socket.getOutputStream().write(command.getBytes("UTF-8"));
    }

}