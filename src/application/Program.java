package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> listSale = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                listSale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
                        Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));

                line = br.readLine();
            }

            Map<String, Double> mapSale = new HashMap<>();
            for(Sale sale : listSale) {
                mapSale.put(
                        sale.getSeller(),
                        listSale.stream().
                                filter(s -> s.getSeller().equals(sale.getSeller()))
                                .map(s -> s.getTotal())
                                .reduce(0.0, Double::sum)
                        );
            }

            System.out.println("Total de vendas por vendedor: ");
            for(String seller : mapSale.keySet()) {
                System.out.printf("%s - R$ %.2f%n", seller, mapSale.get(seller));
            }
        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }
}
