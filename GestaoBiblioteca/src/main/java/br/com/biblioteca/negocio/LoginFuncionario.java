package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionFuncionario.FuncionarioNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.repositorio.RepositorioFuncionario;

public class LoginFuncionario {

    private final RepositorioFuncionario repositorio;
    private Funcionario funcionarioLogado;

    public LoginFuncionario(RepositorioFuncionario repositorio) {
        this.repositorio = repositorio;
    }

    public boolean executar(String cpf) throws FuncionarioNaoEncontradoException {
        Funcionario f = repositorio.buscarFuncionarioPorCpf(cpf);
        if (f == null) {
            throw new FuncionarioNaoEncontradoException(cpf);
        }
        this.funcionarioLogado = f;
        return true;
    }

    public boolean isFuncionarioLogado() {
        return funcionarioLogado != null;
    }

    public void logout() {
        funcionarioLogado = null;
    }

    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }
}
