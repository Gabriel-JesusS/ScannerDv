package resources.desenvolvimento;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.stream.Stream;


public class ScannerDv {

    public static String threatsFolder = "";

    // verifica se o local esta vazio sem ameaças
    private static Boolean directorioStatus = true;


    public static void main(String[] args) {
        Path dataInputDirectory = Paths.get(threatsFolder);

//cria uma condição para não criar outrar Quarentena dentro dela propria
        if (threatsFolder.endsWith("Quarentena")) {
            System.out.println("os arquivos ja estão em quarentena");
            return;

        }

//caminho da pasta Quarentena
        Path dataQuarantine = Paths.get(threatsFolder).resolve("Quarentena");
        try (Stream<Path> stream = Files.list(dataInputDirectory)) {
            stream.forEach(directorArchive -> {


                try {
                    String nameFiles = directorArchive.getFileName().toString();
                    Path QuarantineFolder = Files.createDirectories(dataQuarantine).resolve(nameFiles);
                    directorioStatus = false;

                    // Filtra extensões de interesse
                    if (nameFiles.toLowerCase(Locale.ROOT).endsWith(".bat") ||
                            nameFiles.toLowerCase(Locale.ROOT).endsWith(".exe") ||
                            nameFiles.toLowerCase(Locale.ROOT).endsWith(".txt") || nameFiles.toLowerCase(Locale.ROOT).endsWith(".com")) {
                    //Manda a quantidades de arquivos para uma array da classe Interface
                        Interface app = new Interface();
                        Interface.contFile.add(nameFiles);

                        //manda os arquivos para API verificar o conteudo
                        ApiVirusTotal apiVirusTotal = new ApiVirusTotal();
                        ApiVirusTotal.FilesScanner = directorArchive;
                        apiVirusTotal.main(args);

                        try {
                            Thread.sleep(3000); // delay de 3 segundos
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }


                        //move o arquivo para o diretorio criado na propria pasta examinada
                        Files.move(directorArchive, QuarantineFolder, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println(">> AMEAÇA DETECTADA! Movendo " + nameFiles + " para quarentena.");

                    } else {
                        System.out.println("diretorio limpo: " + nameFiles);

                    }


                } catch (Exception e) {
                    System.err.println("Falha ao processar arquivo: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("Erro ao listar arquivos: " + e.getMessage());
        }

        if (directorioStatus) {
            System.out.println("Diretório vazio ou sem ameaças processadas.");
        } else {
            System.out.println("Processamento concluído.");


        }

    }
}