package br.com.biblioteca.negocio.basicas;

import java.io.Serializable;

public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String logradouro, String numero, String complemento,
                    String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    @Override
    public String toString() {
        return String.format("%s, %s %s - %s, %s/%s - CEP: %s",
                logradouro, numero,
                (complemento != null && !complemento.isEmpty()) ? complemento : "",
                bairro, cidade, estado, cep);
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                logradouro, numero, complemento, bairro, cidade, estado, cep);
    }
}