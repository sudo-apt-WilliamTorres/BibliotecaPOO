package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionUsuario.UsuarioNaoEncontradoException;
import br.com.biblioteca.repositorio.RepositorioUsuario;

public class ExcluirUsuario {

    private final RepositorioUsuario repositorioUsuarios;

    public ExcluirUsuario(RepositorioUsuario repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void executar(String cpf) throws UsuarioNaoEncontradoException {
        if (!repositorioUsuarios.existeUsuario(cpf)) {
            throw new UsuarioNaoEncontradoException(cpf);
        } else {
        }
        repositorioUsuarios.removerPorCpf(cpf);
    }
}
