package fr.eql.ai115.groupb.sessions.directory.eventhandler.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddNewAdmin implements EventHandler<ActionEvent> {

        private Scene scene;
        private TextField idField;
        private PasswordField pswdField;
        private Text actionText;
        private Stage primaryStage;

    public Scene getScene() {
        return scene;
    }

    public TextField getIdField() {
        return idField;
    }

    public PasswordField getPswdField() {
        return pswdField;
    }

    public Text getActionText() {
        return actionText;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AddNewAdmin(TextField idField,
                       PasswordField pswdField, Text actionText,
                       Stage primaryStage) {
            this.idField = idField;
            this.pswdField = pswdField;
            this.actionText = actionText;
            this.primaryStage = primaryStage;

        }

        public void handle(ActionEvent e) {
            String username = idField.getText();
            String password = pswdField.getText();

            if (!username.isEmpty() && !password.isEmpty()) {

                // Enregistrez le nom d'utilisateur et le mot de passe haché dans votre système
                // Ici, vous devriez probablement stocker ces informations dans une base de données sécurisée
                actionText.setText("Nouvel administrateur créé !");
                actionText.setFill(Color.GREEN);
//                System.out.println("Nom d'utilisateur : " + username);
//                System.out.println("Mot de passe : " + password);
                writeToFile(username,password);

            } else {
                actionText.setText("Veuillez remplir tous les champs.");
                actionText.setFill(Color.RED);
            }
        }



        private void writeToFile(String username, String password) {
            // Spécifiez le chemin du fichier
            String filePath = "adminaccess.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // Écrire le nom d'utilisateur et le mot de passe dans le fichier
                writer.write(username + ":" + password);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
