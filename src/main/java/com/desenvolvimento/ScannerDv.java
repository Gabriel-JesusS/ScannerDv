package com.desenvolvimento;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class ScannerDv {

    public static String primeiro = "";
    public static String segundo = "";

    // verifica se o local esta vazio sem ameaças
    private static Boolean statusDiretorioVazio = true;

    public static void main(String[] args) {

        // definir o diretorio que queria ser examinado
        System.out.println(primeiro);
        System.out.println("Digite o caminho do detorio: ");
        // Scanner caminhoDiretorio = new Scanner(System.in);

        // String caminhoDiretorioEntrada = caminhoDiretorio.nextLine();
        // converte o caminho do diretorio no tipo Path
        Path dadosEntradaDiretorio = Paths.get(primeiro);
        // Usamos try-with-resources para garantir que o sistema feche o acesso à pasta
        // Stream<Path> percorrendo recursivamente a árvore de arquivos
        try (Stream<Path> stream = Files.list(dadosEntradaDiretorio)) {
            // vai pegar o caminho direto do diretorio
            stream.forEach(teste -> {

                try {

                    statusDiretorioVazio = false;

                    String nameFile = teste.getFileName().toString();

                    System.out.println("Digite o nome do diretorio que a ameaça dever armazenada:");

                    // Scanner localSafe = new Scanner(System.in);

                    // String localSafeDirectory = localSafe.nextLine();

                    // s: Unir pastas e nomes de arquivos de forma segura (usando o método
                    // .resolve()).
                    Path diretorioQuarentena = Paths.get(segundo).resolve(nameFile);
///melhorar essa linha /08/06
                    if (nameFile.endsWith(".bat") || nameFile.endsWith(".exe") || nameFile.endsWith(".txt") ) {
                        System.out
                                .println("arquivo bat encontrado " + nameFile + " movido para " + diretorioQuarentena);
                        Files.move(teste, diretorioQuarentena, StandardCopyOption.REPLACE_EXISTING);

                    } else {
                        System.out.println("sem arquivos bat");
                    }

                } catch (Exception e) {
                    System.err
                            .println("Falha ao mover " + ": " + e.getClass().getSimpleName() + " - " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("Erro ao listar arquivos: " + e.getMessage());
        }

        if (statusDiretorioVazio) {
            System.out.println("sem ameaças");
        } else {
            System.out.println("Ameaça detectada!!");
        }

    }

}