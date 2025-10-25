package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionReserva;
import br.com.biblioteca.excecoes.ExceptionUsuario;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.negocio.basicas.Reserva;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioLivro;
import br.com.biblioteca.repositorio.RepositorioReserva;
import br.com.biblioteca.repositorio.RepositorioUsuario;
import java.util.List;

public class ControladorReserva {

    private final RepositorioReserva repositorioReserva;
    private final RepositorioLivro repositorioLivro;
    private final RepositorioUsuario repositorioUsuario;

    public ControladorReserva(RepositorioReserva repositorioReserva, RepositorioLivro repositorioLivro, RepositorioUsuario repositorioUsuario) {
        this.repositorioReserva = repositorioReserva;
        this.repositorioLivro = repositorioLivro;
        this.repositorioUsuario = repositorioUsuario;
    }

    public void criar(Livro livro, Usuario usuario, int prazoDias) throws ExceptionUsuario.ReservaLimiteException, ExceptionReserva.ReservaJaExistenteException {
        new CriarReserva(this.repositorioReserva).executar(livro, usuario, prazoDias);
    }

    public void cancelar(String cpfUsuario, String isbnLivro) throws ExceptionReserva.ReservaNaoEncontradaException {
        new CancelarReserva(this.repositorioReserva).executar(cpfUsuario, isbnLivro);
    }

    public List<Reserva> buscarPorUsuario(String cpfUsuario) {
        return new BuscarReservasPorUsuario(this.repositorioReserva).executar(cpfUsuario);
    }
}