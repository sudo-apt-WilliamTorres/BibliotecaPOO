package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Emprestimo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioEmprestimo {

    private final List<Emprestimo> emprestimos;
    private static final String CSV_PATH = "emprestimos.csv";
    private static final String DAT_PATH = "emprestimos.dat";
    
    public RepositorioEmprestimo(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        this.emprestimos = carregarDados(repoLivros, repoUsuarios);
    }
    
    private void salvarDados() {
        salvarEmFormatoNativo();
        salvarEmFormatoCsv();
    }

    private List<Emprestimo> carregarDados(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            List<Emprestimo> emprestimosNativos = carregarDeFormatoNativo();
            if (emprestimosNativos != null) {
                return emprestimosNativos;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            return carregarDeFormatoCsv(repoLivros, repoUsuarios);
        }

        return new ArrayList<>();
    }
    
    private void salvarEmFormatoNativo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.emprestimos);
        } catch (IOException e) {
            throw new RuntimeException("Falha crítica ao salvar os empréstimos no arquivo .dat", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Emprestimo> carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (List<Emprestimo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Falha crítica ao carregar os empréstimos do arquivo .dat", e);
        }
    }
    
    private void salvarEmFormatoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("isbn_livro,cpf_usuario,data_emprestimo,data_prevista,data_devolucao,devolvido");
            bw.newLine();
            for (Emprestimo e : this.emprestimos) {
                bw.write(e.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha crítica ao salvar os empréstimos no arquivo .csv", e);
        }
    }

    private List<Emprestimo> carregarDeFormatoCsv(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        List<Emprestimo> emprestimosCarregados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                Emprestimo e = Emprestimo.fromCSV(line, repoLivros, repoUsuarios);
                if (e != null) {
                    emprestimosCarregados.add(e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha crítica ao carregar os empréstimos do arquivo .csv. O arquivo pode estar corrompido.", e);
        }
        return emprestimosCarregados;
    }
    
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
        salvarDados();
    }
    
    public void atualizar(Emprestimo emprestimo) {
        salvarDados();
    }

    public boolean removerEmprestimoPorIsbn(String isbn) {
        boolean removeu = emprestimos.removeIf(e -> e.getLivro().getIsbn().equals(isbn));
        if (removeu) {
            salvarDados();
        }
        return removeu;
    }

    public Optional<Emprestimo> buscarEmprestimoPorIsbn(String isbn) {
        return emprestimos.stream()
                .filter(e -> e.getLivro().getIsbn().equals(isbn))
                .findFirst();
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    public boolean existeEmprestimoPorIsbn(String isbn) {
        return emprestimos.stream()
                .anyMatch(e -> e.getLivro().getIsbn().equals(isbn) && !e.isDevolvido());
    }

    public List<Emprestimo> listarPorUsuario(String cpf) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getUsuario().getCpf().equals(cpf)) {
                resultado.add(e);
            }
        }
        return resultado;
    }
    
    public List<Emprestimo> retornaEmprestimos() {
        return new ArrayList<>(emprestimos);
    }
    
}
