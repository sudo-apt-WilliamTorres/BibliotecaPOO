package br.com.biblioteca.excecoes;

public class ExceptionEndereco {
    public class EnderecoInvalidoException extends ExceptionBiblioteca {
        public EnderecoInvalidoException() {
            super("Endereço inválido. Verifique os campos obrigatórios.");
        }

        public EnderecoInvalidoException(String mensagem) {
            super(mensagem);
        }
    }
}
