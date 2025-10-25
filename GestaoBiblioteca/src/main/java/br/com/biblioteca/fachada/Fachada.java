package br.com.biblioteca.fachada;

import br.com.biblioteca.excecoes.*;
import br.com.biblioteca.negocio.basicas.*;
import br.com.biblioteca.negocio.*;
import br.com.biblioteca.repositorio.*;
import java.io.IOException;
import java.util.List;

public class Fachada {

    private static Fachada instancia;
    private final ControladorFuncionario controladorFuncionario;
    private final ControladorLivro controladorLivro;
    private final ControladorUsuario controladorUsuario;
    private final ControladorEmprestimo controladorEmprestimo;
    private final ControladorReserva controladorReserva;
    private final ControladorCaixa controladorCaixa;

    private Fachada() {
        RepositorioFuncionario repositorioFuncionario = new RepositorioFuncionario();
        RepositorioLivro repositorioLivro = new RepositorioLivro();
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        RepositorioReserva repositorioReserva = new RepositorioReserva(repositorioLivro, repositorioUsuario);
        RepositorioCaixa repositorioCaixa = new RepositorioCaixa();

        this.controladorFuncionario = new ControladorFuncionario(repositorioFuncionario);
        this.controladorUsuario = new ControladorUsuario(repositorioUsuario);
        this.controladorReserva = new ControladorReserva(repositorioReserva, repositorioLivro, repositorioUsuario);
        this.controladorEmprestimo = new ControladorEmprestimo(repositorioLivro, repositorioUsuario, repositorioReserva);
        this.controladorCaixa = new ControladorCaixa(repositorioCaixa);
        this.controladorLivro = new ControladorLivro(repositorioLivro);
    }

    public static Fachada getInstance() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    public void loginFuncionario(String cpf) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        this.controladorFuncionario.login(cpf);
    }

    public boolean isFuncionarioLogado() {
        return this.controladorFuncionario.isFuncionarioLogado();
    }

    public void logoutFuncionario() {
        this.controladorFuncionario.logout();
    }

    public void cadastrarFuncionario(Funcionario f) throws ExceptionFuncionario.FuncionarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        this.controladorFuncionario.cadastrar(f);
    }
    
    public void editarFuncionario(String cpf, String novoCargo) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        this.controladorFuncionario.editar(cpf, novoCargo);
    }

    public void demissaoFuncionario(String cpf) throws ExceptionFuncionario.FuncionarioNaoEncontradoException {
        this.controladorFuncionario.demitir(cpf);
    }

    public List<Funcionario> retornarFuncionarios() {
        return this.controladorFuncionario.listarTodos();
    }

    public void cadastrarLivro(Livro livro) throws ExceptionLivro.LivroJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        this.controladorLivro.cadastrar(livro);
    }
    

    public void excluirLivro(String isbn) throws ExceptionLivro.LivroNaoEncontradoException {
        this.controladorLivro.excluir(isbn);
    }

    public void editarLivro(String isbn, String novoTitulo) throws ExceptionLivro.LivroNaoEncontradoException {
        this.controladorLivro.editar(isbn, novoTitulo);
    }
    
    public List<Livro> retornarLivros() {
        return this.controladorLivro.listarTodos();
    }

    public List<Livro> buscarLivrosPorPalavraChave(String palavra) {
        return this.controladorLivro.buscarPorPalavraChave(palavra);
    }
    
    public List<Livro> buscarLivrosPorSetor(String setor) {
        return this.controladorLivro.buscarPorSetor(setor);
    }

    public Livro buscarLivroPorIsbn(String isbn) {
        return this.controladorLivro.buscarPorIsbn(isbn);
    }

    public void cadastrarUsuario(Usuario u) throws ExceptionUsuario.UsuarioJaExistenteException, ExceptionValidacao.CampoObrigatorioException {
        this.controladorUsuario.cadastrar(u);
    }
    
    public void editarUsuario(String cpf, String novoNome, Endereco novoEndereco, Telefone novoTelefone) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        this.controladorUsuario.editar(cpf, novoNome, novoEndereco, novoTelefone);
    }

    public void excluirUsuario(String cpf) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        this.controladorUsuario.excluir(cpf);
    }

    public void pagamentoMulta(String cpf, double valor) throws ExceptionUsuario.UsuarioNaoEncontradoException {
        this.controladorUsuario.pagarMulta(cpf, valor);
    }
    
    public List<Usuario> retornarUsuarios() {
        return this.controladorUsuario.listarTodos();
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        return this.controladorUsuario.buscarPorCpf(cpf);
    }

    public void criarEmprestimo(Emprestimo emprestimo) throws ExceptionLivro.LivroNaoEncontradoException,
            ExceptionUsuario.UsuarioNaoEncontradoException, ExceptionEmprestimo.LivroIndisponivelException,
            ExceptionUsuario.LimiteEmprestimosException {
        this.controladorEmprestimo.criar(emprestimo);
    }

    public void registrarDevolucao(String isbn) throws ExceptionEmprestimo.EmprestimoNaoEncontradoException {
        this.controladorEmprestimo.devolver(isbn);
    }

    public List<Emprestimo> retornarEmprestimos() {
        return this.controladorEmprestimo.listarTodos();
    }
    
    public void criarReserva(Livro livro, Usuario usuario, int prazoDias) throws ExceptionUsuario.ReservaLimiteException, ExceptionReserva.ReservaJaExistenteException {
        this.controladorReserva.criar(livro, usuario, prazoDias);
    }

    public void cancelarReserva(String cpfUsuario, String isbnLivro) throws ExceptionReserva.ReservaNaoEncontradaException {
        this.controladorReserva.cancelar(cpfUsuario, isbnLivro);
    }
    
    public List<Reserva> buscarReservasPorUsuario(String cpfUsuario) {
        return this.controladorReserva.buscarPorUsuario(cpfUsuario);
    }

    public void abrirCaixa() throws ExceptionCaixa.CaixaJaAbertoException, IOException {
        this.controladorCaixa.abrir();
    }

    public void fecharCaixa() throws ExceptionCaixa.CaixaNaoAbertoException, IOException {
        this.controladorCaixa.fechar();
    }

    public void registrarMovimento(double valor) throws ExceptionCaixa.CaixaNaoAbertoException, IOException {
        this.controladorCaixa.registrarMovimento(valor);
    }
    
    public List<String> retornarHistoricoCaixa() {
        return this.controladorCaixa.getHistorico();
    }
}