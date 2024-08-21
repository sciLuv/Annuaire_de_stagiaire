package fr.eql.ai115.groupc.sessions.directory.affichage;

import fr.eql.ai115.groupc.sessions.directory.traitment.BinaryTreatment;
import fr.eql.ai115.groupc.sessions.directory.traitment.ExportPDF;
import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static fr.eql.ai115.groupc.sessions.directory.traitment.Trainee.getTraineeList;
import static fr.eql.ai115.groupc.sessions.directory.traitment.FindTraineesList.listOfTrainee;

import fr.eql.ai115.groupc.sessions.directory.traitment.Trainee;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SearchWindow extends Application {

    private String traineeSelectedIntheTable;
    public static void main(String[] args) {
        launch(args);
    }


    ObservableList<Trainee> data;
    @Override
    public void start(Stage stage) {

        //Création du bouton aide
        AtomicReference<String> lastNameAndName = new AtomicReference<>("");
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
        //Mettre le bouton aide en haut à droite de la fenetre
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(btnAide);
        //Style du BorderPane
        borderPane.setStyle("-fx-background-color: #336699; -fx-padding: 10;");

        //Création du bouton retour
        Button btnRetour = new Button();
        btnRetour.setStyle("-fx-font-weight: bold; -fx-background-color: #336699; -fx-padding: 10; -fx-text-fill: white;-fx-font-size: 25px;");
        btnRetour.setText(" < Retour");
        //Mettre le bouton retour en haut à gauche de la fenetre
        borderPane.setLeft(btnRetour);

        //Retourner a la fenetre principale
        btnRetour.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                MainWindow.AllSelectedPromotions.promotionSelected.getChildren().removeAll(
                        MainWindow.AllSelectedPromotions.promotionSelected.getChildren()
                );
                MainWindow.setSelectedPromotionList(new ArrayList<>());
                MainWindow.isAllSelected = false;
                MainWindow.AllSelectedPromotions.promotionSelected.getChildren().add(new Label("Aucune promotion selectionnée."));

                MainWindow mainWindow = new MainWindow();
                MainWindow.lastName = null;
                MainWindow.name = null;
                mainWindow.start(stage);
            }
        });


        //Création de la table
        TableView<Trainee> table = new TableView<Trainee>();
        table.setEditable(true);




        //bloqué la taille de la table
        table.setMaxSize(1200, 500);


        Label label = new Label("Liste des stagiaires");
        label.setFont(new Font("Arial", 20));
        //Séparation du label et de la table
        label.setPadding(new Insets(20, 0, 20, 0));

        TableColumn<Trainee, String> formationCol = new TableColumn<Trainee, String>("Formation");
        formationCol.setMinWidth(200);
        //specifier un "cell value factory" pour cette colonne.
        formationCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("formation"));

        TableColumn<Trainee, String> promotionCol = new TableColumn<Trainee, String>("Promotion");
        promotionCol.setMinWidth(200);
        //specifier un "cell value factory" pour cette colonne.
        promotionCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("promotion"));

        TableColumn<Trainee, String> yearCol = new TableColumn<Trainee, String>("Année");
        yearCol.setMinWidth(200);
        //specifier un "cell value factory" pour cette colonne.
        yearCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("year"));

        TableColumn<Trainee, String> nomCol = new TableColumn<Trainee, String>("Nom");
        nomCol.setMinWidth(200);
        //spécifier une préférence de tri pour cette colonne
        nomCol.setSortType(TableColumn.SortType.ASCENDING);
        //specifier un "cell value factory" pour cette colonne.
        nomCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("lastName"));

        TableColumn<Trainee, String> prenomCol = new TableColumn<Trainee, String>("Prénom");
        prenomCol.setMinWidth(200);
        prenomCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("name"));


        //Création colonne Département
        TableColumn<Trainee, String> departementCol = new TableColumn<Trainee, String>("Département");
        departementCol.setMinWidth(100);
        //specifier un "cell value factory" pour cette colonne.
        departementCol.setCellValueFactory(new PropertyValueFactory<Trainee, String>("dept"));

        //On ajoute les trois colonnes à la table
        table.getColumns().addAll(formationCol, promotionCol, yearCol, nomCol, prenomCol, departementCol);

        ObservableList<Trainee> data = getTraineeList();
        table.setItems(data);

        data.addAll(listOfTrainee());

        HBox allBtn = new HBox();
        if(Admin.isAdmin){
            Button delete = new Button();
            delete.setText("Supprimer un stagiaire");
            delete.setStyle("-fx-background-color: red; -fx-padding: 10; -fx-font-size: 15px; -fx-text-fill: white;");
            delete.setOpacity(0.1);
            delete.setMinWidth(195);
            delete.setMaxWidth(195);

            Button modify = new Button();
            modify.setText("modifier un stagiaire");
            modify.setStyle("-fx-background-color: LightGreen; -fx-padding: 10; -fx-font-size: 15px;");
            modify.setOpacity(0.1);
            modify.setMinWidth(195);
            modify.setMaxWidth(195);
            allBtn.getChildren().addAll(modify, delete);

            delete.setMouseTransparent(true);
            modify.setMouseTransparent(true);
            
            
            delete.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                        //Pop up window to confirm the deletion
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de suppression");
                        alert.setHeaderText("Vous êtes sur le point de supprimer un stagiaire." );
                        alert.setContentText("Êtes-vous sûr de vouloir supprimer "+ lastNameAndName +"  ?");
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.OK){
                            BinaryTreatment bt = new BinaryTreatment();
                            bt.findAndDelete(traineeSelectedIntheTable);
                            SearchWindow searchWindow = new SearchWindow();
                            searchWindow.start(stage);
                        }

                }
            });

            modify.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
