package fr.eql.ai115.groupb.sessions.directory.scenes.admin;


import fr.eql.ai115.groupb.sessions.directory.components.menu.AdminMenu;
import fr.eql.ai115.groupb.sessions.directory.components.table.InternTable;
import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import fr.eql.ai115.groupb.sessions.directory.scenes.UserHome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AdminHome {
    private static AdminHome instance;
    private Scene scene;
    UserHome userHome = new UserHome();
    InternTable internTable = new InternTable();
    InternCatalog catalog = new InternCatalog(userHome.getSOURCE_FILE(), userHome.getBINARY_FILE());

    // Listes des promotions et des stagiaires
    private List<Intern> listInternAfficher = catalog.readMyBinaryFileForMyPromos(internTable.getComboBoxPromo().getValue());
    private ObservableList<Intern> data = FXCollections.observableArrayList(listInternAfficher);
    TableView<Intern> tableView = new TableView<>();




    public Scene getScene() {
        return scene;
    }


    public void setData() throws IOException {
        listInternAfficher = catalog.readMyBinaryFileForMyPromos(internTable.getComboBoxPromo().getValue());
        this.data = FXCollections.observableArrayList(listInternAfficher);
    }


    public AdminHome(Stage primaryStage) throws IOException {

        // Appeler la méthode createUserMenu pour créer le menu administrateur
        AdminMenu adminMenu = new AdminMenu();
        MenuBar menubar = adminMenu.createAdminMenu(primaryStage,this);

        // Ajouter la barre et le bouton de recherche TODO
        // Appeler les méthodes de la classe InternTable
        this.tableView = internTable.createTable();
        HBox searchBar = internTable.createSearchField(data, tableView);
        Button deleteIntern = internTable.createDeleteInternButton(tableView, data, internTable.getFilterField());
        Button addIntern = internTable.addIntern(primaryStage, data, tableView,internTable.getComboBoxPromo());
        Button modifyIntern = internTable.modifyIntern(primaryStage, data, tableView,internTable.getComboBoxPromo());


        VBox root = new VBox();

        //HBOX pour les boutons ajouter et supprimer
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(deleteIntern, addIntern, modifyIntern);

        // On met tous les élements dans une VBox root

        root.getChildren().addAll(menubar,searchBar,tableView,buttonBox);

        FileInputStream input = new FileInputStream("files/img/LogoEQL.png");
        Image icon = new Image(input);
        // Ajouter l'icône à la fenêtre principale
        primaryStage.getIcons().add(icon);

        scene = new Scene(root,1000,600);
        scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");


    }
}
