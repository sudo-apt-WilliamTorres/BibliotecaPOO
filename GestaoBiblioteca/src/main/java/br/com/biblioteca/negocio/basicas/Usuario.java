package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;
import java.time.LocalDate;

public class Usuario extends Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private double multa;
    private int emprestimosAtivos;
    private boolean ativo;

    public static final int MAX_EMPRESTIMOS_ATIVOS = 5;

    public Usuario(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Telefone telefone, boolean ativo) {
        super(nome, cpf, dataNascimento, endereco, telefone);
        this.multa = 0.0;
        this.emprestimosAtivos = 0;
        this.ativo = ativo;
    }

    public boolean isAtivo() { 
    	return ativo; 
    }
    
    public void setAtivo(boolean ativo) { 
    	this.ativo = ativo; 
    }

    public boolean podeEmprestar() {
        return this.ativo && this.emprestimosAtivos < MAX_EMPRESTIMOS_ATIVOS;
    }
    
    public void adicionarEmprestimo() { 
    	if (podeEmprestar()) {
    		emprestimosAtivos++; 
    	}
    }
   
    public void removerEmprestimo() { 
    	if (emprestimosAtivos > 0) {
    		emprestimosAtivos--; 
    	}
    }

    public void adicionarMulta(double valor) { 
    	multa += valor; 
    }
    
    public void pagamentoMulta(double valor) { 
    	multa = Math.max(0, multa - valor); 
    }

    public boolean nomeIgual(String nomeUsuario) {
        if (nomeUsuario == null) {
        	return false;
        }
        
        return this.getNome().equalsIgnoreCase(nomeUsuario);
    }
    
    public void setMulta(double multa) {
        this.multa = multa;
    }
    
    public double getMulta() {
        return this.multa;
    }

    public void setEmprestimosAtivos(int emprestimosAtivos) {
        this.emprestimosAtivos = emprestimosAtivos;
    }
    
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%d", nome, cpf, dataNascimento.toString(), endereco.toCSV(), telefone.toCSV(), ativo, multa, emprestimosAtivos);
    }
    
    public static Usuario fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");

        String nome = fields[0];
        String cpf = fields[1];
        LocalDate dataNascimento = LocalDate.parse(fields[2]);
        Endereco endereco = new Endereco(fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9]);
        Telefone telefone = new Telefone(fields[10], fields[11]);
        
        boolean ativo = Boolean.parseBoolean(fields[12]);
        double multa = Double.parseDouble(fields[13]);
        int emprestimosAtivos = Integer.parseInt(fields[14]);

        Usuario usuario = new Usuario(nome, cpf, dataNascimento, endereco, telefone, ativo);
        
        usuario.setMulta(multa);
        usuario.setEmprestimosAtivos(emprestimosAtivos);

        return usuario;
    }

    @Override
    public String toString() {
        return String.format("Usuario[Nome=%s, CPF=%s, Multa=%.2f, Ativo=%b]", nome, cpf, multa, ativo);
    }
}