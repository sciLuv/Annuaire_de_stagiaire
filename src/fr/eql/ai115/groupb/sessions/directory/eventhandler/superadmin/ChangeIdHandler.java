package fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeIdHandler implements EventHandler<ActionEvent> {

    private static final String CREDENTIALS_FILE = "C:\\Users\\Formation\\Documents\\Project\\SessionsDirectory\\files\\superadminaccess.txt";
    private Scene scene;
    private Stage ModifyAdminStage;
    private TextField idField;
    private PasswordField pswdField;
    private TextField newIdField;
    private PasswordField newpswdField;
    private Text actionText;
    private Stage primaryStage;

    public ChangeIdHandler(Stage ModifyAdminStage, TextField idField,
                           PasswordField pswdField, TextField newIdField, PasswordField newpswdField, Text actionText,
                           Stage primaryStage) {
        this.ModifyAdminStage = ModifyAdminStage;
        this.idField = idField;
        this.pswdField = pswdField;
        this.newIdField = newIdField;
        this.newpswdField = newpswdField;
        this.actionText = actionText;
        this.primaryStage = primaryStage;
    }



    @Override
    public void handle(ActionEvent actionEvent) {
        String inputId = idField.getText();
        String inputPswd = pswdField.getText();
        String inputnewId = newIdField.getText();
        String inputnewPswd = newpswdField.getText();

        // if id and pswd are correct then open admin home
        if (!inputId.isEmpty() && !inputPswd.isEmpty() && !inputnewId.isEmpty() && !inputnewPswd.isEmpty()) {
            // Vous pouvez ajouter ici la vérification de l'authentification
            // par rapport aux informations stockées (par exemple, dans une base de données)

            // Enregistrez les nouvelles informations dans votre système
            actionText.setText("Identifiants modifiés avec succès !");
            actionText.setFill(Color.GREEN);
            System.out.println("Nom d'utilisateur : " + inputId);
            System.out.println("Mot de passe actuel : " + inputPswd);
            System.out.println("Nouveau nom d'utilisateur : " + inputnewId);
            System.out.println("Nouveau mot de passe : " + inputId);

            // Mettez à jour les informations dans le fichier texte ou la base de données
            updateCredentials(inputId, inputPswd, inputnewId, inputnewPswd);
        } else {
            actionText.setText("Veuillez remplir tous les champs.");
            actionText.setFill(Color.RED);
        }
    };


    private void updateCredentials(String inputId, String inputPswd, String inputnewId, String inputnewPswd) {
        // Spécifiez le chemin du fichier
        String filePath = "files/superadminaccess.txt";

        // Lire le contenu du fichier dans une liste
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Recherche de l'administrateur à mettre à jour


        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(":");
            if (parts.length == 2 && parts[0].equals(inputId)) {
                lines.set(i, inputnewId + ":" + inputnewPswd);
                // Réécrire le fichier avec les informations mises à jour
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
    }
}



