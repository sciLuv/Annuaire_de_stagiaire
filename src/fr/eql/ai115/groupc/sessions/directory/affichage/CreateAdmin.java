package fr.eql.ai115.groupc.sessions.directory.affichage;


import fr.eql.ai115.groupc.sessions.directory.traitment.Database;
import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CreateAdmin {
    public void start(Stage stage) {
        stage.setTitle("Super Administrateur");

        VBox vb = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(70);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Création d'administrateur");

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

        Label userName = new Label("Identifiant de l'administrateur:");
        userName.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(userName, 0, 3);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 3);

        Label pw = new Label("Mot de passe de l'administrateur:");
        pw.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(pw, 0, 4);

        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 4);

        Label pwConfirmLbl = new Label("Confirmer le mot de passe:");
        pwConfirmLbl.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(pwConfirmLbl, 0, 5);

        PasswordField passwordConfirm = new PasswordField();
        grid.add(passwordConfirm, 1, 5);

        Button btn = new Button("Créer l'administrateur");
        btn.setStyle("-fx-background-color: #336699; ");
        btn.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 7);
        grid.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(borderPane,grid);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        String adminUsername = "admin";
        String adminPassword = "admin";
        String adminPasswordConfirm = "admin";

        SuperAdmin superAdmin = new SuperAdmin();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                {
                    Admin admin = new Admin();

                    String givenName;
                    String pw;
                    String pwConfirm;
                    ObservableList<Admin> adminList = Admin.readAdminsFromFile("db/database.txt");


                    Admin currentAdmin = null;
                    boolean isIdAlreadyInDb = false;
                    do {
                        //Sets the name of the admin, checks for empty and too long username, and if the username is already taken
                        if (userTextField.getText().isEmpty()) {
                            message.setText("Veuillez entrer un nom d'utilisateur");
                            message.setTextFill(Color.rgb(210, 39, 30));
                            //Checks if the username is already taken in the database
                        } else if (userTextField.getText().length() > 15) {
                            message.setText("L'identifiant doit faire moins de 15 caractères");
                        }else {
                            givenName = userTextField.getText().toString();
                            for (Admin admin1 : adminList) {
                                if (admin1.getName().equals(givenName)) {
                                    isIdAlreadyInDb = true;
                                    break;
                                }
                            }
                            if (isIdAlreadyInDb) {
                                message.setText("L'identifiant est déjà pris");
                                message.setTextFill(Color.rgb(210, 39, 30));
                            } else {
                                admin.setName(userTextField.getText().toString());
                            }
                        }


                        //Sets the password of the admin, checks for empty and too long password
                        if (passwordBox.getText().isEmpty()) {
                            message.setText("Veuillez entrer un mot de passe");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        } else if (passwordBox.getText().length() > 15) {
                            message.setText("Le mot de passe doit faire moins de 15 caractères");
                        } else {
                            pw = passwordBox.getText().toString();
                        }

                        if (passwordConfirm.getText().isEmpty()) {
                            message.setText("Veuillez confirmer le mot de passe");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        } else if (passwordConfirm.getText().length() > 15) {
                            message.setText("Le mot de passe doit faire moins de 15 caractères");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        } else {
                            pwConfirm = passwordConfirm.getText().toString();
                        }

                        //Checks equality of password and confirmation, sets password if both are the same
                        if (!passwordBox.getText().equals(passwordConfirm.getText())) {
                            message.setText("Les mot de passe ne correspondent pas");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        } else if (passwordBox.getText().equals(passwordConfirm.getText())) {
                            admin.setPassword(passwordBox.getText().toString());
                        }

                        if (!admin.getName().isEmpty() && !admin.getPassword().isEmpty()) {
                            //Writes the admin in the database, displays a message to inform the user of the completion
                            Database db = new Database();
                            try {
                                db.search(admin.getName());
                                //if (admin.getName().)
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            db.dbWrite(admin.getName() + " " + admin.getPassword());
                            message.setText("Compte administrateur créé avec succès");
                            message.setTextFill(Color.rgb(21, 117, 84));
                            //clear the textfields
                            userTextField.clear();
                            passwordBox.clear();
                            passwordConfirm.clear();
                        } else {
                            message.setText("Une erreur s'est produite, veuillez recommencer");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        }
                    }while (admin.toString().isEmpty());
                }
            }
        });

        Scene sceneCreateAdmin = new Scene(vb, 1250, 780);
        stage.setScene(sceneCreateAdmin);
        stage.show();

        //Create a gridpane for the interfaces
        VBox vbox = new VBox();

        //Assembly the structure of the interface
        vbox.getChildren().addAll(borderPane,grid);

        //Put the help button at the top right of the window
        borderPane.setRight(btnAide);

        //Create a button return
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold; -fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");

        //Put the return button at the top left of the window
        borderPane.setLeft(btnRetour);

        //Return to the SuperHomeWindow
        btnRetour.setOnAction(e -> {
            SuperAdminWindow superHomeWindow = new SuperAdminWindow();
            try {
                superHomeWindow.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(borderPane,grid);

        Scene scene = new Scene(root,1250, 780);


        stage.setTitle("Annuaire de stagiaires");
        stage.setScene(scene);
        stage.show();

    }}
