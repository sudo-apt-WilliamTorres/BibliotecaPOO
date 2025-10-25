package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioUsuario;
import java.util.List;


public class RetornarUsuarios {

    private final RepositorioUsuario repositorioUsuarios;

    public RetornarUsuarios(RepositorioUsuario repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public List<Usuario> executar() {
        return repositorioUsuarios.listarTodos();
    }
}
