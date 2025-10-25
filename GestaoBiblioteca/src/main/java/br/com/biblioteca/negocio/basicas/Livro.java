package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Livro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    private String setor;
    private boolean disponivel;
    private List<String> palavrasChave;

    public Livro(String titulo, String autor, String isbn, LocalDate dataPublicacao, String setor, List<String> palavrasChave) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.setor = setor;
        this.disponivel = true;
        this.palavrasChave = new ArrayList<>();
        
        if (palavrasChave != null) {
            for (String p : palavrasChave) {
                this.palavrasChave.add(p.toLowerCase());
            }
        }
        
    }

    public void setPalavrasChave(List<String> palavrasChave) {
        this.palavrasChave = new ArrayList<>();
        if (palavrasChave != null) {
            for (String p : palavrasChave) {
                this.palavrasChave.add(p.toLowerCase());
            }
        }
    }
    
    public void adicionarPalavraChave(String palavra) {
        if (!palavrasChave.contains(palavra.toLowerCase())) palavrasChave.add(palavra.toLowerCase());
    }

    public boolean contemPalavraChave(String palavra) {
        return palavrasChave.contains(palavra.toLowerCase());
    }

    public String getTitulo() { 
    	return titulo; 
    }
    
    public void setTitulo(String titulo) { 
    	this.titulo = titulo; 
    }

    public String getAutor() { 
    	return autor; 
    }
    
    public void setAutor(String autor) { 
    	this.autor = autor; 
    }

    public String getIsbn() { 
    	return isbn; 
    }
    
    public void setIsbn(String isbn) { 
    	this.isbn = isbn; 
    }

    public LocalDate getDataPublicacao() { 
    	return dataPublicacao; 
    }
    
    public void setDataPublicacao(LocalDate dataPublicacao) { 
    	this.dataPublicacao = dataPublicacao; 
    }

    public String getSetor() { 
    	return setor; 
    }
    
    public void setSetor(String setor) { 
    	this.setor = setor; 
    }

    public boolean isDisponivel() { 
    	return disponivel; 
    }
    
    public void setDisponivel(boolean disponivel) { 
    	this.disponivel = disponivel; 
    }
    
    public List<String> getPalavrasChave() {
        return palavrasChave;
    }

    public String toCSV() {
        String palavrasChaveStr = String.join(";", this.palavrasChave);
        return String.format("%s,%s,%s,%s,%s,%b,%s", this.titulo, this.autor, this.isbn, this.dataPublicacao.toString(), this.setor, this.disponivel, palavrasChaveStr);
    }
    
    public static Livro fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");

        String titulo = fields[0];
        String autor = fields[1];
        String isbn = fields[2];
        LocalDate dataPublicacao = LocalDate.parse(fields[3]);
        String setor = fields[4];
        boolean disponivel = Boolean.parseBoolean(fields[5]);

        List<String> palavrasChave = new ArrayList<>();
        
        if (fields.length > 6 && !fields[6].isEmpty()) {
            palavrasChave.addAll(Arrays.asList(fields[6].split(";")));
        }

        Livro livro = new Livro(titulo, autor, isbn, dataPublicacao, setor, palavrasChave);
        livro.setDisponivel(disponivel); 

        return livro;
    }
    
    @Override
    public String toString() {
        return String.format("Livro[Título=%s, Autor=%s, ISBN=%s, Publicado=%s, Setor=%s, Disponível=%b]", titulo, autor, isbn, dataPublicacao, setor, disponivel);
    }
}