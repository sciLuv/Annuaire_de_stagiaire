package fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminManagerHandler implements EventHandler<ActionEvent> {

    private static final String CREDENTIALS_FILE = "C:\\Users\\Formation\\Documents\\Project\\SessionsDirectory\\files\\adminaccess.txt";
    private Scene scene;
    private Stage adminManagerStage;
    private TextField idField;
    private PasswordField pswdField;
    private Text actionText;
    private Stage primaryStage;

    public AdminManagerHandler(Stage modifyAdminStage, TextField idField,
                               PasswordField pswdField, Text actionText,
                               Stage primaryStage) {
        this.adminManagerStage = modifyAdminStage;
        this.idField = idField;
        this.pswdField = pswdField;
        this.actionText = actionText;
        this.primaryStage = primaryStage;
}

    public void handle(ActionEvent e) {
        String username = idField.getText();
        String password = pswdField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            // Générer un sel aléatoire pour le hachage

            // Enregistrez le nom d'utilisateur et le mot de passe haché dans votre système
            // Ici, vous devriez probablement stocker ces informations dans une base de données sécurisée
            actionText.setText("Nouvel administrateur créé !");
            actionText.setFill(Color.GREEN);
            System.out.println("Nom d'utilisateur : " + username);
            System.out.println("Mot de passe : " + password);

        } else {
            actionText.setText("Veuillez remplir tous les champs.");
            actionText.setFill(Color.RED);
        }
        writeToFile(username, password);
    }


    private void writeToFile(String username, String password) {
        // Spécifiez le chemin du fichier
        String filePath = "administrateurs.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Écrire le nom d'utilisateur et le mot de passe dans le fichier
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean deleteAdmin(String usernameToDelete) {
        // Spécifiez le chemin du fichier
        String filePath = "administrateurs.txt";

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

        // Recherche de l'administrateur à supprimer
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(":");
            if (parts.length == 2 && parts[0].equals(usernameToDelete)) {
                lines.remove(i);
                // Réécrire le fichier sans l'administrateur supprimé
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        }

        return false; // Administrateur non trouvé
    }
}
