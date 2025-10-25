package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nome;
    protected String cpf;
    protected LocalDate dataNascimento;
    protected Endereco endereco;
    protected Telefone telefone;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Telefone telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getNome() { 
    	return nome; 
    }
    
    public void setNome(String nome) { 
    	this.nome = nome; 
    }

    public String getCpf() { 
    	return cpf; 
    }
    
    public void setCpf(String cpf) { 
    	this.cpf = cpf; 
    }

    public LocalDate getDataNascimento() { 
    	return dataNascimento; 
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() { 
    	return endereco; 
    }
    
    public void setEndereco(Endereco endereco) {
    	this.endereco = endereco; 
    }

    public Telefone getTelefone() { 
    	return telefone; 
    }
    
    public void setTelefone(Telefone telefone) { 
    	this.telefone = telefone; 
    }

    public int getIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    public abstract String toCSV();

    @Override
    public String toString() {
        return String.format("%s (CPF: %s, Nascimento: %s)",
            nome, cpf, dataNascimento);
    }
}