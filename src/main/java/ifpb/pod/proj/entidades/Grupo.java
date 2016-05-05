package ifpb.pod.proj.entidades;

import ifpb.pod.proj.interfaces.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Grupo {

    private List<Usuario> participantes;
    private String id;
    private String nome;

    public Grupo(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.participantes = new ArrayList<>();
    }

    public void addUsuario(Usuario s) {
        participantes.add(s);
    }

    public List<Usuario> getUsers() {
        return Collections.unmodifiableList(participantes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
