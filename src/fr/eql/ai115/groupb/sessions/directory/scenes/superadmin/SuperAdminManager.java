package fr.eql.ai115.groupb.sessions.directory.scenes.superadmin;

import fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin.AdminManagerDeleteHandler;
import fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin.AdminManagerHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SuperAdminManager {
    private Scene scene;
    private Stage superAdminManagerStage;
    private TextField idField;
    private PasswordField pswdField;

    public Scene getScene() {
        return scene;
    }

    public Stage getSuperAdminManagerStage() {
        return superAdminManagerStage;
    }

    public SuperAdminManager(Stage primaryStage) {
        //Login Form content
        Text adminTitle = new Text("Création et suppression de compte Administrateur !");
        Label idLabel = new Label("Identifiant / email actuel :");
        idField = new TextField();
        Label pswdLabel = new Label("Mot de passe actuel :");
        pswdField = new PasswordField();
        Button validButton = new Button("Ajouter");
        Text actionText = new Text();
        Button closeButton = new Button("Supprimer");
        Text closeText = new Text();

        // "Connexion" button action
        superAdminManagerStage = new Stage();
        AdminManagerHandler adminManagerHandler = new AdminManagerHandler(superAdminManagerStage,
                idField, pswdField, actionText, primaryStage);
        validButton.setOnAction(adminManagerHandler);
        AdminManagerDeleteHandler adminManagerDeleteHandler = new AdminManagerDeleteHandler(superAdminManagerStage,
                idField, pswdField, actionText, primaryStage);
        closeButton.setOnAction(adminManagerDeleteHandler);




        //CSS FILE ID
        adminTitle.setId("title");
        idLabel.setId("label");
        pswdLabel.setId("Label");
        validButton.setId("button");
        closeButton.setId("button");

        // Form Layout
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setPadding(new Insets(50));

        // Creating HBox for labels, textfields and button
        // label vbox
        VBox idBox = new VBox();
        idBox.setSpacing(20);
        idBox.getChildren().addAll(idLabel, idField);

        // field vbox
        VBox pswdBox = new VBox();
        pswdBox.setSpacing(20);
        pswdBox.getChildren().addAll(pswdLabel, pswdField);

        // button hbox
        HBox btnHbox = new HBox(10);
        btnHbox.setAlignment(Pos.BASELINE_LEFT);
        btnHbox.getChildren().add(validButton);
        btnHbox.setAlignment(Pos.BASELINE_RIGHT);
        btnHbox.getChildren().add(closeButton);

        // Adding all elements to mainVBOX
        mainVbox.getChildren().addAll(adminTitle, idBox, pswdBox, btnHbox, actionText, closeText);


        // Scene layout
        scene = new Scene(mainVbox, 500, 400);
        superAdminManagerStage = new Stage();
        superAdminManagerStage.setX(primaryStage.getX() + 200);
        superAdminManagerStage.setY(primaryStage.getY() + 100);

        // Showing Layout
        superAdminManagerStage.initModality(Modality.WINDOW_MODAL);
        superAdminManagerStage.initOwner(primaryStage);
        superAdminManagerStage.setScene(scene);
        superAdminManagerStage.setTitle("Ajout/Suppression de compte administrateur");
        superAdminManagerStage.show();
        scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");
    }
}

