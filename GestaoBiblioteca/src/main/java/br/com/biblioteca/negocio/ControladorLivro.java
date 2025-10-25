package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionLivro;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.repositorio.RepositorioLivro;
import java.util.List;

public class ControladorLivro {
    
    private final RepositorioLivro repositorioLivro;

    public ControladorLivro(RepositorioLivro repositorio) {
        this.repositorioLivro = repositorio;
    }

    public void cadastrar(Livro livro) throws ExceptionLivro.LivroJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        new CadastrarLivro(this.repositorioLivro).executar(livro);
    }

    public void excluir(String isbn) throws ExceptionLivro.LivroNaoEncontradoException {
        new ExcluirLivro(this.repositorioLivro).executar(isbn);
    }

    public void editar(String isbn, String novoTitulo) throws ExceptionLivro.LivroNaoEncontradoException {
        new EditarLivro(this.repositorioLivro).executar(isbn, novoTitulo);
    }
    
    public List<Livro> listarTodos() {
        return new RetornarLivros(this.repositorioLivro).executar();
    }

    public Livro buscarPorIsbn(String isbn) {
        return this.repositorioLivro.buscarPorIsbn(isbn);
    }
    
    public List<Livro> buscarPorPalavraChave(String palavra) {
        return new BuscarLivrosPorPalavraChave(this.repositorioLivro).executar(palavra);
    }

    public List<Livro> buscarPorSetor(String setor) {
        return new BuscarLivrosPorSetor(this.repositorioLivro).executar(setor);
    }
}