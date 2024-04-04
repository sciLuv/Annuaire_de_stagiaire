package fr.eql.ai115.groupb.sessions.directory.scenes;

import fr.eql.ai115.groupb.sessions.directory.components.menu.UserMenu;
import fr.eql.ai115.groupb.sessions.directory.components.table.InternTable;
import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class UserHome {
    private Scene scene;
    private final String SOURCE_FILE = "stagiaires.txt";
    private final String BINARY_FILE = "stagiaires.bin";

    public String getSOURCE_FILE() {
        return SOURCE_FILE;
    }

    public String getBINARY_FILE() {
        return BINARY_FILE;
    }

    InternCatalog catalog = new InternCatalog(SOURCE_FILE, BINARY_FILE);
    InternTable internTable = new InternTable();
    TableView<Intern> tableView = new TableView<>();

    public TableView<Intern> getTableView() {
        return tableView;
    }

    public void setListInternAfficher(Intern intern) {
        this.listInternAfficher.add(intern);
    }

    // Listes des promotions et des stagiaires
    private List<Intern> listInternAfficher = catalog.readMyBinaryFileForMyPromos(internTable.getComboBoxPromo().getValue());
    private ObservableList<Intern> data = FXCollections.observableArrayList(listInternAfficher);

    public List<Intern> getListInternAfficher() {
        return listInternAfficher;
    }

    public ObservableList<Intern> getData() {
        return data;
    }

    public Scene getScene() {
        return scene;
    }

    public void setData() throws IOException {
        listInternAfficher = catalog.readMyBinaryFileForMyPromos(internTable.getComboBoxPromo().getValue());
        this.data = FXCollections.observableArrayList(listInternAfficher);
    }

    public UserHome() throws IOException {
    }

    public UserHome(Stage primaryStage) throws IOException {
        // Appeler la méthode createUserMenu pour créer le menu utilisateur
        UserMenu userMenu = new UserMenu();
        MenuBar menubar = userMenu.createUserMenu(primaryStage);

        // Appeler les méthodes de la classe InternTable
        this.tableView = internTable.createTable();
        HBox searchBar = internTable.createSearchField(this.data,tableView);

//        System.out.println(listInternAfficher);

        // On met tous les élements dans une VBox root
        VBox root = new VBox();
        root.getChildren().addAll(menubar,searchBar,tableView);


        primaryStage.setTitle("Liste des promotions");

        FileInputStream input = new FileInputStream("files/img/LogoEQL.png");
        Image icon = new Image(input);
        // Ajouter l'icône à la fenêtre principale
        primaryStage.getIcons().add(icon);
        scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");

    }
}
