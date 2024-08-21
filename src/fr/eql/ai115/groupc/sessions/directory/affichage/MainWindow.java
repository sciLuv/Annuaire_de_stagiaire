package fr.eql.ai115.groupc.sessions.directory.affichage;

import fr.eql.ai115.groupc.sessions.directory.traitment.BinaryTreatment;
import fr.eql.ai115.groupc.sessions.directory.traitment.ExportPDF;
import fr.eql.ai115.groupc.sessions.directory.traitment.Database;
import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import fr.eql.ai115.groupc.sessions.directory.affichage.promoSelection.PromotionSelection;
import fr.eql.ai115.groupc.sessions.directory.affichage.promoSelection.PromoButton;
import fr.eql.ai115.groupc.sessions.directory.traitment.Trainee;
import fr.eql.ai115.groupc.sessions.directory.binaryTree.Tree;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.eql.ai115.groupc.sessions.directory.traitment.FindTraineesList.listOfTrainee;


public class MainWindow extends Application {

    private String selectedFormation, selectedPromotion = "";
    public static String FormationAndPromotion;
    private static List<String> SelectedPromotionList = new ArrayList<>();

    public static boolean isAllSelected = false;
    public static PromotionSelection AllSelectedPromotions = new PromotionSelection(0);

    public static String name = null;
    public static String lastName = null;

    private static String errorText = "";
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public ObservableList<String> getObservableList() {
        return observableList;
    }

    public static List<String> getSelectedPromotionList() {
        return SelectedPromotionList;
    }

    public static void setSelectedPromotionList(List<String> selectedPromotionList) {
        SelectedPromotionList = selectedPromotionList;
    }



    public void start(Stage primaryStage) {
        Tree tree = new Tree();
        HBox topBar = new HBox();
        File isBinaryExist = new File("db\\binary.bin");
        Label errorlabel = new Label();
        ObservableList allSelectPromoButtonContainer = AllSelectedPromotions.promotionSelected.getChildren();
        topBar.setAlignment(Pos.BASELINE_RIGHT);
        topBar.setMinHeight(80);
        topBar.setMaxHeight(80);
        topBar.setStyle("-fx-background-color: #336699; -fx-padding : 10px" );
        // Création du bouton pour ouvrir l'onglet se connecter
        if(!Admin.isAdmin){
            Button btnOuvrir = new Button("Se connecter");
            btnOuvrir.setStyle("-fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");

            btnOuvrir.setOnMouseEntered(event -> {
                btnOuvrir.setStyle("-fx-background-color: #336699;  LightGray; -fx-padding: 10; -fx-text-fill: #9bb7d4;-fx-font-size: 25px;");
                btnOuvrir.setCursor(Cursor.HAND);
            });

            btnOuvrir.setOnMouseExited(event -> {
                btnOuvrir.setStyle("-fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
                btnOuvrir.setCursor(Cursor.DEFAULT);
            });

            btnOuvrir.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //afficher loginWindow
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.start(primaryStage);
                }
            });

