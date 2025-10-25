package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionReserva.ReservaNaoEncontradaException;
import br.com.biblioteca.negocio.basicas.Reserva;
import br.com.biblioteca.repositorio.RepositorioReserva;


public class CancelarReserva {

    private final RepositorioReserva repositorioReservas;

    public CancelarReserva(RepositorioReserva repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    public void executar(String cpfUsuario, String isbnLivro) throws ReservaNaoEncontradaException {
        Reserva reserva = repositorioReservas.buscarPorCpfEIsbn(cpfUsuario, isbnLivro);

        if (reserva == null || !reserva.isAtiva()) {
            throw new ReservaNaoEncontradaException(cpfUsuario, isbnLivro);
        }

        reserva.setAtiva(false);
        repositorioReservas.atualizar(reserva);
    }
}

