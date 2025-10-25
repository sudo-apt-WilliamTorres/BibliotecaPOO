package br.com.biblioteca.excecoes;

public class ExceptionEmprestimo {

    public static class LivroIndisponivelException extends ExceptionBiblioteca {
        public LivroIndisponivelException(String titulo) {
            super("O livro '" + titulo + "' está indisponível para empréstimo.");
        }
    }

    public static class UsuarioNaoPodeEmprestarException extends ExceptionBiblioteca {
        public UsuarioNaoPodeEmprestarException(String nome) {
            super("O usuário '" + nome + "' atingiu o limite de empréstimos ativos.");
        }
    }

    public static class EmprestimoNaoEncontradoException extends ExceptionBiblioteca {
        public EmprestimoNaoEncontradoException(String isbn) {
            super("Empréstimo do livro com ISBN " + isbn + " não encontrado.");
        }
    }

    public static class ReservaAtivaException extends ExceptionBiblioteca {
        public ReservaAtivaException(String titulo, String nomeUsuario) {
            super("O livro '" + titulo + "' está reservado para outro usuário: " + nomeUsuario);
        }
    }
}

