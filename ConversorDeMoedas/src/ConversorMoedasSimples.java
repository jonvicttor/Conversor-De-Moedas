import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConversorMoedasSimples {
    public static void main(String[] args) {
        Map<String, Double> taxas = new HashMap<>();
        taxas.put("USDBRL", 5.57);
        taxas.put("EURBRL", 6.37);
        taxas.put("BRLEUR", 0.16);
        taxas.put("BRLUSD", 0.18);
        taxas.put("USDEUR", 0.93);
        taxas.put("EURUSD", 1.08);

        // Mapa de nomes para siglas
        Map<String, String> nomeParaSigla = new HashMap<>();
        nomeParaSigla.put("dolar", "USD");
        nomeParaSigla.put("real", "BRL");
        nomeParaSigla.put("euro", "EUR");

        // Mapa de siglas para nomes (para exibir no final)
        Map<String, String> siglaParaNome = new HashMap<>();
        siglaParaNome.put("USD", "Dólar");
        siglaParaNome.put("BRL", "Real");
        siglaParaNome.put("EUR", "Euro");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Conversor de Moedas!");

        System.out.println("Digite o nome da moeda de Origem (ex: real, dólar, euro): ");
        String nomeOrigem = scanner.nextLine().trim().toLowerCase();
        String moedaOrigem = nomeParaSigla.get(nomeOrigem);

        System.out.println("Digite o nome da moeda de Destino (ex: real, dólar, euro): ");
        String nomeDestino = scanner.nextLine().trim().toLowerCase();
        String moedaDestino = nomeParaSigla.get(nomeDestino);

        // Verificação se o usuário digitou nomes válidos
        if (moedaOrigem == null || moedaDestino == null) {
            System.out.println("Erro: Nome de moeda inválido. Use apenas: real, dólar ou euro.");
            return;
        }

        System.out.println("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        scanner.close();

        double valorConvertido = 0.0;
        String chaveConversao = moedaOrigem + moedaDestino;

        if (moedaOrigem.equals(moedaDestino)) {
            valorConvertido = valor;
            System.out.println("Moedas de origem e destino são as mesmas. Nenhum cálculo necessário.");
        } else if (taxas.containsKey(chaveConversao)) {
            valorConvertido = valor * taxas.get(chaveConversao);
        } else {
            System.out.println("Não foi possível encontrar uma taxa de conversão direta para " + nomeOrigem + " para " + nomeDestino);
            System.out.println("Verificando se a taxa inversa existe...");

            String chaveInversa = moedaDestino + moedaOrigem;

            if (taxas.containsKey(chaveInversa)) {
                valorConvertido = valor / taxas.get(chaveInversa);
            } else {
                System.out.println("Tentando conversão via uma moeda intermediária (ex: dólar)...");

                Double taxaOrigemParaUSD = taxas.get(moedaOrigem + "USD");
                Double taxaUSDParaDestino = taxas.get("USD" + moedaDestino);

                if (taxaOrigemParaUSD != null && taxaUSDParaDestino != null) {
                    valorConvertido = valor * taxaOrigemParaUSD * taxaUSDParaDestino;
                } else if (taxaOrigemParaUSD != null && moedaDestino.equals("USD")) {
                    valorConvertido = valor * taxaOrigemParaUSD;
                } else if (taxaUSDParaDestino != null && moedaOrigem.equals("USD")) {
                    valorConvertido = valor * taxaUSDParaDestino;
                } else {
                    System.out.println("Erro: Não há taxa de conversão válida para essas moedas.");
                    return;
                }
            }
        }

        // Exibe o resultado com os nomes das moedas
        String nomeMoedaOrigemFinal = siglaParaNome.getOrDefault(moedaOrigem, moedaOrigem);
        String nomeMoedaDestinoFinal = siglaParaNome.getOrDefault(moedaDestino, moedaDestino);

        System.out.printf("%.2f %s equivale a %.2f %s%n", valor, nomeMoedaOrigemFinal, valorConvertido, nomeMoedaDestinoFinal);
    }
}
