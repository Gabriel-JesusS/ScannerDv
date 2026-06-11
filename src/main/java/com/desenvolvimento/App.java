package com.desenvolvimento;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

//SEPERAR AS APLICAÇÕES EM ARQUIVOS PARA FICAR MAIS DINAMICO


// Stage (Janela): A moldura de vidro.

// Scene (Palco): O espaço onde as coisas acontecem (onde definimos o tamanho 400x200).

// Root (Raiz): O seu StackPane. Ele é o "pai" de todos.

public class App extends Application {

    @Override
    @SuppressWarnings("static-access")
    public void start(Stage stage) {

        // raiz do projeto
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // container para alinhar os objetos
        StackPane containerGrup = new StackPane();
        Scene scene = new Scene(containerGrup, 400, 200);

        // titulo do projeto "Scanner de ameaças"
        Label label = new Label("Scaenner threats");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(20));

        Button sendButton = new Button("Enviar");
        Button threatsButton = new Button("Search threats ");
        Button quarantineButton = new Button("Search quarantine ");

        TextField threatsFolder = new TextField();
        TextField quarantineFolder = new TextField();

        threatsFolder.setMaxWidth(250);
        threatsFolder.setMaxHeight(250);

        quarantineFolder.setMaxWidth(250);
        quarantineFolder.setMaxHeight(250);
        sendButton.setMaxWidth(100);
        threatsButton.setMaxWidth(100);

        threatsButton.setOnAction(event -> {

            DirectoryChooser directoryChooser = new DirectoryChooser();

            directoryChooser.setTitle("Seleionar pasta");
            File selectedFolder = directoryChooser.showDialog(stage);

            ScannerDv scannerDv = new ScannerDv();
            scannerDv.primeiro = selectedFolder.toString();

        });

        quarantineButton.setOnAction(event -> {

            DirectoryChooser directoryChooser = new DirectoryChooser();

            directoryChooser.setTitle("Seleionar pasta");
            File selectedFolder = directoryChooser.showDialog(stage);

            System.out.println(selectedFolder);
            ScannerDv scannerDv = new ScannerDv();
            scannerDv.segundo = selectedFolder.toString();

        });

        sendButton.setOnAction(e -> {
            new Thread(() -> {
                ScannerDv scannerDv = new ScannerDv();
                scannerDv.main(null);
            }).start();
        });

        // buttonRotate.setOnAction(e -> {

        // new Thread(() -> {
        // ScannerTR scannerTR = new ScannerTR();
        // System.out.println(nomeLogin.getText());

        // String diretorioAvariado = nomeLogin.getText().toString();
        // String diretorioQuarentena = passwordLogin.getText().toString();

        // scannerTR.diretorio1 = new String[] { diretorioAvariado, diretorioQuarentena
        // };
        // scannerTR.main(null);
        // }).start();
        // // scannerTR.diretorio1 = nomeLogin.getText().toString();
        // // scannerTR.diretorio2 = passwordLogin.getText().toString();
        // });

        Rectangle retangle = new Rectangle();
        retangle.setFill(Color.BLACK);
        retangle.setWidth(350);
        retangle.setHeight(350);

        root.getChildren().addAll(label, threatsButton, quarantineButton, sendButton);
        containerGrup.getChildren().addAll(retangle, root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}