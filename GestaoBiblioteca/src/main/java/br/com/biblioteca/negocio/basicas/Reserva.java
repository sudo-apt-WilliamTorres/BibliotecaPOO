package br.com.biblioteca.negocio.basicas;

import br.com.biblioteca.repositorio.RepositorioLivro;
import br.com.biblioteca.repositorio.RepositorioUsuario;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataReserva;
    private LocalDate dataLimite;
    private boolean ativa;

    public Reserva(Livro livro, Usuario usuario, LocalDate dataReserva, LocalDate dataLimite) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataReserva = dataReserva;
        this.dataLimite = dataLimite;
        this.ativa = true;
    }

    public Livro getLivro() { 
    	return livro; 
    }
    
    public void setLivro(Livro livro) { 
    	this.livro = livro; 
    }

    public Usuario getUsuario() { 
    	return usuario; 
    }
    
    public void setUsuario(Usuario usuario) { 
    	this.usuario = usuario; 
    }

    public LocalDate getDataReserva() { 
    	return dataReserva; 
    }
    
    public void setDataReserva(LocalDate dataReserva) { 
    	this.dataReserva = dataReserva; 
    }

    public LocalDate getDataLimite() { 
    	return dataLimite; 
    }
    
    public void setDataLimite(LocalDate dataLimite) { 
    	this.dataLimite = dataLimite; 
    }

    public boolean isAtiva() { 
    	return ativa; 
    }
    
    public void setAtiva(boolean ativa) { 
    	this.ativa = ativa; 
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s,%b",
            this.livro.getIsbn(),
            this.usuario.getCpf(),
            this.dataReserva.toString(),
            this.dataLimite.toString(),
            this.ativa);
    }

    public static Reserva fromCSV(String csvLine, RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        String[] fields = csvLine.split(",");

        String isbn = fields[0];
        String cpf = fields[1];
        LocalDate dataReserva = LocalDate.parse(fields[2]);
        LocalDate dataLimite = LocalDate.parse(fields[3]);
        boolean ativa = Boolean.parseBoolean(fields[4]);

        Livro livro = repoLivros.buscarPorIsbn(isbn);
        Usuario usuario = repoUsuarios.buscarPorCpf(cpf);

        if (livro == null || usuario == null) {
            return null;
        }

        Reserva reserva = new Reserva(livro, usuario, dataReserva, dataLimite);
        reserva.setAtiva(ativa);
        return reserva;
    }

    @Override
    public String toString() {
        return String.format("Reserva[Livro=%s, Usuario=%s, DataReserva=%s, DataLimite=%s, Ativa=%s]", livro.getTitulo(), usuario.getNome(), dataReserva, dataLimite, ativa ? "Sim" : "Não");
    }
}
