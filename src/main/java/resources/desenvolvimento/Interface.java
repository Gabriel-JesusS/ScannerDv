package resources.desenvolvimento;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

// Stage (Janela): A moldura de vidro.

// Scene (Palco): O espaço onde as coisas acontecem (onde definimos o tamanho 400x200).

// Root (Raiz): O seu StackPane. Ele é o "pai" de todos.

public class Interface extends Application {

    public static List<String> apiData = new ArrayList<>();

    public static List<String> apiDataCategory = new ArrayList<>();
    public static List<String> contFile = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    @SuppressWarnings("static-access")
    public void start(@NotNull Stage stage) {

        // raiz do projeto
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // container para alinhar os objetos
        StackPane containerGrup = new StackPane();
        Scene scene = new Scene(containerGrup, 600, 600);


        // titulo do projeto "Scanner de ameaças"
        Label Title = new Label("ScannerDv");

//envia comando para compilar o programa
        Button sendButton = new Button("Enviar");
        // para acessar os arquivos do dispositivo
        Button threatsButton = new Button("Verifique seus arquivos! ");
        //tela onde ficara as informações encontradas
        Rectangle quarantineScreen = new Rectangle(400, 400);

        TextArea informationQuarantine = new TextArea("Arquivos encontrados");
        informationQuarantine.setWrapText(true);
        informationQuarantine.setEditable(false);
        informationQuarantine.setMaxWidth(400);
        informationQuarantine.setCursor(Cursor.DEFAULT);



        informationQuarantine.setPrefRowCount(10); // altura inicial
        informationQuarantine.setPrefWidth(200);


// Selecione pasta para fazer um varredura de de arquivos maliciosos
        threatsButton.setOnAction(event -> {

            DirectoryChooser directoryChooser = new DirectoryChooser();

            directoryChooser.setTitle("Selecionar pasta");
            File selectedFolder = directoryChooser.showDialog(stage);
            ScannerDv scannerDv = new ScannerDv();
            scannerDv.threatsFolder = selectedFolder.toString();

        });


        sendButton.setOnAction(e -> {


            informationQuarantine.setText("Compilando...");
            new Thread(() -> {
                //chama a classe scanner para compilar o codigo juntamente
                ScannerDv scannerDv = new ScannerDv();
                scannerDv.main(null);


                try {
                    Thread.sleep(3000); // delay de 3 segundos
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }


                Platform.runLater(() -> {

                    informationQuarantine.setText( String.join("\n", apiDataCategory).replace("=", ": "));

                });

            }).start();
        });


        Rectangle retangle = new Rectangle(750, 750);

        //Alinhar a tela onde ficara as informações encontradas dos arquivos
        StackPane quarantineContainer = new StackPane(quarantineScreen, informationQuarantine);


        root.getChildren().addAll(Title, quarantineContainer, threatsButton, sendButton);

        containerGrup.getChildren().addAll(retangle, root);

        stage.setScene(scene);
        scene.getStylesheets().add("resources/desenvolvimento/Style.css");
        retangle.getStyleClass().add("teste");
        informationQuarantine.getStyleClass().add("textDataApi");
        quarantineScreen.getStyleClass().add("Screen");
        threatsButton.getStyleClass().add("buttonDirectory");
        Title.getStyleClass().add("titleProject");
        root.getStyleClass().add(".root");
        sendButton.getStyleClass().add("buttonDirectory");
        stage.show();
    }
}