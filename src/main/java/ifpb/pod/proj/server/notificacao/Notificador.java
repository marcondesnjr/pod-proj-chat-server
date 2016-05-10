/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.proj.server.notificacao;

import ifpb.pod.proj.interfaces.Usuario;
import ifpb.pod.proj.server.ServerImpl;
import ifpb.pod.proj.server.socket.SocketClient;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Notificador extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                List<Usuario> usuarios = new ServerImpl().getUsuarios();
                if (usuarios.size() > 0) {
                    List<Map<String, String>> msgPendentes = new SocketClient().listarMensagensPendentes();
                    for (Usuario usuario : usuarios) {
                        List<Map<String, String>> mensagens = mensagensParaUsuario(msgPendentes, usuario);
                        if (mensagens.size() > 0) {
                            String token = criarNotificação(mensagens);
                            boolean resp = usuario.notificar(token);
                            if (resp == true) {
                                SocketClient socketClient = new SocketClient();
                                for (Map<String, String> mensagen : mensagens) {
                                    socketClient.estadoNotificado(mensagen.get("id"));
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Map<String, String>> mensagensParaUsuario(List<Map<String, String>> msgs, Usuario usr) throws RemoteException {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> msg : msgs) {
            if (msg.get("usuarioId").equals(usr.getEmail())) {
                result.add(msg);
            }
        }
        return result;
    }

    private String criarNotificação(List<Map<String, String>> mensagens) throws IOException, ClassNotFoundException {
        List<Map<String, String>> all = new SocketClient().listarMensagens();
        all.removeIf((Map<String, String> t) -> {
            boolean has = false;
            for (Map<String, String> map : mensagens) {
                if (map.get("mensagemId").equals(t.get("id"))) {
                    has = true;
                }
            }
            return !has;
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, String> msg : all) {
            stringBuilder.append(msg.get("usuarioId")).append(": ")
                    .append(msg.get("conteudo")).append(" via: ").append(msg.get("grupoId"))
                    .append(" data: ").append(msg.get("dataTime")).append(";\n");
        }

        return new SocketClient().criarNotificacao(stringBuilder.toString());
    }

}
