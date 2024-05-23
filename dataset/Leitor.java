package dataset;

import arquivo.Arquivo;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Leitor {
    private static final String CAMINHO_CSV = "C:\\Users\\railt\\Documents\\UFMA\\ED2 - DALLYSON[\\Tab02- ED2\\src\\dataset\\arquivo.csv";
    private static final int TAMANHO_ARQUIVO = 10000;


   
    public static Arquivo[] lerDataset() {
        Arquivo[] arquivos = new Arquivo[TAMANHO_ARQUIVO];

        try {
            File csvFile = new File(CAMINHO_CSV);
            Scanner scanner = new Scanner(csvFile);
            scanner.nextLine(); // pular a primeira linha

            for (int i = 0; i < TAMANHO_ARQUIVO && scanner.hasNextLine(); i++) {
                String linha = scanner.nextLine();
                String[] campos = linha.split(",");

                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Arquivo arquivo = new Arquivo(campos[0], campos[1], campos[1], Integer.parseInt(campos[3]),  dateFormat.parse(campos[4]), dateFormat.parse(campos[5]));
                    arquivos[i] = arquivo;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo CSV não foi encontrado.");
            e.printStackTrace();
        }
        return arquivos;
    }
}
