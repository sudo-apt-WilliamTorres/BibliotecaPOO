package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionUsuario.UsuarioNaoEncontradoException;
import br.com.biblioteca.negocio.basicas.Endereco;
import br.com.biblioteca.negocio.basicas.Telefone;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioUsuario;

public class EditarUsuario {

    private final RepositorioUsuario repositorioUsuario;

    public EditarUsuario(RepositorioUsuario repositorio) {
        this.repositorioUsuario = repositorio;
    }

    public void executar(String cpf, String novoNome, Endereco novoEndereco, Telefone novoTelefone) throws UsuarioNaoEncontradoException {
        Usuario usuario = this.repositorioUsuario.buscarPorCpf(cpf);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(cpf);
        }

        usuario.setNome(novoNome);
        usuario.setEndereco(novoEndereco);
        usuario.setTelefone(novoTelefone);

        this.repositorioUsuario.atualizar(usuario);
    }
}