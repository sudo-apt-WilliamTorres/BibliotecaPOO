package br.com.biblioteca.excecoes;

public class ExceptionFuncionario {

    public static class FuncionarioNaoEncontradoException extends ExceptionBiblioteca {
        public FuncionarioNaoEncontradoException(String cpf) {
            super("Funcionário com CPF " + cpf + " não encontrado.");
        }
    }

    public static class FuncionarioJaExistenteException extends ExceptionBiblioteca {
        public FuncionarioJaExistenteException(String cpf) {
            super("Funcionário com CPF " + cpf + " já está cadastrado.");
        }
    }

    public static class CargoInvalidoException extends ExceptionBiblioteca {
        public CargoInvalidoException() {
            super("Cargo inválido para o funcionário.");
        }
    }
}
