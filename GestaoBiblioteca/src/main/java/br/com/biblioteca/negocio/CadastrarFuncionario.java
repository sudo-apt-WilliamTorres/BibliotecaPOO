package br.com.biblioteca.negocio;

import br.com.biblioteca.excecoes.ExceptionFuncionario.FuncionarioJaExistenteException;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.repositorio.RepositorioFuncionario;

public class CadastrarFuncionario {

    private final RepositorioFuncionario repositorio;

    public CadastrarFuncionario(RepositorioFuncionario repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(Funcionario funcionario) throws FuncionarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        if (funcionario == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Funcionário");
        }
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Nome");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("CPF");
        }
        if (funcionario.getDataNascimento() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Data de Nascimento");
        }
        if (funcionario.getEndereco() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Endereço");
        }
        if (funcionario.getTelefone() == null) {
            throw new ExceptionValidacao.CampoObrigatorioException("Telefone");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new ExceptionValidacao.CampoObrigatorioException("Cargo");
        }

        if (repositorio.buscarFuncionarioPorCpf(funcionario.getCpf()) != null) {
            throw new FuncionarioJaExistenteException(funcionario.getCpf());
        }
        
        repositorio.adicionar(funcionario);
    }
}