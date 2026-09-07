package resources.desenvolvimento;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.stream.Stream;

public class ScannerDv {

    public static String threatsFolder = "";
    public static String segundo = "";


    // verifica se o local esta vazio sem ameaças
    private static Boolean statusDiretorioVazio = true;


    public static void main(String[] args) {


        Path dadosEntradaDiretorio = Paths.get(threatsFolder);
        Path dadosQuarentena = Paths.get(threatsFolder).resolve("Quarentena");
        try (Stream<Path> stream = Files.list(dadosEntradaDiretorio)) {
            stream.forEach(directorArchive -> {


                try {
                    String nameFile = directorArchive.getFileName().toString();
                    Path destino =   Files.createDirectories(dadosQuarentena).resolve(nameFile);
                    statusDiretorioVazio = false;

                    // Filtra extensões de interesse
                    if (nameFile.toLowerCase(Locale.ROOT).endsWith(".bat") ||
                            nameFile.toLowerCase(Locale.ROOT).endsWith(".exe") ||
                            nameFile.toLowerCase(Locale.ROOT).endsWith(".txt") || nameFile.toLowerCase(Locale.ROOT).endsWith(".com")) {

                        Interface app = new Interface();
                        Interface.contFile.add(nameFile);
ApiVirusTotal apiVirusTotal = new ApiVirusTotal();
apiVirusTotal.FilesScanner = directorArchive;
apiVirusTotal.main(args);
                 //move o arquivo para o diretorio criado na propria pasta examinada
                        try {
                            Thread.sleep(3000); // delay de 3 segundos
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }


                        Files.move(directorArchive,destino, StandardCopyOption.REPLACE_EXISTING);
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