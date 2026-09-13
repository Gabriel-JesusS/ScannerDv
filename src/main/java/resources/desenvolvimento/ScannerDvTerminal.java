package resources.desenvolvimento;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.DigestUtils;

public class ScannerDvTerminal {

    public static String threatsFolder = "";


    // verifica se o local esta vazio sem ameaças
    private static Boolean statusDiretorioVazio = true;


    public static void main(String[] args) {

        System.out.println("digite o nome da pasta:");
        Scanner scanner = new Scanner(System.in);
        String directorEntry = scanner.next();
        threatsFolder = directorEntry;

        //cria uma condição para não criar outrar Quarentena dentro dela propria
        if (directorEntry.endsWith("Quarentena")) {
            System.out.println("os arquivos ja estão em quarentena");
            return;

        }


        Path dadosEntradaDiretorio = Paths.get(threatsFolder);
        Path dadosQuarentena = Paths.get(threatsFolder).resolve("Quarentena");


        try (Stream<Path> stream = Files.list(dadosEntradaDiretorio)) {
            stream.forEach(directorArchive -> {


                try {
                    String nameFile = directorArchive.getFileName().toString();
                    Path destino = Files.createDirectories(dadosQuarentena).resolve(nameFile);
                    statusDiretorioVazio = false;

                    // Filtra extensões de interesse
                    if (nameFile.toLowerCase(Locale.ROOT).endsWith(".bat") ||
                            nameFile.toLowerCase(Locale.ROOT).endsWith(".exe") ||
                            nameFile.toLowerCase(Locale.ROOT).endsWith(".txt") || nameFile.toLowerCase(Locale.ROOT).endsWith(".com")) {


                        ApiVirusTotal apiVirusTotal = new ApiVirusTotal();
                        ApiVirusTotal.FilesScanner = directorArchive;

                        try {
                            Thread.sleep(3000); // delay de 3 segundos
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        apiVirusTotal.main(args);

                        //move o arquivo para o diretorio criado na propria pasta examinada
                        Files.move(directorArchive, destino, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println(">> AMEAÇA DETECTADA! Movendo " + nameFile + " para quarentena.");

                    } else {
                        System.out.println("diretorio limpo: " + nameFile);

                    }


                } catch (Exception e) {
                    System.err.println("Falha ao processar arquivo: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("Erro ao listar arquivos: " + e.getMessage());
        }

        if (statusDiretorioVazio) {
            System.out.println("Diretório vazio ou sem ameaças processadas.");
        } else {
            System.out.println("Processamento concluído.");


        }

    }
}