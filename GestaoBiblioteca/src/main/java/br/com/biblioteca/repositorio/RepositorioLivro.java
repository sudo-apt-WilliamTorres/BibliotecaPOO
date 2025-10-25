package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Livro;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioLivro {
	
    private final List<Livro> livros;
    private static final String CSV_PATH = "livros.csv";
    private static final String DAT_PATH = "livros.dat";

    public RepositorioLivro() {
        this.livros = carregarDados();
    }
    
    public void adicionar(Livro livro) {
        livros.add(livro);
        salvarDados();
    }

    public boolean remover(String isbn) {
        boolean removeu = livros.removeIf(l -> l.getIsbn().equals(isbn));
        if (removeu) {
            salvarDados(); 
        }
        return removeu;
    }
    
    public void atualizar(Livro livroAtualizado) {
        Livro livroExistente = buscarPorIsbn(livroAtualizado.getIsbn());

        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAutor(livroAtualizado.getAutor());
        livroExistente.setSetor(livroAtualizado.getSetor());
        livroExistente.setPalavrasChave(livroAtualizado.getPalavrasChave());
        livroExistente.setDisponivel(livroAtualizado.isDisponivel());

        salvarDados();
    }
    
    public void salvarDados() {
        salvarEmFormatoNativo();
        salvarEmFormatoCsv();
    }

    private List<Livro> carregarDados() {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            List<Livro> livrosNativos = carregarDeFormatoNativo();
            if (livrosNativos != null) {
                return livrosNativos;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            return carregarDeFormatoCsv();
        }

        return new ArrayList<>();
    }
    
    private void salvarEmFormatoNativo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.livros);
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar livros em formato nativo (.dat)", e);
        }
    }

    @SuppressWarnings("unchecked")
	private List<Livro> carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (List<Livro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar livros do formato nativo (.dat)", e);
        }
    }
    
    private void salvarEmFormatoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("titulo,autor,isbn,dataPublicacao,setor,disponivel,palavrasChave");
            bw.newLine();
            for (Livro livro : this.livros) {
                bw.write(livro.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar livros em formato CSV", e);
        }
    }

    private List<Livro> carregarDeFormatoCsv() {
        List<Livro> livrosCarregados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                livrosCarregados.add(Livro.fromCSV(line));
            }
        } catch (Exception e) {
        	throw new RuntimeException("Falha ao carregar livros do formato CSV. O arquivo pode estar corrompido.", e);
        }
        return livrosCarregados;
    }
    
    public Livro buscarPorIsbn(String isbn) {
        return livros.stream()
                     .filter(l -> l.getIsbn().equals(isbn))
                     .findFirst()
                     .orElse(null);
    }
    
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }
    
    public List<Livro> buscarPorPalavraChave(String palavra) {
        return livros.stream()
            .filter(l -> l.getPalavrasChave().contains(palavra))
            .collect(Collectors.toList());
    }
    
    public List<Livro> buscarPorSetor(String setor) {
        return livros.stream()
            .filter(l -> l.getSetor().equalsIgnoreCase(setor))
            .collect(Collectors.toList());
    }

    public boolean existeLivro(String isbn) {
        return buscarPorIsbn(isbn) != null;
    }
    
}
