package br.com.biblioteca.ui;

import br.com.biblioteca.excecoes.ExceptionCaixa;
import br.com.biblioteca.excecoes.ExceptionEmprestimo;
import br.com.biblioteca.excecoes.ExceptionFuncionario;
import br.com.biblioteca.excecoes.ExceptionLivro;
import br.com.biblioteca.excecoes.ExceptionReserva;
import br.com.biblioteca.excecoes.ExceptionUsuario;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.*;
import br.com.biblioteca.fachada.Fachada;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {

    private static final Fachada fachada = Fachada.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void opcao() {
        while (true) {
            System.out.println("\n=== Sistema da Biblioteca ===");

            if (fachada.isFuncionarioLogado()) {
                System.out.println("--- MENU DO FUNCIONÁRIO ---");
                
                System.out.println("\n--- Gerenciamento de Livros ---");
                System.out.println("1 - Cadastrar Livro");
                System.out.println("2 - Editar Livro");
                System.out.println("3 - Excluir Livro");
                System.out.println("4 - Listar Todos os Livros");

                System.out.println("\n--- Gerenciamento de Usuários ---");
                System.out.println("5 - Cadastrar Usuário");
                System.out.println("6 - Editar Usuário");
                System.out.println("7 - Excluir Usuário");
                System.out.println("8 - Listar Todos os Usuários");

                System.out.println("\n--- Gerenciamento de Funcionários ---");
                System.out.println("9 - Cadastrar Funcionário");
                System.out.println("10 - Editar Funcionário");
                System.out.println("11 - Demitir Funcionário");
                System.out.println("12 - Listar Todos os Funcionários");
                
                System.out.println("\n--- Operações ---");
                System.out.println("13 - Listar Todos os Empréstimos");

                System.out.println("\n--- Caixa ---");
                System.out.println("14 - Abrir Caixa");
                System.out.println("15 - Fechar Caixa");
                System.out.println("16 - Registrar Movimento");
                System.out.println("17 - Ver Histórico do Caixa");

                System.out.println("\n--- Sistema ---");
                System.out.println("0 - Logout");
                System.out.print("Escolha uma opção: ");
                
                try {
                    int opc = sc.nextInt();
                    sc.nextLine();

                    switch (opc) {
                        case 1 -> cadastrarLivro();
                        case 2 -> editarLivro();
                        case 3 -> excluirLivro();
                        case 4 -> consultarLivros();
                        case 5 -> cadastrarUsuario();
                        case 6 -> editarUsuario();
                        case 7 -> excluirUsuario();
                        case 8 -> listarUsuarios();
                        case 9 -> cadastrarFuncionario();
                        case 10 -> editarFuncionario();
                        case 11 -> demitirFuncionario();
                        case 12 -> listarFuncionarios();
                        case 13 -> listarEmprestimos();
                        case 14 -> abrirCaixa();
                        case 15 -> fecharCaixa();
                        case 16 -> registrarMovimentoCaixa();
                        case 17 -> mostrarHistoricoCaixa();
                        case 0 -> {
                            fachada.logoutFuncionario();
                            System.out.println("Logout realizado.");
                        }
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Erro: Por favor, digite um número válido.");
                    sc.nextLine();
                }

            } else {
                System.out.println("--- BEM-VINDO ---");
                System.out.println("\n--- Consulta de Livros ---");
                System.out.println("1 - Listar Todos os Livros");
                System.out.println("2 - Buscar por Palavra-Chave");
                System.out.println("3 - Buscar por Setor");
                System.out.println("\n--- Operações de Usuário ---");
                System.out.println("4 - Fazer Empréstimo");
                System.out.println("5 - Devolver Livro");
                System.out.println("6 - Pagar Multa");
                System.out.println("7 - Criar Reserva");
                System.out.println("8 - Cancelar Reserva");
                System.out.println("9 - Minhas Reservas");
                System.out.println("\n--- Sistema ---");
                System.out.println("10 - Login (Funcionário)");
                System.out.println("0 - Sair do Sistema");
                System.out.print("Escolha uma opção: ");

                try {
                    int opc = sc.nextInt();
                    sc.nextLine();

                    switch (opc) {
                        case 1 -> consultarLivros();
                        case 2 -> buscarLivrosPalavraChave();
                        case 3 -> buscarLivrosSetor();
                        case 4 -> fazerEmprestimo();
                        case 5 -> devolverLivro();
                        case 6 -> pagarMulta();
                        case 7 -> criarReserva();
                        case 8 -> cancelarReserva();
                        case 9 -> listarReservasUsuario();
                        case 10 -> loginFuncionario();
                        case 0 -> {
                            System.out.println("Saindo...");
                            return;
                        }
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Erro: Por favor, digite um número válido.");
                    sc.nextLine();
                }
            }
        }
    }
    
    private static void abrirCaixa() {
        try {
            fachada.abrirCaixa();
            System.out.println("Caixa aberto com sucesso!");
        } catch (ExceptionCaixa.CaixaJaAbertoException | IOException e) {
            System.err.println("Erro ao abrir o caixa: " + e.getMessage());
        }
    }

    private static void fecharCaixa() {
        try {
            fachada.fecharCaixa();
            System.out.println("Caixa fechado com sucesso!");
        } catch (ExceptionCaixa.CaixaNaoAbertoException | IOException e) {
            System.err.println("Erro ao fechar o caixa: " + e.getMessage());
        }
    }

    private static void registrarMovimentoCaixa() {
        try {
            System.out.print("Digite o valor do movimento: ");
            double valor = sc.nextDouble();
            sc.nextLine();
            fachada.registrarMovimento(valor);
            System.out.println("Movimento registrado com sucesso.");
        } catch (InputMismatchException e) {
            System.err.println("Erro: Valor inválido.");
            sc.nextLine();
        } catch (ExceptionCaixa.CaixaNaoAbertoException | IOException e) {
            System.err.println("Erro ao registrar movimento: " + e.getMessage());
        }
    }

    private static void mostrarHistoricoCaixa() {
        List<String> historico = fachada.retornarHistoricoCaixa();
        System.out.println("=== Histórico do Caixa ===");
        if (historico == null || historico.isEmpty()) {
            System.out.println("Nenhum movimento registrado.");
        } else {
            historico.forEach(System.out::println);
        }
    }

    private static void loginFuncionario() {
        try {
            System.out.print("Digite CPF do funcionário: ");
            String cpf = sc.nextLine();
            fachada.loginFuncionario(cpf);
            System.out.println("Login realizado com sucesso!");
        } catch (ExceptionFuncionario.FuncionarioNaoEncontradoException e) {
            System.err.println("Erro de login: " + e.getMessage());
        }
    }
    
    private static void cadastrarLivro() {
        try {
            System.out.print("Título: ");
            String titulo = sc.nextLine();
            System.out.print("Autor: ");
            String autor = sc.nextLine();
            System.out.print("ISBN: ");
            String isbn = sc.nextLine();
            System.out.print("Setor: ");
            String setor = sc.nextLine();
            System.out.print("Quantas palavras-chave deseja adicionar? ");
            int n = sc.nextInt();
            sc.nextLine();
            List<String> palavrasChave = new java.util.ArrayList<>();
            for (int i = 0; i < n; i++) {
                System.out.print("Palavra-chave " + (i + 1) + ": ");
                palavrasChave.add(sc.nextLine());
            }
            Livro livro = new Livro(titulo, autor, isbn, LocalDate.now(), setor, palavrasChave);
            fachada.cadastrarLivro(livro);
            System.out.println("Livro cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para número de palavras-chave.");
            sc.nextLine();
        } catch (ExceptionValidacao.CampoObrigatorioException e) {
            System.err.println("Erro de Validação: " + e.getMessage());
        } catch (ExceptionLivro.LivroJaExistenteException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    private static void consultarLivros() {
        System.out.println("=== Lista de Livros ===");
        List<Livro> livros = fachada.retornarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(l ->
                System.out.println("Título: " + l.getTitulo() + ", Autor: " + l.getAutor() + ", ISBN: " + l.getIsbn() + 
                                   ", Setor: " + l.getSetor() + ", Disponível: " + l.isDisponivel())
            );
        }
    }
    
    private static void editarLivro() {
        try {
            System.out.print("ISBN do livro a editar: ");
            String isbn = sc.nextLine();
            System.out.print("Novo título: ");
            String novoTitulo = sc.nextLine();
            fachada.editarLivro(isbn, novoTitulo);
            System.out.println("Livro editado com sucesso!");
        } catch (ExceptionLivro.LivroNaoEncontradoException e) {
            System.err.println("Erro ao editar livro: " + e.getMessage());
        }
    }
    
    private static void excluirLivro() {
        try {
            System.out.print("ISBN do livro a excluir: ");
            String isbn = sc.nextLine();
            fachada.excluirLivro(isbn);
            System.out.println("Livro excluído com sucesso.");
        } catch (ExceptionLivro.LivroNaoEncontradoException e) {
            System.err.println("Erro ao excluir livro: " + e.getMessage());
        }
    }
    
    private static void buscarLivrosPalavraChave() {
        System.out.print("Palavra-chave: ");
        String palavra = sc.nextLine();
        List<Livro> livros = fachada.buscarLivrosPorPalavraChave(palavra);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado com esta palavra-chave.");
        } else {
            livros.forEach(l -> System.out.println(l.getTitulo()));
        }
    }

    private static void buscarLivrosSetor() {
        System.out.print("Setor: ");
        String setor = sc.nextLine();
        List<Livro> livros = fachada.buscarLivrosPorSetor(setor);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado neste setor.");
        } else {
            livros.forEach(l -> System.out.println(l.getTitulo()));
        }
    }
    
    private static void cadastrarUsuario() {
        try {
            System.out.println("\n--- Cadastro de Novo Usuário ---");
            System.out.print("Nome do usuário: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Data de nascimento (aaaa-mm-dd): ");
            LocalDate dataNascimento = LocalDate.parse(sc.nextLine());
            System.out.println("--- Endereço ---");
            System.out.print("Logradouro: ");
            String logradouro = sc.nextLine();
            System.out.print("Número: ");
            String numero = sc.nextLine();
            System.out.print("Complemento: ");
            String complemento = sc.nextLine();
            System.out.print("Bairro: ");
            String bairro = sc.nextLine();
            System.out.print("Cidade: ");
            String cidade = sc.nextLine();
            System.out.print("Estado: ");
            String estado = sc.nextLine();
            System.out.print("CEP: ");
            String cep = sc.nextLine();
            Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
            System.out.println("--- Telefone ---");
            System.out.print("DDD: ");
            String ddd = sc.nextLine();
            System.out.print("Número: ");
            String numeroTel = sc.nextLine();
            Telefone telefone = new Telefone(ddd, numeroTel);
            Usuario usuario = new Usuario(nome, cpf, dataNascimento, endereco, telefone, true);
            fachada.cadastrarUsuario(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (DateTimeParseException e) {
            System.err.println("Erro de Formato: Data inválida. Use aaaa-mm-dd.");
        } catch (ExceptionValidacao.CampoObrigatorioException e) {
            System.err.println("Erro de Validação: " + e.getMessage());
        } catch (ExceptionUsuario.UsuarioJaExistenteException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    private static void cadastrarFuncionario() {
        try {
            System.out.println("\n--- Cadastro de Novo Funcionário ---");
            System.out.print("Nome do funcionário: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Data de nascimento (aaaa-mm-dd): ");
            LocalDate dataNascimento = LocalDate.parse(sc.nextLine());
            System.out.println("--- Endereço ---");
            System.out.print("Logradouro: ");
            String logradouro = sc.nextLine();
            System.out.print("Número: ");
            String numero = sc.nextLine();
            System.out.print("Complemento: ");
            String complemento = sc.nextLine();
            System.out.print("Bairro: ");
            String bairro = sc.nextLine();
            System.out.print("Cidade: ");
            String cidade = sc.nextLine();
            System.out.print("Estado: ");
            String estado = sc.nextLine();
            System.out.print("CEP: ");
            String cep = sc.nextLine();
            Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
            System.out.println("--- Telefone ---");
            System.out.print("DDD: ");
            String ddd = sc.nextLine();
            System.out.print("Número: ");
            String numeroTel = sc.nextLine();
            Telefone telefone = new Telefone(ddd, numeroTel);
            System.out.print("Cargo: ");
            String cargo = sc.nextLine();
            Funcionario f = new Funcionario(nome, cpf, dataNascimento, endereco, telefone, cargo);
            fachada.cadastrarFuncionario(f);
            System.out.println("Funcionário cadastrado com sucesso!");
        } catch (DateTimeParseException e) {
            System.err.println("Erro de Formato: Data inválida. Use aaaa-mm-dd.");
        } catch (ExceptionValidacao.CampoObrigatorioException e) {
            System.err.println("Erro de Validação: " + e.getMessage());
        } catch (ExceptionFuncionario.FuncionarioJaExistenteException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void excluirUsuario() {
        try {
            System.out.print("CPF do usuário a excluir: ");
            String cpf = sc.nextLine();
            fachada.excluirUsuario(cpf);
            System.out.println("Usuário excluído com sucesso.");
        } catch (ExceptionUsuario.UsuarioNaoEncontradoException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }
    
    private static void demitirFuncionario() {
        try {
            System.out.print("CPF do funcionário a demitir: ");
            String cpf = sc.nextLine();
            fachada.demissaoFuncionario(cpf);
            System.out.println("Funcionário removido com sucesso.");
        } catch (ExceptionFuncionario.FuncionarioNaoEncontradoException e) {
            System.err.println("Erro ao demitir funcionário: " + e.getMessage());
        }
    }
    
    private static void fazerEmprestimo() {
        try {
            System.out.print("CPF do usuário: ");
            String cpf = sc.nextLine();
            System.out.print("ISBN do livro: ");
            String isbn = sc.nextLine();

            Usuario u = fachada.buscarUsuarioPorCpf(cpf);
            Livro l = fachada.buscarLivroPorIsbn(isbn);
            
            if (u == null) throw new Exception("Usuário não encontrado.");
            if (l == null) throw new Exception("Livro não encontrado.");

            fachada.criarEmprestimo(new Emprestimo(l, u, LocalDate.now(), 7));
            System.out.println("Empréstimo realizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    private static void devolverLivro() {
        try {
            System.out.print("ISBN do livro a devolver: ");
            String isbn = sc.nextLine();
            fachada.registrarDevolucao(isbn);
            System.out.println("Devolução registrada com sucesso!");
        } catch (ExceptionEmprestimo.EmprestimoNaoEncontradoException e) {
            System.err.println("Erro ao devolver livro: " + e.getMessage());
        }
    }

    private static void pagarMulta() {
        try {
            System.out.print("CPF do usuário: ");
            String cpf = sc.nextLine();
            System.out.print("Valor a pagar: ");
            double valor = sc.nextDouble();
            sc.nextLine();
            fachada.pagamentoMulta(cpf, valor);
            System.out.println("Pagamento registrado!");
        } catch (InputMismatchException e) {
            System.err.println("Erro: Valor inválido.");
            sc.nextLine();
        } catch (ExceptionUsuario.UsuarioNaoEncontradoException e) {
            System.err.println("Erro no pagamento: " + e.getMessage());
        }
    }

    private static void criarReserva() {
        try {
            System.out.print("CPF do usuário: ");
            String cpf = sc.nextLine();
            System.out.print("ISBN do livro: ");
            String isbn = sc.nextLine();
            System.out.print("Prazo para retirada (dias): ");
            int prazo = sc.nextInt();
            sc.nextLine();

            Usuario u = fachada.buscarUsuarioPorCpf(cpf);
            Livro l = fachada.buscarLivroPorIsbn(isbn);

            if (u == null) throw new Exception("Usuário não encontrado.");
            if (l == null) throw new Exception("Livro não encontrado.");

            fachada.criarReserva(l, u, prazo);
            System.out.println("Reserva criada com sucesso!");
        } catch (InputMismatchException e) {
            System.err.println("Erro: Prazo em dias deve ser um número.");
            sc.nextLine();
        } catch (Exception e) {
            System.err.println("Erro ao criar reserva: " + e.getMessage());
        }
    }
    
    private static void cancelarReserva() {
        try {
            System.out.print("CPF do usuário: ");
            String cpf = sc.nextLine();
            System.out.print("ISBN do livro: ");
            String isbn = sc.nextLine();
            fachada.cancelarReserva(cpf, isbn);
            System.out.println("Reserva cancelada com sucesso.");
        } catch (ExceptionReserva.ReservaNaoEncontradaException e) {
            System.err.println("Erro ao cancelar reserva: " + e.getMessage());
        }
    }

    private static void listarReservasUsuario() {
        System.out.print("CPF do usuário: ");
        String cpf = sc.nextLine();

        List<Reserva> reservas = fachada.buscarReservasPorUsuario(cpf);
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva ativa para este usuário.");
        } else {
            reservas.forEach(r ->
                System.out.println("Livro: " + r.getLivro().getTitulo() +
                                   ", Limite retirada: " + r.getDataLimite())
            );
        }
    }
    
    private static void listarUsuarios() {
        System.out.println("\n--- Lista de Usuários ---");
        List<Usuario> usuarios = fachada.retornarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("Nome: " + u.getNome() + " | CPF: " + u.getCpf() + " | Multa: R$" + u.getMulta());
            }
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        List<Funcionario> funcionarios = fachada.retornarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + " | Cargo: " + f.getCargo());
            }
        }
    }

    private static void listarEmprestimos() {
        System.out.println("\n--- Lista de Empréstimos Ativos ---");
        List<Emprestimo> emprestimos = fachada.retornarEmprestimos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo ativo no momento.");
        } else {
            for (Emprestimo e : emprestimos) {
                if (!e.isDevolvido()) {
                    System.out.println("Livro: " + e.getLivro().getTitulo() + " | Usuário: " + e.getUsuario().getNome() + 
                                       " | Devolução Prevista: " + e.getPrevistaDevolucao());
                }
            }
        }
    }
    
    private static void editarUsuario() {
        try {
            System.out.print("Digite o CPF do usuário que deseja editar: ");
            String cpf = sc.nextLine();

            Usuario usuarioAtual = fachada.buscarUsuarioPorCpf(cpf);
            if(usuarioAtual == null){
                System.out.println("Usuário não encontrado.");
                return;
            }
            System.out.println("Editando usuário: " + usuarioAtual.getNome());

            System.out.print("Digite o novo nome: ");
            String novoNome = sc.nextLine();

            System.out.println("Digite o novo endereço:");
            System.out.print("Logradouro: ");
            String logradouro = sc.nextLine();
            System.out.print("Número: ");
            String numero = sc.nextLine();
            System.out.print("Complemento: ");
            String complemento = sc.nextLine();
            System.out.print("Bairro: ");
            String bairro = sc.nextLine();
            System.out.print("Cidade: ");
            String cidade = sc.nextLine();
            System.out.print("Estado: ");
            String estado = sc.nextLine();
            System.out.print("CEP: ");
            String cep = sc.nextLine();
            Endereco novoEndereco = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);

            System.out.println("Digite o novo telefone:");
            System.out.print("DDD: ");
            String ddd = sc.nextLine();
            System.out.print("Número: ");
            String numeroTel = sc.nextLine();
            Telefone novoTelefone = new Telefone(ddd, numeroTel);
            
            fachada.editarUsuario(cpf, novoNome, novoEndereco, novoTelefone);
            System.out.println("Usuário atualizado com sucesso!");

        } catch (ExceptionUsuario.UsuarioNaoEncontradoException e) {
            System.err.println("Erro ao editar usuário: " + e.getMessage());
        }
    }

    private static void editarFuncionario() {
        try {
            System.out.print("Digite o CPF do funcionário que deseja editar: ");
            String cpf = sc.nextLine();
            System.out.print("Digite o novo cargo: ");
            String novoCargo = sc.nextLine();
            
            fachada.editarFuncionario(cpf, novoCargo);
            System.out.println("Funcionário atualizado com sucesso!");

        } catch (ExceptionFuncionario.FuncionarioNaoEncontradoException e) {
            System.err.println("Erro ao editar funcionário: " + e.getMessage());
        }
    }
}