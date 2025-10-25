package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.repositorio.RepositorioEmprestimo;


public class BuscarEmprestimoPorIsbn {

    private final RepositorioEmprestimo repositorioEmprestimos;

    public BuscarEmprestimoPorIsbn(RepositorioEmprestimo repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public Emprestimo executar(String isbn) {
        return repositorioEmprestimos.retornaEmprestimos()
                                     .stream()
                                     .filter(e -> e.getLivro().getIsbn().equals(isbn))
                                     .findFirst()
                                     .orElse(null);
    }
}
