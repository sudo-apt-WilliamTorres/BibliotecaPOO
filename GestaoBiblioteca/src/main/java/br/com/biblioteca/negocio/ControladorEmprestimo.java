package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionEmprestimo;
import br.com.biblioteca.excecoes.ExceptionLivro;
import br.com.biblioteca.excecoes.ExceptionUsuario;
import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.repositorio.*;
import java.util.List;

public class ControladorEmprestimo {
    
    private final RepositorioEmprestimo repositorioEmprestimo;
    private final RepositorioLivro repositorioLivro;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioReserva repositorioReserva;

    public ControladorEmprestimo(RepositorioLivro repLivro, RepositorioUsuario repUser, RepositorioReserva repReserva) {
        this.repositorioLivro = repLivro;
        this.repositorioUsuario = repUser;
        this.repositorioReserva = repReserva;
        this.repositorioEmprestimo = new RepositorioEmprestimo(this.repositorioLivro, this.repositorioUsuario);
    }
    
    public void criar(Emprestimo emprestimo) throws ExceptionLivro.LivroNaoEncontradoException,
            ExceptionUsuario.UsuarioNaoEncontradoException, ExceptionEmprestimo.LivroIndisponivelException,
            ExceptionUsuario.LimiteEmprestimosException {
        
        new CriarEmprestimo(
            this.repositorioEmprestimo, 
            this.repositorioUsuario, 
            this.repositorioLivro,
            this.repositorioReserva.listarTodas()
        ).executar(emprestimo);
    }

    public void devolver(String isbn) throws ExceptionEmprestimo.EmprestimoNaoEncontradoException {
        new Devolucao(
            this.repositorioEmprestimo,
            this.repositorioLivro,
            this.repositorioUsuario
        ).executar(isbn);
    }
    
    public List<Emprestimo> listarTodos(){
        return new RetornarEmprestimos(this.repositorioEmprestimo).executar();
    }
}