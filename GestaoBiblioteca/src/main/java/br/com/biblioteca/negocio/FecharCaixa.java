package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionCaixa.CaixaNaoAbertoException;
import br.com.biblioteca.repositorio.RepositorioCaixa;

public class FecharCaixa {

    private final RepositorioCaixa repositorioCaixa;

    public FecharCaixa(RepositorioCaixa repositorioCaixa) {
        this.repositorioCaixa = repositorioCaixa;
    }

    public void executar() throws CaixaNaoAbertoException {
        try {
            repositorioCaixa.fecharCaixa();
        } catch (IllegalStateException e) {
            throw new CaixaNaoAbertoException();
        }
    }
}
