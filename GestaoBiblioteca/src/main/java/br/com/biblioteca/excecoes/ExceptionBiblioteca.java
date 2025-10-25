package br.com.biblioteca.excecoes;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ExceptionBiblioteca extends Exception {

    private static final Scanner sc = new Scanner(System.in);
    
   
    public ExceptionBiblioteca(String mensagem) {
        super(mensagem);
    }
    
    public ExceptionBiblioteca() {
        super();
    }
    
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    public static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(sc.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número válido.");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String input = sc.nextLine().trim();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Digite no formato yyyy-mm-dd.");
            }
        }
    }

    public static boolean lerBoolean(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (S/N): ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.equals("S")) return true;
            if (input.equals("N")) return false;
            System.out.println("Opção inválida. Digite S ou N.");
        }
    }
}

