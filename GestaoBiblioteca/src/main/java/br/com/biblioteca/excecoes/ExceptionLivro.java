package br.com.biblioteca.excecoes;

public class ExceptionLivro {

    public static class LivroException extends Exception {
        public LivroException(String mensagem) {
            super(mensagem);
        }
    }

    public static class LivroNaoEncontradoException extends LivroException {
        public LivroNaoEncontradoException(String isbn) {
            super("Livro não encontrado.");
        }
    }

    public static class LivroJaCadastradoException extends LivroException {
        public LivroJaCadastradoException() {
            super("Livro já cadastrado no sistema.");
        }
    }
    public static class LivroJaExistenteException extends ExceptionBiblioteca {
        public LivroJaExistenteException(String isbn) {
            super("Já existe um livro cadastrado com o ISBN " + isbn + ".");
        }
    }

    public static class LivroIndisponivelException extends LivroException {
        public LivroIndisponivelException() {
            super("Livro indisponível para empréstimo.");
        }
    }

    public static class PalavraChaveNaoEncontradaException extends LivroException {
        public PalavraChaveNaoEncontradaException() {
            super("Palavra-chave não encontrada em nenhum livro.");
        }
    }
}
