package fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin;

import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import fr.eql.ai115.groupb.sessions.directory.scenes.superadmin.SuperAdminHome;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SuperAdminLoginHandler implements EventHandler<ActionEvent> {

    private static final String CREDENTIALS_FILE = "files/superadminaccess.txt";
    private Stage superAdminLoginStage;
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


    public SuperAdminLoginHandler(Stage superAdminLoginStage, TextField idField,
                                  PasswordField pswdField, Text actionText,
                                  Stage primaryStage) {
        this.superAdminLoginStage = superAdminLoginStage;
        this.idField = idField;
        this.pswdField = pswdField;
        this.actionText = actionText;
        this.primaryStage = primaryStage;
        this.data = data;
        this.table = table;
        this.comboBox = comboBox;
        this.listPromo = listPromo;
        this.catalog = catalog;
        this.filterField = filterField;

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inputId = idField.getText();
        String inputPswd = pswdField.getText();

        // if id and pswd are correct then open admin home
        if (isAdmin(inputId, inputPswd)) {
            Platform.runLater(() -> {
                SuperAdminHome superAdminHome;
                try {
                    superAdminHome = new SuperAdminHome(primaryStage);
                    primaryStage.setScene(superAdminHome.getScene());
                    primaryStage.setTitle("Espace Super-Administrateur");
                    primaryStage.show();

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
                // L'utilisateur a appuyé sur le bouton "Réessayer", ne pas fermer la fenêtre de connexion
                return;
            }
        }

        // Fermer la fenêtre de connexion
        Object source = actionEvent.getSource();
        if (source instanceof Node) {
            Stage loginStage = (Stage) ((Node) source).getScene().getWindow();
            loginStage.close();
        }
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
