package br.com.biblioteca.negocio;

import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.repositorio.RepositorioFuncionario;
import java.util.List;

public class RetornarFuncionarios {

    private final RepositorioFuncionario repositorio;

    public RetornarFuncionarios(RepositorioFuncionario repositorio) {
        this.repositorio = repositorio;
    }

    public List<Funcionario> executar() {
        return repositorio.listarTodos();
    }
}
