package fr.eql.ai115.groupc.sessions.directory.affichage;


import fr.eql.ai115.groupc.sessions.directory.traitment.Database;
import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginWindow{

    public void start(Stage primaryStage) {


        //Create fields
        Label idLabel = new Label("Identifiant");
        TextField idField = new TextField();


        //Reduce the size of the id field
        idField.setMaxWidth(500);
        idField.setStyle("-fx-background-color: White; -fx-padding: 10; -fx-font-size: 20px;");
        idField.setPromptText("Identifiant");
        Label passwordLabel = new Label("Mot de passe");
        passwordLabel.setPadding(new Insets(30, 0, 0, 0));
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: White; -fx-margin-bottom : 20px; -fx-padding: 10; -fx-font-size: 20px;");

        //Reduce the size of the password field
        passwordField.setMaxWidth(500);
        passwordField.setPromptText("Mot de passe");

        //Vbox to organize the elements
        VBox vBoxMain = new VBox();
        vBoxMain.setSpacing(10);
        vBoxMain.setPadding(new Insets(100, 10, 10, 10));

        //Center the VBox
        vBoxMain.setAlignment(javafx.geometry.Pos.CENTER);

        //Create a button confirm
        Button btnConnexion = new Button("Se connecter");
        btnConnexion.setStyle("-fx-background-color: #3667C7; -fx-padding: 10; -fx-text-fill: white; -fx-font-size: 15px;");
        btnConnexion.setMinWidth(195);
        btnConnexion.setMaxWidth(195);
        HBox btnConnexionContainer = new HBox(btnConnexion);
        btnConnexionContainer.setPadding(new Insets(40,0,0,0));
        btnConnexionContainer.setAlignment(javafx.geometry.Pos.CENTER);

        Label connexionError = new Label();
        connexionError.setStyle("-fx-text-fill: red;");
        vBoxMain.getChildren().addAll(idLabel, idField, passwordLabel, passwordField, btnConnexionContainer, connexionError);

        //Create a button help
        Button btnAide = new Button();
        btnAide.setStyle("-fx-background-color: White; -fx-padding: 10; -fx-text-fill: Blue;-fx-font-size: 27px;");
        btnAide.setText("?");

        //Set the shape of the button
        btnAide.setShape(new Circle(2));
        btnAide.setMinSize(60, 60);
        btnAide.setMaxSize(60, 60);

        btnAide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try
                {
                    String filename = "user_doc\\Documentation_App_Utilisateur.pdf";
                    if(Admin.isAdmin) filename = "user_doc\\Documentation_App_Administrateur.pdf";
                    if(SuperAdmin.isSuperAdmin) filename = "user_doc\\Documentation_App_SuperAdministrateur.pdf";
                    File file = new File(filename);
                    if(!Desktop.isDesktopSupported())
                    {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()) desktop.open(file);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        //Put the help button at the top right of the window
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(btnAide);

        //Style of the BorderPane
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Create a button return
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold; -fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");

        //Put the button return at the top left of the window
        borderPane.setLeft(btnRetour);

        //Return to the MainWindow
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainWindow mainWindow = new MainWindow();
                mainWindow.start(primaryStage);
            }
        });


        btnConnexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("test");
                Database db = new Database();
                try {
                    System.out.println("test2");
                    int test = db.search(idField.getText() + " " + passwordField.getText());
                    if (test == 0){
                       Admin.isAdmin = true;
                        SuperAdmin.isSuperAdmin = true;
                    } else if (test == 1){
                        Admin.isAdmin = true;
                    }
                    Admin.adminConnected = idField.getText();
                    if(test != 2){
                        MainWindow mainWindow = new MainWindow();
                        mainWindow.start(primaryStage);
                    } else {
                        connexionError.setText("Il y a une erreur dans votre pair identifiant/mot de passe");
                    }

                    System.out.println(Admin.isAdmin);
                    System.out.println(test);
                }


                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        VBox root = new VBox();

        Scene scene = new Scene(root,1250, 780);
        root.getChildren().add(borderPane);
        root.getChildren().add(vBoxMain);

        primaryStage.setTitle("Annuaire de stagiaires");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}