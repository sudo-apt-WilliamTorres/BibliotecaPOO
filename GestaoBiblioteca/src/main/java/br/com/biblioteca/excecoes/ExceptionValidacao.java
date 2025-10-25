package br.com.biblioteca.excecoes;

public class ExceptionValidacao extends Exception {

    public ExceptionValidacao(String message) {
        super(message);
    }

    public static class CampoObrigatorioException extends ExceptionValidacao {
        public CampoObrigatorioException(String nomeDoCampo) {
            super("O campo '" + nomeDoCampo + "' é obrigatório.");
        }
    }
}