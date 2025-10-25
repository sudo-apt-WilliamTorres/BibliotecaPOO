package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionCaixa;
import br.com.biblioteca.repositorio.RepositorioCaixa;
import java.io.IOException;
import java.util.List;

public class ControladorCaixa {
    
    private final RepositorioCaixa repositorioCaixa;

    public ControladorCaixa(RepositorioCaixa repositorio) {
        this.repositorioCaixa = repositorio;
    }

    public void abrir() throws ExceptionCaixa.CaixaJaAbertoException, IOException {
        new AbrirCaixa(this.repositorioCaixa).executar();
    }

    public void fechar() throws ExceptionCaixa.CaixaNaoAbertoException {
        new FecharCaixa(this.repositorioCaixa).executar();
    }

    public void registrarMovimento(double valor) throws ExceptionCaixa.CaixaNaoAbertoException {
        new RegistrarMovimento(this.repositorioCaixa).executar(valor);
    }

    public List<String> getHistorico() {
        if (this.repositorioCaixa.getCaixaAtual() != null) {
            return this.repositorioCaixa.getCaixaAtual().getHistorico();
        }
        return new java.util.ArrayList<>();
    }
}