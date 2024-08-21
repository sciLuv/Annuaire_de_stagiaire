package fr.eql.ai115.groupc.sessions.directory.affichage;


import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class SuperAdminWindow {
    public void start(Stage stage) {


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);
        grid.setVgap(50);
        grid.setPadding(new Insets(100, 100, 100, 100));

        //Create a button to create an admin account
        Button createBTN = new Button("Créer un administrateur");
        createBTN.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        createBTN.setTextAlignment(TextAlignment.CENTER);
        createBTN.setMaxWidth(400);

        //Go to the page to create an admin account
        createBTN.setOnAction(e -> {
            CreateAdmin createAdmin = new CreateAdmin();
            try {
                createAdmin.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        grid.add(createBTN, 1, 1);
        grid.setAlignment(Pos.CENTER);

        //Create a button to delete an admin account
        Button suppBtn = new Button("Supprimer un administrateur");
        suppBtn.setTextAlignment(TextAlignment.CENTER);
        suppBtn.setMaxWidth(400);
        //Go to the page to delete an admin account
        suppBtn.setOnAction(e -> {
            DeleteAdmin deleteAdmin = new DeleteAdmin();
            try {
                deleteAdmin.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        suppBtn.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));

        grid.add(suppBtn, 1, 2);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);


        //Create a button return
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold; -fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");

        //Create a borderpane for the buttons help and return
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Put the return button at the top left of the window
        borderPane.setLeft(btnRetour);
        //return to the main window
        btnRetour.setOnAction(e -> {
            MainWindow mainWindow = new MainWindow();
            try {
                mainWindow.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        //Create a grid for the buttons for the interfaces
        VBox vbox = new VBox();
        //Assembly of the borderpane and the grid
        vbox.getChildren().addAll(borderPane,grid);

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
        borderPane.setRight(btnAide);

        VBox root = new VBox();
        root.getChildren().addAll(borderPane,grid);

        Scene scene = new Scene(root,1250, 780);

        stage.setTitle("Annuaire de stagiaires");
        stage.setScene(scene);
        stage.show();

    }}
