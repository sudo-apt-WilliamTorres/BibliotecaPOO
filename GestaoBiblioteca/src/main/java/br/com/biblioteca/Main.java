package br.com.biblioteca;

import br.com.biblioteca.excecoes.ExceptionFuncionario;
import br.com.biblioteca.excecoes.ExceptionValidacao;
import br.com.biblioteca.negocio.basicas.Telefone;
import br.com.biblioteca.negocio.basicas.Endereco;
import br.com.biblioteca.negocio.basicas.Funcionario;
import br.com.biblioteca.ui.UI;
import br.com.biblioteca.fachada.Fachada;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Fachada fachada = Fachada.getInstance();

        try {
            if (fachada.retornarFuncionarios().stream().noneMatch(f -> f.getCpf().equals("12345678900"))) {   
                System.out.println("Nenhum administrador padrão encontrado. Cadastrando...");
                
                Endereco endereco = new Endereco("Rua A", "123", "", "Bairro B", "Cidade C", "Estado D", "00000-000");
                Telefone telefone = new Telefone("11", "99999-9999");
                Funcionario funcionario = new Funcionario("Admin", "12345678900", LocalDate.of(1990,1,1), endereco, telefone, "Gerente");

                fachada.cadastrarFuncionario(funcionario);
                System.out.println("Administrador padrão cadastrado com sucesso!");
            }
        } catch (ExceptionFuncionario.FuncionarioJaExistenteException e) {
            System.out.println("Info: Administrador padrão já cadastrado.");
        } catch (ExceptionValidacao.CampoObrigatorioException e) {
            System.err.println("Ocorreu um erro inesperado na inicialização: " + e.getMessage());
            return;
        }
        
        System.out.println("\n=== Sistema de Gestão de Biblioteca ===");
        UI.opcao();
        
    }
}