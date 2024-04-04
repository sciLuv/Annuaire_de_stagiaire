package fr.eql.ai115.groupb.sessions.directory.eventhandler.admin;

import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.AdminHome;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.util.List;
import java.util.Optional;

// Gestionnaire d'événement qui ouvre la scène administrateur

public class AdminLoginHandler implements EventHandler<ActionEvent> {

    private static final String CREDENTIALS_FILE = "files/adminaccess.txt";
    private Stage loginStage;
    private TextField idField;
    private PasswordField pswdField;
    private Text actionText;
    private Stage primaryStage;
    private ObservableList<Intern> data;
    private TableView<Intern> table;
    private ComboBox<Intern> comboBox;
    private List<String> listPromo;
    private InternCatalog catalog;
    private TextField filterField;


    public AdminLoginHandler(Stage loginStage, TextField idField,
                             PasswordField pswdField, Text actionText,
                             Stage primaryStage) {
        this.loginStage = loginStage;
        this.idField = idField;
        this.pswdField = pswdField;
        this.actionText = actionText;
        this.primaryStage = primaryStage;


    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inputId = idField.getText();
        String inputPswd = pswdField.getText();

        // if id and pswd are correct then open admin home
        if (isAdmin(inputId, inputPswd)) {
            Platform.runLater(() -> {
                AdminHome adminHome;
                try {
                    adminHome = new AdminHome(primaryStage);
                    primaryStage.setScene(adminHome.getScene());
                    primaryStage.setTitle("Espace Administrateur");
                    primaryStage.show();
                    loginStage.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        } else {
            // Affiche une boite de dialogue en cas de mauvais mdp/id
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Identifiant/mot de passe incorrect(s).");
            // Ajouter un bouton "Réessayer"
            ButtonType retryButton = new ButtonType("Réessayer", ButtonBar.ButtonData.APPLY);
            alert.getButtonTypes().add(retryButton);

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == retryButton) {
            }

        }

        // Fermer la fenêtre de connexion

    }


    private boolean isAdmin(String admin, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[1].trim();

                    if (storedUsername.equals(admin) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
        return false;
    }
}