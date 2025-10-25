package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionLivro.LivroNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;


public class ExcluirLivro {

    private final RepositorioLivro repositorio;

    public ExcluirLivro(RepositorioLivro repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(String isbn) throws LivroNaoEncontradoException {
        Livro livro = repositorio.buscarPorIsbn(isbn);

        if (livro == null) {
            throw new LivroNaoEncontradoException(isbn);
        }

        repositorio.remover(isbn);
    }
}
