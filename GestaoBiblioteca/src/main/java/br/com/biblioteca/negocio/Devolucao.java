package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionEmprestimo.EmprestimoNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioEmprestimo;
import br.com.biblioteca.repositorio.RepositorioLivro;
import br.com.biblioteca.repositorio.RepositorioUsuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Devolucao {

    private final RepositorioEmprestimo repositorioEmprestimos;
    private final RepositorioLivro repositorioLivros;
    private final RepositorioUsuario repositorioUsuarios;
    
    public Devolucao(RepositorioEmprestimo repositorioEmprestimos, RepositorioLivro repositorioLivros, RepositorioUsuario repositorioUsuarios) {
        this.repositorioEmprestimos = repositorioEmprestimos;
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void executar(String isbn) throws EmprestimoNaoEncontradoException {
        Emprestimo emprestimo = repositorioEmprestimos.buscarEmprestimoPorIsbn(isbn)
                .orElse(null);

        if (emprestimo == null || emprestimo.isDevolvido()) {
            throw new EmprestimoNaoEncontradoException(isbn);
        }

        Livro livro = emprestimo.getLivro();
        Usuario usuario = emprestimo.getUsuario();

        try {
            LocalDate hoje = LocalDate.now();
            emprestimo.setDevolvido(hoje);
            livro.setDisponivel(true);
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getPrevistaDevolucao(), hoje);
            if (diasAtraso > 0) {
                CalcularValorMulta calcularMulta = new CalcularValorMulta(repositorioEmprestimos);
                double multa = calcularMulta.executar(isbn, (int)diasAtraso);
                usuario.adicionarMulta(multa);
            }
            repositorioLivros.atualizar(livro);
            repositorioUsuarios.atualizar(usuario);
            repositorioEmprestimos.atualizar(emprestimo);
        } catch (EmprestimoNaoEncontradoException e) {
            repositorioLivros.salvarDados();
            repositorioUsuarios.salvarDados();
            throw e;
        }
    }
}
