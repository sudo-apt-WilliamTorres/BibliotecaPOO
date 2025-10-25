package br.com.biblioteca.excecoes;

public class ExceptionReserva {

    public static class LimiteReservasException extends ExceptionBiblioteca {
        public LimiteReservasException() {
            super("O usuário atingiu o limite de reservas ativas.");
        }
    }

    public static class LivroJaReservadoException extends ExceptionBiblioteca {
        public LivroJaReservadoException(String titulo) {
            super("O livro '" + titulo + "' já está reservado por outro usuário.");
        }
    }

    public static class ReservaNaoEncontradaException extends Exception {
        public ReservaNaoEncontradaException(String cpf, String isbn) {
            super("Reserva não encontrada para CPF: " + cpf + " e ISBN: " + isbn);
        }
    }
    public static class ReservaJaExistenteException extends ExceptionBiblioteca {
        public ReservaJaExistenteException(String tituloLivro, String cpfUsuario) {
            super("O usuário com CPF " + cpfUsuario + " já possui uma reserva ativa para o livro '" + tituloLivro + "'.");
        }
    }
}
