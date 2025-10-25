package br.com.biblioteca.repositorio;

import br.com.biblioteca.negocio.basicas.Reserva;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioReserva {

    private final List<Reserva> reservas;
    private static final String CSV_PATH = "reservas.csv";
    private static final String DAT_PATH = "reservas.dat";

    public RepositorioReserva(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        this.reservas = carregarDados(repoLivros, repoUsuarios);
    }

    private void salvarDados() {
        salvarEmFormatoNativo();
        salvarEmFormatoCsv();
    }
    
    private List<Reserva> carregarDados(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        File arquivoDat = new File(DAT_PATH);
        if (arquivoDat.exists()) {
            List<Reserva> reservasNativas = carregarDeFormatoNativo();
            if (reservasNativas != null) {
                return reservasNativas;
            }
        }
        
        File arquivoCsv = new File(CSV_PATH);
        if (arquivoCsv.exists()) {
            return carregarDeFormatoCsv(repoLivros, repoUsuarios);
        }

        return new ArrayList<>();
    }
    
    private void salvarEmFormatoNativo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_PATH))) {
            oos.writeObject(this.reservas);
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar reservas em formato nativo (.dat)", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Reserva> carregarDeFormatoNativo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DAT_PATH))) {
            return (List<Reserva>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar reservas do formato nativo (.dat)", e);
        }
    }
    
    private void salvarEmFormatoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("isbn_livro,cpf_usuario,data_reserva,data_limite,ativa");
            bw.newLine();
            for (Reserva r : this.reservas) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
        	throw new RuntimeException("Falha ao salvar reservas em formato CSV", e);
        }
    }

    private List<Reserva> carregarDeFormatoCsv(RepositorioLivro repoLivros, RepositorioUsuario repoUsuarios) {
        List<Reserva> reservasCarregadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                Reserva r = Reserva.fromCSV(line, repoLivros, repoUsuarios);
                if (r != null) {
                    reservasCarregadas.add(r);
                }
            }
        } catch (Exception e) {
        	throw new RuntimeException("Falha ao carregar reservas do formato CSV. O arquivo pode estar corrompido.", e);
        }
        return reservasCarregadas;
    }
    
    public void adicionar(Reserva reserva) {
        reservas.add(reserva);
        salvarDados();
    }
    
    public void atualizar(Reserva reservaAtualizada) {
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getUsuario().getCpf().equals(reservaAtualizada.getUsuario().getCpf()) &&
                r.getLivro().getIsbn().equals(reservaAtualizada.getLivro().getIsbn())) {
                reservas.set(i, reservaAtualizada);
                salvarDados();
                return;
            }
        }
    }
    
    public boolean remover(Reserva reserva) {
        boolean removeu = reservas.remove(reserva);
        if (removeu) {
            salvarDados();
        }
        return removeu;
    }  
    
    public Reserva buscarPorCpfEIsbn(String cpfUsuario, String isbnLivro) {
        return reservas.stream()
                .filter(r -> r.getUsuario().getCpf().equals(cpfUsuario)
                          && r.getLivro().getIsbn().equals(isbnLivro)
                          && r.isAtiva())
                .findFirst()
                .orElse(null);
    }
    

    public List<Reserva> listarTodas() {
        return new ArrayList<>(reservas);
    }

    public List<Reserva> buscarPorUsuario(String cpfUsuario) {
        return reservas.stream()
                .filter(r -> r.getUsuario().getCpf().equals(cpfUsuario) && r.isAtiva())
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarPorIsbnLivro(String isbn) {
        return reservas.stream()
                .filter(r -> r.getLivro().getIsbn().equals(isbn) && r.isAtiva())
                .collect(Collectors.toList());
    }

    public Reserva buscarReserva(String cpfUsuario, String isbnLivro) {
        return reservas.stream()
                .filter(r -> r.getUsuario().getCpf().equals(cpfUsuario)
                          && r.getLivro().getIsbn().equals(isbnLivro)
                          && r.isAtiva())
                .findFirst()
                .orElse(null);
    }
}
