package br.com.biblioteca.excecoes;

public class ExceptionPessoa {

    public static class NomeInvalidoException extends ExceptionBiblioteca {
        public NomeInvalidoException() {
            super("O nome informado é inválido ou está vazio.");
        }
    }

    public static class CpfInvalidoException extends ExceptionBiblioteca {
        public CpfInvalidoException() {
            super("O CPF informado é inválido.");
        }
    }

    public static class DataNascimentoInvalidaException extends ExceptionBiblioteca {
        public DataNascimentoInvalidaException() {
            super("A data de nascimento é inválida.");
        }
    }
}
