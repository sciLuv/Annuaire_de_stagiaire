package fr.eql.ai115.groupb.sessions.directory.eventhandler.admin;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImportFileHandler implements EventHandler {

    private String inputFilePath = "C:\\Users\\Formation\\Documents\\Project\\SessionsDirectory";
    private Scene scene;
    private Stage importStage;
    private Stage primaryStage;

    public ImportFileHandler(String inputFilePath, Scene scene, Stage importStage, Stage primaryStage) {
        this.inputFilePath = inputFilePath;
        this.scene = scene;
        this.importStage = importStage;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(Event event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le fichier texte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            inputFilePath = file.getAbsolutePath();
        }
        System.out.println("Le fichier .txt a bien été importé !");
    }
}
