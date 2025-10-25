package br.com.biblioteca.excecoes;

import br.com.biblioteca.excecoes.ExceptionBiblioteca;

public class ExceptionSetor{
    
    public static class SetorJaExistenteException extends Exception {
        public SetorJaExistenteException(String nomeSetor) {
            super("O setor '" + nomeSetor + "' já existe.");
        }
    }
    public static class SetorNaoEncontradoException extends ExceptionBiblioteca {
        public SetorNaoEncontradoException(String nome) {
            super("Setor '" + nome + "' não encontrado.");
        }
    }
}
