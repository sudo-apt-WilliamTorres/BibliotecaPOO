package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionLivro.LivroNaoEncontradoException;
import br.com.biblioteca.repositorio.RepositorioLivro;

import br.com.biblioteca.negocio.basicas.Livro;

public class EditarLivro {

    private final RepositorioLivro repositorio;

    public EditarLivro(RepositorioLivro repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(String isbn, String novoTitulo) throws LivroNaoEncontradoException {
        Livro livro = repositorio.buscarPorIsbn(isbn);

        if (livro == null) {
            throw new LivroNaoEncontradoException(isbn);
        }

        livro.setTitulo(novoTitulo);
        repositorio.atualizar(livro);
    }
}