            topBar.getChildren().add(btnOuvrir);

        }
        //condition for add some composants to the window for admin/Super user
        else {
            String adminOrSuperText = SuperAdmin.isSuperAdmin ? "Super-administrateur" : "Administrateur";

            //the menu and it label for a design trick (hover effect)
            Label labelMenu = new Label(adminOrSuperText);
            Menu menuAdmin = new Menu("", labelMenu);

            labelMenu.setStyle("-fx-text-fill: white;-fx-font-size: 25px;");

            //event on the "admin/superAdmin menu for hover effect on the cursor
            labelMenu.setOnMouseEntered(mouseEvent-> { labelMenu.setCursor(Cursor.HAND); });
            labelMenu.setOnMouseExited(event -> { labelMenu.setCursor(Cursor.DEFAULT); });

            //all admin menuItem
            MenuItem menuItemProfile = new MenuItem("Profil");
            MenuItem menuItemImport = new MenuItem("Importer");
            MenuItem menuItemLogout = new MenuItem("Se déconnecter");

            //add all menuItem to the menu
            menuAdmin.getItems().addAll(menuItemProfile, menuItemImport, menuItemLogout);


            menuItemProfile.setOnAction(e -> {


                AdminHomeWindow adminHomeWindow = new AdminHomeWindow();
                adminHomeWindow.start(primaryStage);
            });
            //import a binary file
            menuItemImport.setOnAction(e -> {
                System.out.println(SelectedPromotionList);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Ouvrir un fichier");
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                if (selectedFile != null) {
                    BinaryTreatment test = new BinaryTreatment();
                    test.binaryFileCreation();
                    test.textFileReading(selectedFile.getAbsolutePath());

                }

                MainWindow newWindow = new MainWindow();
                newWindow.start(primaryStage);
            });

            //to log out when a admin is connected
            menuItemLogout.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //afficher loginWindow dans la snene principale
                    MainWindow newWindow = new MainWindow();
                    Admin.isAdmin = false;
                    SuperAdmin.isSuperAdmin = false;
                    newWindow.start(primaryStage);
                }
            });

            //add administrator gestion to the menu if the user is the superAdmin
            MenuItem menuItemAdminGestion = new MenuItem("Gérer les administrateurs");
            if(SuperAdmin.isSuperAdmin) menuAdmin.getItems().add(menuItemAdminGestion);

            menuItemAdminGestion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SuperAdminWindow superAdminWindow = new SuperAdminWindow();
                    superAdminWindow.start(primaryStage);
                }
            });

            // Création de la barre de menu et ajout du menu principal
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().add(menuAdmin);

            menuBar.setPadding(new Insets(0,0, -15, 0));
            menuBar.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 25px;");
            topBar.getChildren().add(menuBar);
        }


        //Création du bouton aide
        Button btnAide = new Button();
        btnAide.setStyle("-fx-background-color: White; -fx-padding: 10; -fx-text-fill: Blue;-fx-font-size: 27px;");
        btnAide.setText("?");
        //Forme ronde du bouton
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
        // Création de la barre supérieure contenant le bouton "Se connecter"
        topBar.setSpacing(20);
        topBar.getChildren().add(btnAide);


        // Création du BorderPane pour mettre la barre supérieure à droite
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBar);
        borderPane.setMinHeight(650);



        //Création labels
        Label labelFormation = new Label("Liste des Formations");
        labelFormation.setPrefSize(200, 20);
        Label labelPromotion = new Label("Liste des Promotions");
        labelPromotion.setPrefSize(200, 20);



        List<String> formationArrayList = new ArrayList<>();


        if(isBinaryExist.exists()){
            tree.getAllFormation(formationArrayList);
        }else {
        }
        
        //Formation list and listView
        ListView<String> listFormation = new ListView<String>();


        //add Formation to the listview
        for (String s : formationArrayList) { observableList.add(s); }
        listFormation.getItems().addAll(observableList);

        //Promotion list and listView
        ListView<String> listPromotion = new ListView<String>();
        ObservableList<String> observableList2 = FXCollections.observableArrayList();

        listFormation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFormation = newValue;
            observableList2.clear();
            listPromotion.getItems().clear();
            List<String> promotionList = tree.getPromotionsFromFormation(new ArrayList<>(), newValue);
            observableList2.addAll(promotionList);
            listPromotion.getItems().addAll(promotionList);
        });

        listPromotion.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                selectedPromotion = newValue;
                String selectedFormationAndPromotion = selectedFormation + " " + selectedPromotion;
                if(!SelectedPromotionList.contains(selectedFormationAndPromotion)){
                    if(allSelectPromoButtonContainer.get(0).getClass().toString().contains("Label")){
                        allSelectPromoButtonContainer.remove(allSelectPromoButtonContainer.get(0));
                    }
                    SelectedPromotionList.add(selectedFormationAndPromotion);
                    allSelectPromoButtonContainer.add(new PromoButton(selectedFormationAndPromotion));
                }
                errorlabel.setText("");
            }
        });

        listFormation.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setOnMouseEntered(event -> setStyle("-fx-control-inner-background: lightgray;"));
                    setOnMouseExited(event -> setStyle(""));
                }
            }
        });
        listPromotion.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setOnMouseEntered(event -> setStyle("-fx-control-inner-background: lightgray;"));
                    setOnMouseExited(event -> setStyle(""));
                }
            }
        });

        //ajout des tableaux et labels à la fenêtre
        //Box de Formation
        VBox formationBox = new VBox();
        formationBox.setSpacing(10);
        formationBox.getChildren().addAll(labelFormation, listFormation);
        formationBox.setAlignment(Pos.TOP_CENTER);

        //Box de Promotion

        VBox promotionBox = new VBox();
        promotionBox.setSpacing(10);
        promotionBox.getChildren().addAll(labelPromotion,listPromotion);
        promotionBox.setAlignment(Pos.TOP_CENTER);


        Label traineeSearchLabel = new Label("Selectionner un stagiaire : ");
        traineeSearchLabel.setPadding(new Insets(5,8,0,0));


        TextField traineeNameSearchBar = new TextField();
        traineeNameSearchBar.setPromptText("Prénom");
        traineeNameSearchBar.setMinWidth(150);
        traineeNameSearchBar.setMaxWidth(150);
        traineeNameSearchBar.setMinHeight(30);
        traineeNameSearchBar.setMaxHeight(30);

        TextField traineeLastNameSearchBar = new TextField();
        traineeLastNameSearchBar.setPromptText("Nom");
        traineeLastNameSearchBar.setMinWidth(150);
        traineeLastNameSearchBar.setMaxWidth(150);
        traineeLastNameSearchBar.setMinHeight(30);
        traineeLastNameSearchBar.setMaxHeight(30);
        HBox traineeLastNameSearchBarContainer = new HBox();
        traineeLastNameSearchBarContainer.setPadding(new Insets(0,5,0,0));
        traineeLastNameSearchBarContainer.getChildren().add(traineeLastNameSearchBar);

        HBox traineeSearchContainer = new HBox();
        traineeSearchContainer.setPadding(new Insets(32,0,0,0));
        traineeSearchContainer.getChildren().addAll(traineeSearchLabel, traineeLastNameSearchBarContainer, traineeNameSearchBar);

        traineeNameSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")) name = null;
            else name = newValue;
        });
        traineeLastNameSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")) lastName = null;
            else lastName = newValue;
        });

        //checkbox to select all formation
        CheckBox selectAllFormation = new CheckBox("Selectionner toutes les Formations");
        selectAllFormation.setSelected(false);
        selectAllFormation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectAllFormation.isSelected()) {
                    // L'action à effectuer si la CheckBox est sélectionnée
                    System.out.println("CheckBox sélectionnée. Action effectuée !");
                    allSelectPromoButtonContainer.removeAll(allSelectPromoButtonContainer);

                    Label allPromotionSelectionLabel = new Label("Toutes les promotions sont sélectionnées !");
                    allPromotionSelectionLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    allPromotionSelectionLabel.setPadding(new Insets(20));
                    allSelectPromoButtonContainer.add(allPromotionSelectionLabel);
                    listFormation.setMouseTransparent(true);
                    listPromotion.setMouseTransparent(true);
                    listFormation.setOpacity(0.5);
                    listPromotion.setOpacity(0.5);


                    SelectedPromotionList.removeAll(SelectedPromotionList);
                    isAllSelected = true;
                    errorlabel.setText("");
                } else {
                    allSelectPromoButtonContainer.removeAll(allSelectPromoButtonContainer);
                    allSelectPromoButtonContainer.add(new Label("Aucune promotion selectionnée."));
                    listFormation.setMouseTransparent(false);
                    listPromotion.setMouseTransparent(false);
                    listFormation.setOpacity(1);
                    listPromotion.setOpacity(1);

                    isAllSelected = false;
                }
            }
        });
        HBox checkBoxContainer = new HBox(selectAllFormation);
        checkBoxContainer.setPadding(new Insets(10,0,10,0));
        //Mise en page de la recherche
        VBox rechercheBox = new VBox();
        rechercheBox.setSpacing(10);

        rechercheBox.getChildren().addAll(traineeSearchContainer, checkBoxContainer, AllSelectedPromotions);
        rechercheBox.setAlignment(Pos.TOP_CENTER);

        HBox btnBox = new HBox();

        if(Admin.isAdmin){
            Button btnAddTrainee = new Button("Ajouter un stagiaire");
            btnAddTrainee.setStyle("-fx-background-color: #00E0FF; -fx-padding: 10; -fx-font-size: 15px;");
            btnAddTrainee.setMinWidth(195);
            btnAddTrainee.setMaxWidth(195);
            btnBox.getChildren().add(btnAddTrainee);

            btnAddTrainee.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(!isBinaryExist.exists()){
                        errorText = "Action Impossible Il n'y a aucune formation, promotion ou stagiaire en mémoire pour le moment.";
                        errorlabel.setText(errorText);
                    }else {
                        AddTraineeWindow addTraineeWindow = new AddTraineeWindow();
                        addTraineeWindow.setObservableList((observableList));
                        addTraineeWindow.start(primaryStage);
                    }

                }
            });
        }


        //bouton exporter en PDF
        Button btnExporter = new Button("Exporter en PDF l'annuaire");
        btnExporter.setStyle("-fx-background-color: #3667C7; -fx-padding: 10; -fx-text-fill: white; -fx-font-size: 15px;");
        btnExporter.setMinWidth(195);
        btnExporter.setMaxWidth(195);
        btnExporter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isBinaryExist.exists()){
                    errorText = "Action Impossible Il n'y a aucune formation, promotion ou stagiaire en mémoire pour le moment.";
                    errorlabel.setText(errorText);
                }else {
                    MainWindow.isAllSelected = true;
                    List<Trainee> traineeList = listOfTrainee();
                    ExportPDF.generatePDF(traineeList);
                    MainWindow.isAllSelected = false;
                }
            }
        });

        //bouton recherche
        Button btnRecherche = new Button();
        btnRecherche.setText("Lancer la recherche");
        btnRecherche.setStyle("-fx-background-color: LightGreen; -fx-padding: 10; -fx-font-size: 15px;");
        btnRecherche.setMinWidth(195);
        btnRecherche.setMaxWidth(195);
        //ajout des boutons à la fenêtre

        btnBox.setSpacing(100);
        btnBox.getChildren().addAll(btnExporter, btnRecherche);
        btnBox.setAlignment(Pos.BOTTOM_CENTER);

        //Lien entre le bouton lancer la recherche et la fenêtre de recherche
        SearchWindow searchWindow = new SearchWindow();
        btnRecherche.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isBinaryExist.exists()){
                    errorText = "Action Impossible Il n'y a aucune formation, promotion ou stagiaire en mémoire pour le moment.";
                     errorlabel.setText(errorText);
                }else {
                    if(isAllSelected||!SelectedPromotionList.isEmpty()){

                        //afficher searchWindow dans la scene principale
                        searchWindow.start(primaryStage);
                    } else{
                        errorText = "Il faut sélectionner une ou plusieurs promotions avant de faire une recherche.";
                        errorlabel.setText(errorText);
                    }
                }

            }
        });

        //Mise en page de tout

        HBox mainHBox = new HBox();
        mainHBox.setSpacing(20);
        //écart entre topBar et mainBox
        mainHBox.setPadding(new Insets(10, 10, 10, 10));
        mainHBox.getChildren().addAll(formationBox, promotionBox, rechercheBox);
        //Centrer dans la fenêtre
        mainHBox.setAlignment(Pos.TOP_CENTER);
        //agrandir la police du texte
        mainHBox.setStyle("-fx-font-size: 15px;");

        borderPane.setCenter(mainHBox);


        errorlabel.setPadding(new Insets(20));
        errorlabel.setStyle("-fx-text-fill : red;");
        HBox errorContainer = new HBox(errorlabel);
        errorContainer.setAlignment(Pos.TOP_CENTER);
        VBox root = new VBox();
        root.getChildren().addAll(borderPane, btnBox, errorContainer);

        Scene scene = new Scene(root, 1250, 780);
        primaryStage.setTitle("Annuaire de stagiaires");
        primaryStage.setScene(scene);
        primaryStage.show();

        Database beginDb = new Database();
        try {
            beginDb.beginDb();
        } catch (IOException e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {

        launch(args);
    }
}
