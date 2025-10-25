package br.com.biblioteca.excecoes;

public class ExceptionCaixa {

    public static class CaixaFechadoException extends ExceptionBiblioteca {
        public CaixaFechadoException() {
            super("O caixa está fechado. Não é possível registrar movimentos.");
        }
    }

    public static class CaixaJaAbertoException extends ExceptionBiblioteca {
        public CaixaJaAbertoException() {
            super("O caixa já está aberto.");
        }
    }

    public static class CaixaJaFechadoException extends ExceptionBiblioteca {
        public CaixaJaFechadoException() {
            super("O caixa já está fechado.");
        }
    }
    public static class CaixaNaoAbertoException extends ExceptionBiblioteca {
        public CaixaNaoAbertoException() {
            super("O caixa não está aberto.");
        }
    }

    public static class SaldoInsuficienteException extends ExceptionBiblioteca {
        public SaldoInsuficienteException(double saldo, double valor) {
            super("Saldo insuficiente no caixa. Saldo atual: " + saldo + ", valor solicitado: " + valor);
        }
    }
}

