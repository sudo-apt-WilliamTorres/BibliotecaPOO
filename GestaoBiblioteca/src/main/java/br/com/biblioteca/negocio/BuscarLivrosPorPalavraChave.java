package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarLivrosPorPalavraChave {

    private final RepositorioLivro repositorioLivro;

    public BuscarLivrosPorPalavraChave(RepositorioLivro repositorioLivro) {
        this.repositorioLivro = repositorioLivro;
    }

    public List<Livro> executar(String palavraChave) {
        return repositorioLivro.listarTodos().stream()
                .filter(l -> l.contemPalavraChave(palavraChave))
                .collect(Collectors.toList());
    }
}
