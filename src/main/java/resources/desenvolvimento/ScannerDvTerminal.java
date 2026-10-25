package resources.desenvolvimento;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static resources.desenvolvimento.ApiFileEntry.DirectorialFilesFound;

public class ScannerDvTerminal {

    public static String getDirector(Scanner scanner) {
//Cria um loop para
        while (true) {
            System.out.println("digite o nome da pasta:");
            String inputDirector = scanner.nextLine().trim();

            //cria uma condição para não criar outrar Quarentena dentro dela propria
            if (inputDirector.endsWith("Quarentena")) {

                System.out.println("os arquivos ja estão em quarentena");
                continue;
            }
            return inputDirector;
        }

    }

    public static void VerificFile(String category, int value, Path filesScanner, String directorEntry) {

        List<ApiFileEntry> lista = new ArrayList<>();
        lista.add(new ApiFileEntry(category, value));

        for (ApiFileEntry d : lista) {

            if (d.getCategoryName() == "malicious" && d.getCategoryValue() >= 1) {
                System.out.println("Ameaças detectadas");
                MoveFileQuaretena(filesScanner, directorEntry);
            }
        }
    }


    public static void MoveFileQuaretena(Path filesScanner, String directorEntry) {
        String nameFiles = filesScanner.getFileName().toString();
        Path dataQuarantine = Paths.get(directorEntry).resolve("Quarentena");

        try {

            Path QuarantineFolder = Files.createDirectories(dataQuarantine).resolve(nameFiles);
            Files.move(filesScanner, QuarantineFolder, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(">> AMEAÇA DETECTADA! Movendo " + nameFiles + " para quarentena.");


        System.out.println(filesScanner);
    }


    public static void perguntarNovaVarredura() {
        System.out.println("\n-----------------------------------------");
        System.out.print("Deseja realizar uma nova varredura? (s/n): ");
        Scanner scanner = new Scanner(System.in);
        String inputDirector = scanner.nextLine().trim();

        DirectorialFilesFound.clear();

        if (inputDirector.equalsIgnoreCase("n") || inputDirector.equalsIgnoreCase("nao")) {
            System.out.println("Encerrando o programa. Até logo!");
            System.exit(0);

        }
        main(null);
    }


    public static void scanningDirector(Path dataInputDirectory, String directorEntry) {

        try (Stream<Path> stream = Files.list(dataInputDirectory)) {
            //filter (Files::isRegularFile) e um filtro que filtra arquivos faz com que ele apenas manda arquivos para serem processados nas demais funções
            stream.filter(Files::isRegularFile).forEach(directorArchive -> {
                String nameFile = directorArchive.getFileName().toString();
                DirectorialFilesFound.add(nameFile);

                try {
                    ApiVirusTotal api = new ApiVirusTotal();
                    api.main(directorArchive, directorEntry);

                } catch (Exception e) {
                    System.out.println(
                            "Erro ao analisar " + nameFile + ": " + e.getMessage()
                    );
                }

            });

        } catch (Exception e) {
            System.out.println("Erro ao listar arquivos: " + e.getMessage());
        }
        perguntarNovaVarredura();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String directorEntry = getDirector(scanner);


        //converte a String para o tipo path para o caminho do diretorio que deseja scannear
        Path dataInputDirectory = Paths.get(directorEntry);

        scanningDirector(dataInputDirectory, directorEntry);


    }


}