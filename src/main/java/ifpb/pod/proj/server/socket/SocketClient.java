/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server.socket;

import ifpb.pod.proj.interfaces.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

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

    public boolean hasUsuario(String email, String senha) throws IOException {
        Socket socket = new Socket("localhost", 10999);
        String command = "hasUsuario?email=" + email + "&senha=" + senha;
        System.out.println(command);
        socket.getOutputStream().write(command.getBytes("UTF-8"));
        byte[] b = new byte[128];
        socket.getInputStream().read(b);
        System.out.println("resposta ao login: " + new String(b).trim() );
        return new String(b).trim().equals("true");
    }

//    public void escreverMensagem(String email, String dateTime, String groupId, String conteudo) throws IOException {
//        Socket socket = new Socket("localhost", 10999);
//        String command = "escreverMensagem?email=email&dataTime=hoje&grupoId=idGrupo&conteudo=este é o conteudo";
//        socket.getOutputStream().write(command.getBytes("UTF-8"));
//    }
    public void entrarGrupo(String usrEmail, String groupId) throws IOException {
        Socket socket = new Socket("localhost", 10999);
        String command = "entrarGrupo?email=" + usrEmail + "&grupoId=" + groupId;
        socket.getOutputStream().write(command.getBytes("UTF-8"));
    }
    
    public void escreverMensagem(String usrEmail, String dateTime, String groupId, String conteudo) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 10999);
        String command = "escreverMensagem?email=" + usrEmail + "&grupoId=" + groupId+"&dateTime="+dateTime
                +"&conteudo="+conteudo;

        System.out.println("SOCKETCLIENT.ESCREVERMENSAGEM ->");
        System.out.println(command);

        socket.getOutputStream().write(command.getBytes("UTF-8"));
        System.out.println((Boolean) new ObjectInputStream(socket.getInputStream()).readObject());
    }
    
    public List<Map<String,String>> listarMensagensPendentes() throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 10999);
        String command = "listarPendentes?";
        socket.getOutputStream().write(command.getBytes("UTF-8"));
        byte[] b = new byte[128];
        List<Map<String,String>> list = (List<Map<String,String>>) new ObjectInputStream(socket.getInputStream()).readObject();
        return list;
    }
    
    public List<Map<String,String>> listarMensagens() throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost", 10999);
        String command = "listarMensagens?";
        socket.getOutputStream().write(command.getBytes("UTF-8"));
        byte[] b = new byte[128];
        List<Map<String,String>> list = (List<Map<String,String>>) new ObjectInputStream(socket.getInputStream()).readObject();
        return list;
    }
    
    public String criarNotificacao(String txt) throws IOException{
        Socket socket = new Socket("localhost", 10999);
        String command = "criarNotificacao?text="+txt;
        socket.getOutputStream().write(command.getBytes("UTF-8"));
        byte[] b = new byte[128];
        socket.getInputStream().read(b);
        return new String(b);
    }
    
    public void estadoNotificado(String id) throws IOException{
        Socket socket = new Socket("localhost", 10999);
        String command = "estadoNotificado?id="+id;
        socket.getOutputStream().write(command.getBytes("UTF-8"));
    }
    
    public void excluirUsuario(Usuario usr) throws IOException{
        Socket socket = new Socket("localhost", 10999);
        String command = "excuirUsuario?email="+usr.getEmail();
        socket.getOutputStream().write(command.getBytes("UTF-8"));
    }

}
