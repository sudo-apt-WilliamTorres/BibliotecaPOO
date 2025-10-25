package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionUsuario.UsuarioJaExistenteException;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioUsuario;

public class CadastrarUsuario {

    private final RepositorioUsuario repositorioUsuarios;

    public CadastrarUsuario(RepositorioUsuario repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void executar(Usuario usuario) throws UsuarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        if (usuario == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Usuário");
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Nome");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("CPF");
        }
        if (usuario.getDataNascimento() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Data de Nascimento");
        }
        if (usuario.getEndereco() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Endereço");
        }
        if (usuario.getTelefone() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Telefone");
        }

        if (repositorioUsuarios.buscarPorCpf(usuario.getCpf()) != null) {
            throw new UsuarioJaExistenteException(usuario.getCpf());
        }

        repositorioUsuarios.adicionar(usuario);
    }
}