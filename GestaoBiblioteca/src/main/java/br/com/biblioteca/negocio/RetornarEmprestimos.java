package br.com.biblioteca.negocio;
import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.repositorio.RepositorioEmprestimo;
import java.util.List;

public class RetornarEmprestimos {

    private final RepositorioEmprestimo repositorioEmprestimos;

    public RetornarEmprestimos(RepositorioEmprestimo repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public List<Emprestimo> executar() {
        return repositorioEmprestimos.retornaEmprestimos();
    }
}
