package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionEmprestimo.EmprestimoNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Emprestimo;
import br.com.biblioteca.repositorio.RepositorioEmprestimo;

public class CalcularValorMulta {

    private final RepositorioEmprestimo repositorioEmprestimos;

    public CalcularValorMulta(RepositorioEmprestimo repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public double executar(String isbn, int dias) throws EmprestimoNaoEncontradoException {
        Emprestimo emprestimo = repositorioEmprestimos.retornaEmprestimos()
                .stream()
                .filter(e -> e.getLivro().getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        if (emprestimo == null) {
            throw new EmprestimoNaoEncontradoException(isbn);
        }

        double valorPorDia = 0.5; 
        return dias * valorPorDia;
    }
}
