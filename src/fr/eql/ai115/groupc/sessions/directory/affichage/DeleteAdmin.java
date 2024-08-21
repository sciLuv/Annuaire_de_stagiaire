package fr.eql.ai115.groupc.sessions.directory.affichage;


import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeleteAdmin extends Application {
    public void start(Stage stage) {

        stage.setTitle("Annuaire de stagiaires");

        VBox vb = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Suppression d'un administrateur");

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

        //Create upper border
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Put the help button at the top right of the window
        borderPane.setRight(btnAide);

        //Create a button return
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold;-fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");

        //Put the return button at the top left of the window
        borderPane.setLeft(btnRetour);

        //Return to the SuperAdminWindow
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SuperAdminWindow superAdminWindow = new SuperAdminWindow();
                try {
                    superAdminWindow.start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Label for education
        Label educationLabel = new Label("Liste des administrateurs");
        educationLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));

        //Call the methos readAdminsFromFile to get the list of admins
        ObservableList<Admin> admins = Admin.readAdminsFromFile("db/database.txt");
        ListView<Admin> adminListView = new ListView<>(admins);

        grid.add(adminListView, 0, 4);


        //add the label and the list view to the grid

        grid.add(educationLabel,0,3);


        Label selectedAdmin = new Label("Administrateur(s) sélectionné(s) :");
        selectedAdmin.setPadding(new Insets(50));
        selectedAdmin.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        grid.add(selectedAdmin,1,4);
        selectedAdmin.setMinSize(500, 45);
        selectedAdmin.setMinSize(500, 45);
        //Select an admin and display it in the label
        adminListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Admin> ov, Admin old_val, Admin new_val) -> {
            selectedAdmin.setText("Administrateur(s) sélectionné(s) : " + new_val.getName());
        });
        Button btn = new Button("Supprimer l'administrateur");
        btn.setStyle("-fx-background-color: #336699; ");
        btn.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 7);
        grid.setAlignment(Pos.CENTER);


        btn.setOnAction(event -> {

            Admin admin = adminListView.getSelectionModel().getSelectedItem();
            if (admin == admins.get(0)) {
                selectedAdmin.setText("Vous ne pouvez pas supprimer le super administrateur.");
            } else

            if (admin != null) {
                //Pop up window to confirm the deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Vous êtes sur le point de supprimer l'administrateur " + admin.getName());
                alert.setContentText("Êtes-vous sûr de vouloir supprimer cet administrateur ?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK){
                    admins.remove(admin);
                selectedAdmin.setText("L'administrateur a bien été supprimé.");}
                else {
                    selectedAdmin.setText("L'administrateur n'a pas été supprimé.");}
                 //Create a new list of admins with distinct objects
                    ObservableList<Admin> updatedAdmins = FXCollections.observableArrayList();
                 for (Admin a : admins) {
                        updatedAdmins.add(new Admin(a.getName(), a.getPassword(), a.getType()));
                 }

                // Write the new list of admins in the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/database.txt"))) {
                    for (Admin a : updatedAdmins) {
                        writer.write(a.getName() + " " + a.getPassword() + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                selectedAdmin.setText("Veuillez sélectionner un administrateur.");
            }
        });

        vb.getChildren().addAll(borderPane,grid);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        Scene sceneDeleteAdmin = new Scene(vb, 1250, 780);
        stage.setScene(sceneDeleteAdmin);
        stage.show();

    }
}

