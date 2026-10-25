package resources.desenvolvimento;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import static resources.desenvolvimento.ApiFileEntry.DirectorialFilesFound;
import static resources.desenvolvimento.ScannerDvTerminal.VerificFile;



public class ApiVirusTotal {


    public void main(Path filesScanner, String directorEntry) throws Exception {

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
        try (InputStream fileStream = Files.newInputStream(filesScanner)) {
            calcularHash = DigestUtils.sha256Hex((fileStream));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "erro Hash");
            return;
        }

        String url = "https://www.virustotal.com/api/v3/files/" + calcularHash;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-apikey", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode json = mapper.readTree(response.body());

        JsonNode stats = json
                .path("data")
                .path("attributes")
                .path("last_analysis_stats");


        System.out.println("--- RESULTADO VIRUSTOTAL --- \n");
        //Nome do arquivo
        System.out.println("Contagem de arquivos:" + DirectorialFilesFound.size());
        System.out.println("Nome do arquivo: " + filesScanner.getFileName());
//loop para coletar os dados do json da api
        for (Map.Entry<String, JsonNode> campo : stats.properties()) {

            String Category = campo.getKey();
            JsonNode valores = campo.getValue();


            System.out.println("Categoria: " + Category + ":" + valores.asInt() + "\n");

            VerificFile(Category, valores.asInt(), filesScanner, directorEntry);
        }


        System.out.println("Código HTTP: " + response.statusCode() + "\n");


    }


}