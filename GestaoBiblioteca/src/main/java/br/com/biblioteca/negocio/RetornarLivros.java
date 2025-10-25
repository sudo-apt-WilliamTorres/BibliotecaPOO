package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;
import java.util.List;


public class RetornarLivros {

    private final RepositorioLivro repositorio;

    public RetornarLivros(RepositorioLivro repositorio) {
        this.repositorio = repositorio;
    }

    public List<Livro> executar() {
        return repositorio.listarTodos();
    }
}
