package fr.eql.ai115.groupc.sessions.directory.affichage;


import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class ConfirmDeleteWindow {
    public void start(Stage stage) {
        stage.setTitle("Super Administrateur");

        VBox vb = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Validation de la Suppression d'un administrateur");

        sceneTitle.setTextAlignment(TextAlignment.CENTER);
        sceneTitle.setFont(Font.font("", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 1, 2, 1);

        final Label message = new Label("");
        message.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));

        grid.add(message, 0, 2);


        //Create a button help
        Button btnAide = new Button();
        btnAide.setStyle("-fx-background-color: White; -fx-padding: 10; -fx-text-fill: Blue;-fx-font-size: 27px;");
        btnAide.setText("?");

        //Set the shape of the button help
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

        //Create a borderpane for the buttons help and return
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Put the help button at the top right of the window
        borderPane.setRight(btnAide);

        //Create a button return
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold;-fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");
        //Mettre le bouton retour en haut à gauche de la fenetre
        borderPane.setLeft(btnRetour);
        //retourner à la deleteAdmin
        btnRetour.setOnAction(e -> {
            try {
                new DeleteAdmin().start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Label questionLabel = new Label("Voulez-vous vraiment supprimer le profil de :\n" /*+ admin à supprimer*/ );
        questionLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(questionLabel, 0, 2);

        Label confirmSupp = new Label("Si oui, saisissez votre mot de passe puis validez la suppression.");
        confirmSupp.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(confirmSupp, 0, 4);


        Label passwordLabel = new Label("Votre mot de passe");
        passwordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(passwordLabel, 0, 6);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField,0,7);
        Button btn = new Button("Supprimer l'administrateur");
        btn.setStyle("-fx-background-color: #336699; ");
        btn.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 8 );
        grid.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(borderPane,grid);

        Scene scene = new Scene(vb, 1250, 780);
        stage.setScene(scene);
        stage.setTitle("Annuaire de stagiaires");
        stage.show();
    }
    }
