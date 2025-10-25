package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Funcionario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioFuncionario {

    private final List<Funcionario> funcionarios;
    private static final String CSV_PATH = "funcionarios.csv";
    private static final String DAT_PATH = "funcionarios.dat";
    
    public RepositorioFuncionario() {
        this.funcionarios = carregarDados();
    }
    
    public void adicionar(Funcionario funcionario) {
        funcionarios.add(funcionario);
        salvarDados();
    }
    
    public void atualizar(Funcionario funcionarioAtualizado) {
        funcionarios.removeIf(f -> f.getCpf().equals(funcionarioAtualizado.getCpf()));
        funcionarios.add(funcionarioAtualizado);
        salvarDados();
    }
    
    public boolean removerPorCpf(String cpf) {
        boolean removeu = funcionarios.removeIf(f -> f.getCpf().equals(cpf));
        if (removeu) {
            salvarDados();
        }
        return removeu;
    }
    
    public Funcionario buscarFuncionarioPorCpf(String cpf) {
        return funcionarios.stream()
                           .filter(f -> f.getCpf().equals(cpf))
                           .findFirst()
                           .orElse(null);
    }
    
    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }
    
    public boolean existeFuncionario(String cpf) {
        return funcionarios.stream().anyMatch(f -> f.getCpf().equals(cpf));
    }
    
    private void salvarDados() {
        try {
            salvarEmFormatoNativo();
            salvarEmFormatoCsv();
        } catch (IOException e) {
            System.err.println("ERRO CRÍTICO: Falha ao salvar os dados dos funcionários.");
            e.printStackTrace();
        }
    }

    private List<Funcionario> carregarDados() {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            List<Funcionario> funcionariosNativos = carregarDeFormatoNativo();
            if (funcionariosNativos != null) {
                return funcionariosNativos;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            return carregarDeFormatoCsv();
        }

        return new ArrayList<>();
    }

    private void salvarEmFormatoNativo() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.funcionarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar funcionarios em formato nativo (.dat)", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Funcionario> carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (List<Funcionario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void salvarEmFormatoCsv() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("nome,cpf,dataNascimento,logradouro,numero,complemento,bairro,cidade,estado,cep,ddd,numero_tel,cargo");
            bw.newLine();
            for (Funcionario f : this.funcionarios) {
                bw.write(f.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar funcionarios em formato CSV", e);
        }
    }

    private List<Funcionario> carregarDeFormatoCsv() {
        List<Funcionario> funcionariosCarregados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                funcionariosCarregados.add(Funcionario.fromCSV(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return funcionariosCarregados;
    }
}