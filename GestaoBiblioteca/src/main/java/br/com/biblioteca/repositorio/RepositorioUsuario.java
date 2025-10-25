package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario {

    private List<Usuario> usuarios;
    private static final String CSV_PATH = "usuarios.csv";
    private static final String DAT_PATH = "usuarios.dat";

    public RepositorioUsuario() {
        this.usuarios = carregarDados();
    }
    
    public void adicionar(Usuario usuario) {
        usuarios.add(usuario);
        salvarDados();
    }
    
    public void atualizar(Usuario usuarioAtualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCpf().equals(usuarioAtualizado.getCpf())) {
                usuarios.set(i, usuarioAtualizado);
                salvarDados();
                return;
            }
        }
    }
    
    public boolean removerPorCpf(String cpf) {
        boolean removeu = usuarios.removeIf(u -> u.getCpf().equals(cpf));
        if (removeu) {
            salvarDados();
        }
        return removeu;
    }
    
    public void salvarDados() {
        salvarEmFormatoNativo();
        salvarEmFormatoCsv();
    }

    private List<Usuario> carregarDados() {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            List<Usuario> usuariosNativos = carregarDeFormatoNativo();
            if (usuariosNativos != null) {
                return usuariosNativos;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            return carregarDeFormatoCsv();
        }

        return new ArrayList<>();
    }

    private void salvarEmFormatoNativo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.usuarios);
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar usuários em formato nativo (.dat)", e);
        }
    }

    @SuppressWarnings("unchecked")
	private List<Usuario> carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar usuários do formato nativo (.dat)", e);
        }
    }

    private void salvarEmFormatoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("nome,cpf,dataNascimento,logradouro,numero,complemento,bairro,cidade,estado,cep,ddd,numero_tel,ativo,multa,emprestimosAtivos");
            bw.newLine();
            for (Usuario u : this.usuarios) {
                bw.write(u.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar usuários em formato CSV", e);
        }
    }

    private List<Usuario> carregarDeFormatoCsv() {
        List<Usuario> usuariosCarregados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                usuariosCarregados.add(Usuario.fromCSV(line));
            }
        } catch (Exception e) {
        	throw new RuntimeException("Falha ao carregar usuários do formato CSV. O arquivo pode estar corrompido.", e);
        }
        return usuariosCarregados;
    }
    
    public Usuario buscarPorCpf(String cpf) {
        return usuarios.stream()
                       .filter(u -> u.getCpf().equals(cpf))
                       .findFirst()
                       .orElse(null);
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public boolean existeUsuario(String cpf) {
        return usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf));
    }

}
