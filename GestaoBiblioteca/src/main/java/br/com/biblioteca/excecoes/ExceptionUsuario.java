package br.com.biblioteca.excecoes;

public class ExceptionUsuario {

    public static class UsuarioInativoException extends ExceptionBiblioteca {
        public UsuarioInativoException() {
            super("O usuário está inativo e não pode realizar operações.");
        }
    }

    public static class LimiteEmprestimosException extends ExceptionBiblioteca {
        public LimiteEmprestimosException() {
            super("O usuário atingiu o limite de empréstimos ativos.");
        }
    }

    public static class UsuarioNaoEncontradoException extends ExceptionBiblioteca {
        public UsuarioNaoEncontradoException(String cpf) {
            super("Usuário com CPF " + cpf + " não encontrado.");
        }
    }

    public static class ReservaLimiteException extends ExceptionBiblioteca {
        public ReservaLimiteException() {
            super("O usuário atingiu o limite de reservas ativas.");
        }
    }
    public static class UsuarioJaExistenteException extends ExceptionBiblioteca {
        public UsuarioJaExistenteException(String cpf) {
            super("Usuário com CPF " + cpf + " já está cadastrado.");
        }
    }
}
