package fr.eql.ai115.groupc.sessions.directory.affichage;

import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class ModifPasswordAdmin extends Application {
    public void start(Stage stage) {
    VBox vb = new VBox();

    GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(70);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

    Text sceneTitle = new Text("Modification du mot de passe");

        sceneTitle.setTextAlignment(TextAlignment.CENTER);
        sceneTitle.setFont(Font.font("", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 1, 2, 1);

    final Label message = new Label("\r\n");
        message.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));




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
        //Create a borderpane for the buttons help and return
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Put the help button at the top right of the windowa
        borderPane.setRight(btnAide);

        //Create a button return
    Button btnRetour = new Button();
        btnRetour.setStyle("-fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");

        //return to the AdminHomeWindow
        btnRetour.setOnAction(e -> {
            AdminHomeWindow adminHomeWindow = new AdminHomeWindow();
            try {
                adminHomeWindow.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //Put the return button at the top left of the window
        borderPane.setLeft(btnRetour);


    Label userName = new Label("Votre mot de passe actuel:");
        userName.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(userName, 0, 3);

    PasswordField adminPWField = new PasswordField();
        grid.add(adminPWField, 1, 3);

    Label newId = new Label("Votre nouveau mot de passe:");
        newId.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(newId, 0, 4);

    PasswordField newAdminPW = new PasswordField();
        grid.add(newAdminPW, 1, 4);

    Label pwConfirmLbl = new Label("Confirmer le nouveau mot de passe:");
        pwConfirmLbl.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(pwConfirmLbl, 0, 5);

    PasswordField pwConfirm = new PasswordField();
        grid.add(pwConfirm, 1, 5);

    Button btn = new Button("Changer de mot de passe");
        btn.setStyle("-fx-background-color: #336699; ");
        btn.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
    HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 7);
        grid.setAlignment(Pos.CENTER);
        VBox messageVbox = new VBox();
        messageVbox.setAlignment(Pos.CENTER);
        messageVbox.getChildren().add(message);

        btn.setOnAction(e -> {
            String currentPassword = adminPWField.getText();
            String newPassword = newAdminPW.getText();
            String confirmPassword = pwConfirm.getText();

            //Use the list of admins to find the current admin with adminConnected
            ObservableList<Admin> adminList = Admin.readAdminsFromFile("db/database.txt");
            Admin currentAdmin = null;
            for (Admin admin : adminList) {
                if (admin.getName().equals(Admin.adminConnected)) {
                    currentAdmin = admin;
                    break;
                }
            }

            //Verify if the admin exists and if the current password is correct
            if (currentAdmin != null && currentPassword.equals(currentAdmin.getPassword())) {
                //Verify if the new password and the confirmation match
                if (newPassword.equals(confirmPassword)) {
                    //Do the modification of the password
                    currentAdmin.setPassword(newPassword);

                    //Save the modifications in the file
                    Admin.writeAdminsToFile("db/database.txt", adminList);

                    message.setText("\r\nMot de passe modifié avec succès.");
                    message.setStyle("-fx-text-fill: green;");
                } else {
                    message.setText("\r\nLe nouveau mot de passe et la confirmation ne correspondent pas.");
                    message.setStyle("-fx-text-fill: red;");

                }
            } else {
                message.setText("\r\nL'administrateur avec l'identifiant actuel ou le mot de passe actuel est incorrect.");
                message.setStyle("-fx-text-fill: red;");
            }
        });


        vb.getChildren().addAll(borderPane,messageVbox,grid);

    final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);


        Scene sceneModifPW = new Scene(vb, 1250, 780);
        stage.setScene(sceneModifPW);
        stage.setTitle("Annuaire de stagiaires");
        stage.show();
    
}}
