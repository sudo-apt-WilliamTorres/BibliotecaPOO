package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionFuncionario.FuncionarioNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.repositorio.RepositorioFuncionario;

public class EditarFuncionario {

    private final RepositorioFuncionario repositorioFuncionario;

    public EditarFuncionario(RepositorioFuncionario repositorio) {
        this.repositorioFuncionario = repositorio;
    }

    public void executar(String cpf, String novoCargo) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = this.repositorioFuncionario.buscarFuncionarioPorCpf(cpf);

        if (funcionario == null) {
            throw new FuncionarioNaoEncontradoException(cpf);
        }

        funcionario.setCargo(novoCargo);

        this.repositorioFuncionario.atualizar(funcionario);
    }
}