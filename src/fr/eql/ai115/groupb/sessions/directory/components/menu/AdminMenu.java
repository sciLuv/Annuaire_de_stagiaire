package fr.eql.ai115.groupb.sessions.directory.components.menu;


import fr.eql.ai115.groupb.sessions.directory.eventhandler.admin.UploadAdminDoc;
import fr.eql.ai115.groupb.sessions.directory.scenes.UserHome;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.AdminHome;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.ChangeIdForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AdminMenu extends Menu {

    public MenuBar createAdminMenu(Stage primaryStage, AdminHome adminHome) {
        MenuBar menuBar = new MenuBar();

        // Menu Administrateur
        Menu fileMenu = new Menu("Fichier");
        Menu myAccountMenu = new Menu("Mon espace");
        Menu helpMenu = new Menu("Aide");

        // MenuItems du menu fichier admin
        MenuItem importFileItem = new MenuItem("Importer un fichier");
        importFileItem.setDisable(true);
        // MenuItems du menu aide
        MenuItem adminDocItem = new MenuItem("Documentation Administrateur");

        //Ajouter l'action de userDocItem
        adminDocItem.setOnAction(new UploadAdminDoc(primaryStage));

        // MenuItems du menu mon espace admin
        MenuItem changeIdItem = new MenuItem("Modifier mes identifiants");
        changeIdItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Ouvrir la scène utilisateur
                ChangeIdForm changeIdForm = null;
                try {
                    changeIdForm = new ChangeIdForm(primaryStage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                changeIdForm.getModifyAdminStage().show();
            }
        });


        MenuItem logoutItem = new MenuItem("Se déconnecter");
        // Définir le gestionnaire d'événements pour le bouton "Se déconnecter"
        logoutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    UserHome userHome = new UserHome(primaryStage);
                    primaryStage.setScene(userHome.getScene());
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        // Ajouter les items au menu fichier
        fileMenu.getItems().addAll(importFileItem);

        // Ajouter les items au menu mon espace
        myAccountMenu.getItems().addAll(changeIdItem, logoutItem);

        // Ajouter les items au menu aide
        helpMenu.getItems().addAll(adminDocItem);

        // Ajouter les menus à la barre de menu
        menuBar.getMenus().addAll(fileMenu,myAccountMenu, helpMenu);
        menuBar.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");
        return menuBar;
    }
}
