package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Reserva;
import br.com.biblioteca.repositorio.RepositorioReserva;
import java.util.List;

public class BuscarReservasPorUsuario {

    private final RepositorioReserva repositorioReservas;

    public BuscarReservasPorUsuario(RepositorioReserva repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    public List<Reserva> executar(String cpfUsuario) {
        return repositorioReservas.buscarPorUsuario(cpfUsuario);
    }
}
