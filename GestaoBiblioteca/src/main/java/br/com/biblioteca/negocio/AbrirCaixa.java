package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionCaixa;
import br.com.biblioteca.negocio.basicas.Caixa;
import br.com.biblioteca.repositorio.RepositorioCaixa;
import java.io.IOException;

public class AbrirCaixa {

    private RepositorioCaixa repositorioCaixa;
    private Caixa caixa;

    public AbrirCaixa(RepositorioCaixa repositorioCaixa) {
        this.repositorioCaixa = repositorioCaixa;
        this.caixa = repositorioCaixa.getCaixaAtual();
    }

    public void executar() throws ExceptionCaixa.CaixaJaAbertoException, IOException {
        if (caixa.isAberto()) {
            throw new ExceptionCaixa.CaixaJaAbertoException();
        }
        caixa.abrir();
        repositorioCaixa.salvarDados();
    }
}
