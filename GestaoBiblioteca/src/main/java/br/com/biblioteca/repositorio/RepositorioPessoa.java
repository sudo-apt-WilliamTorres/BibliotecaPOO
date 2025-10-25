package br.com.biblioteca.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.biblioteca.negocio.basicas.Pessoa;

public class RepositorioPessoa {
    private List<Pessoa> pessoas;

    public RepositorioPessoa() {
        this.pessoas = new ArrayList<>();
    }

    public void adicionar(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public boolean removerPorNome(String nome) {
        return pessoas.removeIf(p ->
            p.getNome().equalsIgnoreCase(nome)
        );
    }

    public Optional<Pessoa> buscarPorNome(String nome) {
        return pessoas.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Pessoa> listarTodas() {
        return new ArrayList<>(pessoas);
    }
}