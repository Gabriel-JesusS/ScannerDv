package resources.desenvolvimento;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.commons.codec.digest.DigestUtils;

public class ApiVirusTotal {
    public static Path FilesScanner;

    public void main(String[] args) throws Exception {

        Properties props = new Properties();

        // Carrega o arquivo da mesma pasta onde esta classe se encontra
        try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
            if (input == null) {
                System.err.println(">> ERRO: Arquivo 'config.properties' não foi localizado no pacote.");
                return;
            }
            props.load(input);
        } catch (IOException e) {
            System.err.println(">> ERRO ao ler 'config.properties': " + e.getMessage());
            return;
        }

        String apiKey = props.getProperty("maps.key");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println(">> ERRO: A chave 'maps.key' não foi definida em config.properties");
            return;
        }

        String calcularHash;
        try (InputStream fileStream = Files.newInputStream(FilesScanner)) {
            calcularHash = DigestUtils.sha256Hex(fileStream);
        }

        String url = "https://www.virustotal.com/api/v3/files/" + calcularHash;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-apikey", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


// Adicione estas linhas para depurar:
        System.out.println("--- RESULTADO VIRUSTOTAL ---");
        System.out.println("Código HTTP: " + response.statusCode());
        System.out.println("Corpo da Resposta: " + response.body());
        System.out.println("----------------------------");
    }
}