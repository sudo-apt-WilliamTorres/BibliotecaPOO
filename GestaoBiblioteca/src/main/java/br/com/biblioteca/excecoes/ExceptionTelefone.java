package br.com.biblioteca.excecoes;

public class ExceptionTelefone{
    public class TelefoneInvalidoException extends ExceptionBiblioteca {
        public TelefoneInvalidoException() {
            super("Telefone inválido. Verifique DDD e número.");
        }

        public TelefoneInvalidoException(String mensagem) {
            super(mensagem);
        }
    }
}
