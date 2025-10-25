package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionCaixa.CaixaNaoAbertoException;
import br.com.biblioteca.repositorio.RepositorioCaixa;

public class RegistrarMovimento {

    private final RepositorioCaixa repositorioCaixa;

    public RegistrarMovimento(RepositorioCaixa repositorioCaixa) {
        this.repositorioCaixa = repositorioCaixa;
    }

    public void executar(double valor) throws CaixaNaoAbertoException {
        try {
            repositorioCaixa.adicionarSaldo(valor);
        } catch (IllegalStateException e) {
            throw new CaixaNaoAbertoException();
        }
    }
}
