package br.com.biblioteca.negocio.basicas;

import br.com.biblioteca.repositorio.RepositorioLivro;
import br.com.biblioteca.repositorio.RepositorioUsuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo, int prazoDias) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(prazoDias);
        this.devolvido = false;
        this.dataDevolucao = null;
    }
    
    private Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataPrevista, LocalDate dataDevolucao, boolean devolvido) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public boolean isDevolvido() { return devolvido; }

    public String toCSV() {
        String dataDevolucaoStr = (this.dataDevolucao == null) ? "null" : this.dataDevolucao.toString();
        return String.format("%s,%s,%s,%s,%s,%b",
            this.livro.getIsbn(),
            this.usuario.getCpf(),
            this.dataEmprestimo.toString(),
            this.dataPrevistaDevolucao.toString(),
            dataDevolucaoStr,
            this.devolvido
        );
    }

    public static Emprestimo fromCSV(String csvLine, RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        String[] fields = csvLine.split(",");
        String isbn = fields[0];
        String cpf = fields[1];
        LocalDate dataEmprestimo = LocalDate.parse(fields[2]);
        LocalDate dataPrevista = LocalDate.parse(fields[3]);
        LocalDate dataDevolucao = fields[4].equals("null") ? null : LocalDate.parse(fields[4]);
        boolean devolvido = Boolean.parseBoolean(fields[5]);

        Livro livro = repoLivros.buscarPorIsbn(isbn);
        Usuario usuario = repoUsuarios.buscarPorCpf(cpf);

        if (livro == null || usuario == null) {
            return null;
        }
        
        return new Emprestimo(livro, usuario, dataEmprestimo, dataPrevista, dataDevolucao, devolvido);
    }
    
    public void setDevolvido(LocalDate dataDevolucao) {
        this.devolvido = true;
        this.dataDevolucao = dataDevolucao;
    }

    public long diasAtraso(LocalDate dataDevolucao) {
        return ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataDevolucao);
    }

    @Override
    public String toString() {
        return String.format(
            "Empréstimo[Livro=%s, Usuário=%s, Data Empréstimo=%s, Prevista Devolução=%s, Devolvido=%s]",
            livro.getTitulo(),
            usuario.getNome(),
            dataEmprestimo,
            dataPrevistaDevolucao,
            devolvido ? "Sim" : "Não"
        );
    }
}
