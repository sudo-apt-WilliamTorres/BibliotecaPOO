package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionEmprestimo.LivroIndisponivelException;
import br.com.biblioteca.excecoes.ExceptionLivro.LivroNaoEncontradoException;
import br.com.biblioteca.excecoes.ExceptionUsuario.LimiteEmprestimosException;
import br.com.biblioteca.excecoes.ExceptionUsuario.UsuarioNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.negocio.basicas.Reserva;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioEmprestimo;
import br.com.biblioteca.repositorio.RepositorioLivro;
import br.com.biblioteca.repositorio.RepositorioUsuario;
import java.util.List;

public class CriarEmprestimo {

    private final RepositorioEmprestimo repositorioEmprestimos;
    private final RepositorioUsuario repositorioUsuarios;
    private final RepositorioLivro repositorioLivros;
    private final List<Reserva> reservas;

    public CriarEmprestimo(RepositorioEmprestimo repositorioEmprestimos,
                           RepositorioUsuario repositorioUsuarios,
                           RepositorioLivro repositorioLivros,
                           List<Reserva> reservas) {
        this.repositorioEmprestimos = repositorioEmprestimos;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioLivros = repositorioLivros;
        this.reservas = reservas;
    }

    public void executar(Emprestimo novoEmprestimo)
            throws LivroNaoEncontradoException, UsuarioNaoEncontradoException,
                   LivroIndisponivelException, LimiteEmprestimosException {

        if (novoEmprestimo == null || novoEmprestimo.getLivro() == null || novoEmprestimo.getUsuario() == null) {
            throw new IllegalArgumentException("O objeto de empréstimo e seus componentes (livro, usuário) não podem ser nulos.");
        }

        if (novoEmprestimo.getPrevistaDevolucao().isBefore(novoEmprestimo.getDataEmprestimo())) {
            throw new IllegalArgumentException("A data de devolução não pode ser anterior à data do empréstimo.");
        }

        Livro l = repositorioLivros.buscarPorIsbn(novoEmprestimo.getLivro().getIsbn());
        Usuario u = repositorioUsuarios.buscarPorCpf(novoEmprestimo.getUsuario().getCpf());

        if (l == null) throw new LivroNaoEncontradoException(novoEmprestimo.getLivro().getIsbn());
        if (u == null) throw new UsuarioNaoEncontradoException(novoEmprestimo.getUsuario().getCpf());

        Reserva reservaAtiva = reservas.stream()
                .filter(r -> r.getLivro().getIsbn().equals(l.getIsbn()) && r.isAtiva())
                .findFirst()
                .orElse(null);

        if (reservaAtiva != null && !reservaAtiva.getUsuario().getCpf().equals(u.getCpf())) {
            throw new LivroIndisponivelException("Livro reservado para outro usuário: " +
                                                 reservaAtiva.getUsuario().getNome());
        }

        if (!l.isDisponivel()) {
            throw new LivroIndisponivelException("Livro indisponível para empréstimo.");
        }

        if (!u.podeEmprestar()) {
            throw new LimiteEmprestimosException();
        }

        l.setDisponivel(false);
        u.adicionarEmprestimo();
        repositorioEmprestimos.adicionarEmprestimo(novoEmprestimo);

        if (reservaAtiva != null) {
            reservaAtiva.setAtiva(false);
        }
        
    }
}