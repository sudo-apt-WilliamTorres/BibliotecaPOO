package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;
import java.time.LocalDate;

public class Funcionario extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
    private String cargo;

    public Funcionario(String nome, String cpf, LocalDate dataNascimento, Endereco endereco, Telefone telefone, String cargo) {
        super(nome, cpf, dataNascimento, endereco, telefone);
        this.cargo = cargo;
    }

    public String getCargo() { 
    	return cargo; 
    }
    
    public void setCargo(String cargo) { 
    	this.cargo = cargo;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s",
            nome,
            cpf,
            dataNascimento.toString(),
            endereco.toCSV(),
            telefone.toCSV(), 
            cargo);
    }

    public static Funcionario fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");

        String nome = fields[0];
        String cpf = fields[1];
        LocalDate dataNascimento = LocalDate.parse(fields[2]);

        Endereco endereco = new Endereco(
            fields[3], fields[4], fields[5], fields[6],
            fields[7], fields[8], fields[9]
        );

        Telefone telefone = new Telefone(fields[10], fields[11]);

        String cargo = fields[12];

        return new Funcionario(nome, cpf, dataNascimento, endereco, telefone, cargo);
    }
    
    @Override
    public String toString() {
        return "Funcionario{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
