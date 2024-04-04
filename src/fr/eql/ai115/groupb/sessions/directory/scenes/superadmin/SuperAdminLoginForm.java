package fr.eql.ai115.groupb.sessions.directory.scenes.superadmin;

import fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin.SuperAdminLoginHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SuperAdminLoginForm {
        private Scene scene;
        private Stage superAdminLoginStage;
        private TextField idField;
        private PasswordField pswdField;


        public Scene getScene() {return scene;}

        public Stage getSuperAdminLoginStage() {return superAdminLoginStage;}

        public SuperAdminLoginForm(Stage primaryStage) throws FileNotFoundException {

            //Login Form content
            Text adminTitle = new Text("Connectez-vous - Super-Administrateur");
            Label idLabel = new Label("Identifiant / email :");
            idField = new TextField();
            Label pswdLabel = new Label("Mot de passe :");
            pswdField = new PasswordField();
            Button loginButton = new Button("Connexion");
            Text actionText = new Text();

            // "Connexion" button action
            superAdminLoginStage = new Stage();
            SuperAdminLoginHandler superAdminLoginHandler = new SuperAdminLoginHandler(superAdminLoginStage,
                    idField, pswdField, actionText, primaryStage);
            loginButton.setOnAction(superAdminLoginHandler);

            // permet à l'utilisateur d'appuyer sur la touche entrée pour se connecter
            pswdField.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    superAdminLoginHandler.handle(new ActionEvent());
                    superAdminLoginStage.close();
                }
            });
            //CSS FILE ID
            adminTitle.setId("title");
            idLabel.setId("label");
            loginButton.setId("button");

            // Form Layout
            VBox mainVbox = new VBox();
            mainVbox.setSpacing(20);
            mainVbox.setPadding(new Insets(50));

            // Creating HBox for labels, textfields and button
            // label vbox
            VBox idBox = new VBox();
            idBox.setSpacing(20);
            idBox.getChildren().addAll(idLabel,idField);

            // field vbox
            VBox pswdBox = new VBox();
            pswdBox.setSpacing(20);
            pswdBox.getChildren().addAll(pswdLabel,pswdField);

            // button hbox
            HBox btnHbox = new HBox(10);
            btnHbox.setAlignment(Pos.BASELINE_LEFT);
            btnHbox.getChildren().add(loginButton);

            // Adding all elements to mainVBOX
            mainVbox.getChildren().addAll(adminTitle,idBox,pswdBox,btnHbox,actionText);


            // Scene layout
            scene = new Scene(mainVbox, 500, 400);
            superAdminLoginStage = new Stage();
            superAdminLoginStage.setX(primaryStage.getX()+200);
            superAdminLoginStage.setY(primaryStage.getY()+100);


            FileInputStream input = new FileInputStream("files/img/LogoEQL.png");
            Image icon = new Image(input);
            // Ajouter l'icône à la fenêtre principale
            superAdminLoginStage.getIcons().add(icon);
            // Showing Layout
            superAdminLoginStage.initModality(Modality.WINDOW_MODAL);
            superAdminLoginStage.initOwner(primaryStage);
            superAdminLoginStage.setScene(scene);
            superAdminLoginStage.setTitle("Formulaire de connexion super-administrateur");
            superAdminLoginStage.show();
            scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");
        }
}



