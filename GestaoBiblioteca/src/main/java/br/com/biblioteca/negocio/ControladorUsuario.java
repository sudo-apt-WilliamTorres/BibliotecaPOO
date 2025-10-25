package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionUsuario;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Endereco;
import br.com.biblioteca.negocio.basicas.Telefone; 
import br.com.biblioteca.negocio.basicas.Usuario;
import br.com.biblioteca.repositorio.RepositorioUsuario;
import java.util.List;

public class ControladorUsuario {

    private final RepositorioUsuario repositorioUsuario;

    public ControladorUsuario(RepositorioUsuario repositorio) {
        this.repositorioUsuario = repositorio;
    }

    public void cadastrar(Usuario u) throws ExceptionUsuario.UsuarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        new CadastrarUsuario(this.repositorioUsuario).executar(u);
    }

    public void excluir(String cpf) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        new ExcluirUsuario(this.repositorioUsuario).executar(cpf);
    }

    public void pagarMulta(String cpf, double valor) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        new PagamentoMulta(this.repositorioUsuario).executar(cpf, valor);
    }

    public List<Usuario> listarTodos() {
        return new RetornarUsuarios(this.repositorioUsuario).executar();
    }
    
    public Usuario buscarPorCpf(String cpf) {
        return this.repositorioUsuario.buscarPorCpf(cpf);
    }
    
    public void editar(String cpf, String novoNome, Endereco novoEndereco, Telefone novoTelefone) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        new EditarUsuario(this.repositorioUsuario).executar(cpf, novoNome, novoEndereco, novoTelefone);
    }
}