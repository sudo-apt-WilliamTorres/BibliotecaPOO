package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionFuncionario;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.repositorio.RepositorioFuncionario;
import java.util.List;

public class ControladorFuncionario {
    
    private final RepositorioFuncionario repositorioFuncionario;
    private final LoginFuncionario loginManager;

    public ControladorFuncionario(RepositorioFuncionario repositorio) {
        this.repositorioFuncionario = repositorio;
        this.loginManager = new LoginFuncionario(this.repositorioFuncionario);
    }

    public void login(String cpf) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        loginManager.executar(cpf);
    }

    public boolean isFuncionarioLogado() {
        return loginManager.isFuncionarioLogado();
    }

    public void logout() {
        loginManager.logout();
    }

    public void cadastrar(Funcionario f) throws ExceptionFuncionario.FuncionarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        new CadastrarFuncionario(this.repositorioFuncionario).executar(f);
    }

    public void demitir(String cpf) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        new DemissaoFuncionario(this.repositorioFuncionario).executar(cpf);
    }

    public List<Funcionario> listarTodos() {
        return new RetornarFuncionarios(this.repositorioFuncionario).executar();
    }
    
    public void editar(String cpf, String novoCargo) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        new EditarFuncionario(this.repositorioFuncionario).executar(cpf, novoCargo);
    }
}