MainWindow mainWindow = new MainWindow();
                    AddTraineeWindow addTraineeWindow = new AddTraineeWindow(false, traineeSelectedIntheTable);
                    addTraineeWindow.setObservableList(mainWindow.getObservableList());
                    addTraineeWindow.start(stage);
                }
            });

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    lastNameAndName.set(newValue.getLastName() + " " + newValue.getName());
                    traineeSelectedIntheTable = newValue.getFormation() + " " +
                            newValue.getPromotion() + "/" + newValue.getYear() + "/" +
                            newValue.getLastName() + "/" + newValue.getName() + "/" +
                            newValue.getDept();

                    delete.setMouseTransparent(false);
                    modify.setMouseTransparent(false);
                    delete.setOpacity(1);
                    modify.setOpacity(1);
                    System.out.println(traineeSelectedIntheTable);
                }
            });
        }

        //bouton exporter PDF
        Button btnExportPDF = new Button();
        btnExportPDF.setStyle("-fx-background-color: LightBlue; -fx-padding: 10;");
        btnExportPDF.setText("Exporter en PDF");
        //Exporter le contenu de la table en PDF
        btnExportPDF.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                ExportPDF.generatePDF(data);
            }
        });

        //Mettre le bouton exporter en bas à droite de la table
        allBtn.getChildren().add(btnExportPDF);
        allBtn.setPadding(new Insets(10, 20, 10, 20));
        allBtn.setSpacing(25);
        allBtn.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);


        VBox vboxPane = new VBox();
        vboxPane.getChildren().add(borderPane);


        //On place le label et la table dans une VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, table);
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setSpacing(5);
        //centrer la vbox en fonction de la feneetre
        vbox.setAlignment(javafx.geometry.Pos.CENTER);


        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(vboxPane, vbox, allBtn);

        Scene scene = new Scene(mainBox);
        stage.setTitle("Annuaire de stagiaires");
        stage.setWidth(1250);
        stage.setHeight(780);
        stage.setScene(scene);
        stage.show();


    }

}
