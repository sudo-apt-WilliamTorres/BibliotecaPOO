package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarLivrosPorSetor {

    private final RepositorioLivro repositorioLivro;

    public BuscarLivrosPorSetor(RepositorioLivro repositorioLivro) {
        this.repositorioLivro = repositorioLivro;
    }

    public List<Livro> executar(String setor) {
        return repositorioLivro.listarTodos().stream()
                .filter(l -> l.getSetor().equalsIgnoreCase(setor))
                .collect(Collectors.toList());
    }
}
