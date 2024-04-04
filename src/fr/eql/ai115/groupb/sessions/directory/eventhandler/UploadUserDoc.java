package fr.eql.ai115.groupb.sessions.directory.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UploadUserDoc implements EventHandler<ActionEvent> {
    private final Stage primaryStage;

    public UploadUserDoc(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String filePath = "files/docs/Introduction_documentation_espace_utilisateur.pdf";

        try {
            openUserDoc(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public  void openUserDoc(String filePath) throws IOException {
        File file = new File(filePath);
        // Utiliser Desktop pour ouvrir le fichier avec l'application par défaut
        Desktop.getDesktop().open(file);

    }
}
