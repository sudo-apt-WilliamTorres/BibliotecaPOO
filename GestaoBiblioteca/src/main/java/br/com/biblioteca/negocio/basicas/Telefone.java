package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;

public class Telefone implements Serializable {
	private static final long serialVersionUID = 1L;
    private String ddd;
    private String numero;

    public Telefone(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() { 
    	return ddd; 
    }
    
    public void setDdd(String ddd) { 
    	this.ddd = ddd; 
    }

    public String getNumero() { 
    	return numero; 
    }
    
    public void setNumero(String numero) { 
    	this.numero = numero; 
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", ddd, numero);
    }

    public String toCSV() {
        return String.format("%s,%s", ddd, numero);
    }
}
