package br.com.biblioteca.negocio;


import br.com.biblioteca.excecoes.ExceptionReserva.ReservaJaExistenteException;
import br.com.biblioteca.excecoes.ExceptionUsuario.ReservaLimiteException;
import br.com.biblioteca.negocio.basicas.Livro;
import br.com.biblioteca.negocio.basicas.Reserva;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioReserva;
import java.time.LocalDate;
import java.util.List;

public class CriarReserva {

    private static final int MAX_RESERVAS_ATIVAS = 3;
    private final RepositorioReserva repositorioReservas;

    public CriarReserva(RepositorioReserva repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    public void executar(Livro livro, Usuario usuario, int prazoDias)
            throws ReservaLimiteException, ReservaJaExistenteException {

        if (livro == null || usuario == null) {
            throw new IllegalArgumentException("Livro ou usuário inválido.");
        }

        List<Reserva> reservasAtivas = repositorioReservas.buscarPorUsuario(usuario.getCpf());
        long countAtivas = reservasAtivas.stream().filter(Reserva::isAtiva).count();

        if (countAtivas >= MAX_RESERVAS_ATIVAS) {
            throw new ReservaLimiteException();
        }

        boolean jaReservado = reservasAtivas.stream()
                .anyMatch(r -> r.getLivro().getIsbn().equals(livro.getIsbn()) && r.isAtiva());

        if (jaReservado) {
            throw new ReservaJaExistenteException(livro.getTitulo(), usuario.getCpf());
        }

        LocalDate hoje = LocalDate.now();
        Reserva novaReserva = new Reserva(livro, usuario, hoje, hoje.plusDays(prazoDias));
        repositorioReservas.adicionar(novaReserva);
    }
}
