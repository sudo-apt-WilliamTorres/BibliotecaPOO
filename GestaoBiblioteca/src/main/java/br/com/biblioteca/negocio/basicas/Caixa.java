package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Caixa implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private boolean aberto;
    private double saldo;
    private List<String> historico;

    public Caixa(boolean aberto, double saldoInicial, List<String> historico) {
        this.aberto = aberto;
        this.saldo = saldoInicial;
        this.historico = new ArrayList<>(historico);
    }

    public boolean isAberto() {
        return aberto;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<String> getHistorico() {
        return historico;
    }

    public void abrir() {
        if (!aberto) {
            aberto = true;
            saldo = 0.0;
            limparMovimentos();
            historico.add("Caixa aberto.");
        }
    }

    public void fechar() {
        if (aberto) {
            aberto = false;
            historico.add(String.format("Caixa fechado. Saldo final: R$ %.2f", saldo));
        }
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
        historico.add("Alteração manual do status do caixa: " + (aberto ? "Aberto" : "Fechado"));
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
        historico.add(String.format("Saldo manual atualizado: R$ %.2f", saldo));
    }

    public void setHistorico(List<String> historico) {
        this.historico = new ArrayList<>(historico);
    }

    public void limparMovimentos() {
        historico.clear();
    }

    public void adicionarMovimento(double valor) {
        if (!aberto) throw new IllegalStateException("O caixa não está aberto.");
        saldo += valor;
        historico.add(String.format("Movimento: R$ %.2f", valor));
    }

    @Override
    public String toString() {
        return String.format("Caixa[Aberto=%b, Saldo=R$ %.2f, Movimentos=%d]", aberto, saldo, historico.size());
    }
}
