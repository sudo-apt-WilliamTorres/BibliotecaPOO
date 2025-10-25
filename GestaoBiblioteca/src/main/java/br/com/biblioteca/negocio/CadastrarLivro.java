package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionLivro.LivroJaExistenteException;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;

public class CadastrarLivro {

    private final RepositorioLivro livroRepositorio;

    public CadastrarLivro(RepositorioLivro livroRepository) {
        this.livroRepositorio = livroRepository;
    }

    public void executar(Livro livro) throws LivroJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        if (livro == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Livro");
        }
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Título");
        }
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Autor");
        }
        if (livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("ISBN");
        }
        if (livro.getSetor() == null || livro.getSetor().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Setor");
        }

        if (livroRepositorio.buscarPorIsbn(livro.getIsbn()) != null) {
            throw new LivroJaExistenteException(livro.getIsbn());
        }
        
        livroRepositorio.adicionar(livro);
    }
}