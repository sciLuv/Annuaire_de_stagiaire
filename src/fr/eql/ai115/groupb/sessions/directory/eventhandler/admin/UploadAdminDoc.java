package fr.eql.ai115.groupb.sessions.directory.eventhandler.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UploadAdminDoc implements EventHandler<ActionEvent> {

        private final Stage primaryStage;

        public UploadAdminDoc(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            String filePath = "files/docs/Introduction_documentation_espace_administrateur.pdf";

            try {
                openAdminDoc(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public  void openAdminDoc(String filePath) throws IOException {
            File file = new File(filePath);
            // Utiliser Desktop pour ouvrir le fichier avec l'application par défaut
            Desktop.getDesktop().open(file);

        }

}
