package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Caixa;
import java.io.*; 
import java.util.ArrayList;
import java.util.List;


public class RepositorioCaixa {

    private Caixa caixaAtual;
    private static final String CSV_PATH = "caixa.csv";
    private static final String DAT_PATH = "caixa.dat";
    
    public RepositorioCaixa() {
        this.caixaAtual = carregarDados();
    }

    public void abrirCaixa() throws IllegalStateException {
        caixaAtual.abrir();
        salvarDados();
    }

    public void fecharCaixa() throws IllegalStateException {
        caixaAtual.fechar();
        salvarDados();
    }

    public void adicionarSaldo(double valor) {
        caixaAtual.adicionarMovimento(valor);
        salvarDados();
    }
    
    public Caixa getCaixaAtual() {
        return this.caixaAtual;
    }
    
    public void salvarDados() {
        salvarEmFormatoNativo();
        salvarEmFormatoCsv();
    }

    private Caixa carregarDados() {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            Caixa caixaNativo = carregarDeFormatoNativo();
            if (caixaNativo != null) {
                return caixaNativo;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            Caixa caixaCsv = carregarDeFormatoCsv();
            if (caixaCsv != null) {
                return caixaCsv;
            }
        }

        return new Caixa(false, 0.0, new ArrayList<>());
    }

    private void salvarEmFormatoNativo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.caixaAtual);
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar o estado do caixa no arquivo .dat", e);
        }
    }

    private Caixa carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (Caixa) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null; 
        }
    }
    
    private void salvarEmFormatoCsv() {
        try (FileWriter fw = new FileWriter(CSV_PATH)) {
            fw.write(caixaAtual.isAberto() + "," + caixaAtual.getSaldo() + "\n");

            for (String movimento : caixaAtual.getHistorico()) {
                fw.write(movimento + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o estado do caixa no arquivo .csv", e);
        }
    }

    private Caixa carregarDeFormatoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String primeiraLinha = br.readLine();
            if (primeiraLinha != null) {
                String[] partes = primeiraLinha.split(",");
                boolean aberto = Boolean.parseBoolean(partes[0]);
                double saldo = Double.parseDouble(partes[1]);
                List<String> historico = new ArrayList<>();
                String linha;
                while ((linha = br.readLine()) != null) {
                    historico.add(linha);
                }
                return new Caixa(aberto, saldo, historico);
            }
        } catch (IOException | NumberFormatException e) {
        	throw new RuntimeException("Falha ao carregar o estado do caixa do arquivo .csv", e);
        }
        return null;
    }

}
