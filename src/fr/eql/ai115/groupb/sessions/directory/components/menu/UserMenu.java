package fr.eql.ai115.groupb.sessions.directory.components.menu;

/**
 * User Menu available for simple users
 * @cynthia
 *
 */

import fr.eql.ai115.groupb.sessions.directory.eventhandler.UploadUserDoc;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.AdminLoginForm;
import fr.eql.ai115.groupb.sessions.directory.scenes.superadmin.SuperAdminLoginForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class UserMenu extends Menu {

    public MenuBar createUserMenu(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // Menu Utilisateur
        Menu account = new Menu("Espace administrateur");
        Menu helpMenu = new Menu("Aide");

        // MenuItems du menu utilisateur
        MenuItem adminLoginItem = new MenuItem("Connexion administrateur");
        MenuItem superAdminLoginItem = new MenuItem("Connexion super-administrateur");
        MenuItem userDocItem = new MenuItem("Documentation Utilisateur");

        //Ajouter l'action de userDocItem
        userDocItem.setOnAction(new UploadUserDoc(primaryStage));

        // Ajouter une action à login
        adminLoginItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AdminLoginForm adminLoginForm = null;
                try {
                    adminLoginForm = new AdminLoginForm(primaryStage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                adminLoginForm.getLoginStage().show();
            }
        });

        // Ajouter une action a login super admin
        // Ajouter une action à login
        superAdminLoginItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SuperAdminLoginForm superAdminLoginForm = null;
                try {
                    superAdminLoginForm = new SuperAdminLoginForm(primaryStage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                superAdminLoginForm.getSuperAdminLoginStage().show();
            }
        });

        // Ajouter les items au menu aide
        account.getItems().addAll(adminLoginItem,superAdminLoginItem);
        helpMenu.getItems().addAll(userDocItem);

        // Ajouter les menus à la barre de menu
        menuBar.getMenus().addAll(account, helpMenu);
        menuBar.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");

        return menuBar;

    }


}
