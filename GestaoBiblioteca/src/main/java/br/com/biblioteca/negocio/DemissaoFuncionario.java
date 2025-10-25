package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionFuncionario.FuncionarioNaoEncontradoException;
import br.com.biblioteca.repositorio.RepositorioFuncionario;

public class DemissaoFuncionario {

    private final RepositorioFuncionario repositorio;

    public DemissaoFuncionario(RepositorioFuncionario repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(String cpf) throws FuncionarioNaoEncontradoException {
        if (!repositorio.existeFuncionario(cpf)) {
            throw new FuncionarioNaoEncontradoException(cpf);
        }
        repositorio.removerPorCpf(cpf);
    }
}
