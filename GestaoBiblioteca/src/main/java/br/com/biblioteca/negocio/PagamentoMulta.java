package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionUsuario.UsuarioNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioUsuario;

public class PagamentoMulta {

    private final RepositorioUsuario repositorioUsuarios;

    public PagamentoMulta(RepositorioUsuario repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void executar(String cpf, double valor) throws UsuarioNaoEncontradoException {
        Usuario usuario = repositorioUsuarios.buscarPorCpf(cpf);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(cpf);
        }
        usuario.pagamentoMulta(valor);
    }
}
