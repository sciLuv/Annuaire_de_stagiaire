package fr.eql.ai115.groupb.sessions.directory.components.table;

import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.AddInternForm;
import fr.eql.ai115.groupb.sessions.directory.scenes.admin.ModifyInternForm;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InternTable {
    private static final String SOURCE_FILE = "stagiaires.txt";
    private static final String BINARY_FILE = "stagiaires.bin";
    InternCatalog catalog = new InternCatalog(SOURCE_FILE, BINARY_FILE);
    private ComboBox<String> comboBoxPromo = new ComboBox<>();
    private TextField filterField = new TextField();

    public TextField getFilterField() {
        return filterField;
    }

    public ComboBox<String> getComboBoxPromo() {
        return comboBoxPromo;
    }

    public List<String> getListPromo() {
        return listPromo;
    }

    List<String> listPromo = catalog.getListPromo().stream().sorted().collect(Collectors.toList());

    public InternTable() throws IOException {
    }

    public HBox createSearchField(ObservableList<Intern> data, TableView<Intern> table) {

        HBox hBox = new HBox();
        FilteredList<Intern> flPromos = new FilteredList(data, p -> true);
        table.setItems(flPromos);
        filterField.setPadding(new Insets(10,20,10,20));
        filterField.setPromptText("Chercher un stagiaire");
        comboBoxPromo = createComboBoxPromo(data, table, filterField);

        Button clearSearch = new Button("Effacer la recherche");
        clearSearch.setPadding(new Insets(10,30,10,30));

        // RAJOUT DE L'EVENEMENT POUR CLEAR LA TABLE
        clearSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                data.clear();
                comboBoxPromo.setValue(null);
            }
        });

        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(filterField, comboBoxPromo,clearSearch);
        return hBox;
    }

    // Création d'une méthode qui crée le tableau de stagiaires
    public TableView<Intern> createTable() {
        TableView<Intern> table = new TableView<Intern>();
        table.setEditable(true);

        //CREATION DES COLONNES DU TABLEAU
        // Nom
        TableColumn lastNameCol = new TableColumn("Nom");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Intern, String>("lastName"));

        // Prénom
        TableColumn firstNameCol = new TableColumn("Prénom");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Intern, String>("firstName"));

        // Promotion
        TableColumn promotionCol = new TableColumn("Promotion");
        promotionCol.setMinWidth(200);
        promotionCol.setCellValueFactory(
                new PropertyValueFactory<Intern, String>("Promotion"));

        // Année de promotion
        TableColumn yearsCol = new TableColumn("Année de la promotion");
        yearsCol.setMinWidth(250);
        yearsCol.setCellValueFactory(
                new PropertyValueFactory<Intern, Integer>("year"));

        // Département
        TableColumn departmentCol = new TableColumn("Département");
        departmentCol.setMinWidth(150);
        departmentCol.setCellValueFactory(
                new PropertyValueFactory<Intern, Integer>("department"));

        // Ajout de tous les éléments dans le tableau
        table.getColumns().addAll(lastNameCol, firstNameCol, promotionCol, departmentCol, yearsCol);

        return table;
    }

    // Méthode pour la sélection des promotions à rechercher
    public ComboBox<String> createComboBoxPromo
    (ObservableList<Intern> data, TableView<Intern> table, TextField filterField) {

//        ComboBox<String> comboBoxPromo = new ComboBox<>();
        this.comboBoxPromo.setPadding(new Insets(6));

        // Ajouter toute la liste des promotions dans la liste de sélection
        comboBoxPromo.getItems().addAll(listPromo);

        comboBoxPromo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {
            if (newVal != null) {
                if (listPromo.contains(comboBoxPromo.getValue())) {
                    try {
                        List<String> listPromosAfficher = data.stream().map(Intern::getPromotion).distinct().collect(Collectors.toList());
                        if (!listPromosAfficher.contains(comboBoxPromo.getValue())) {
                            data.addAll(catalog.readMyBinaryFileForMyPromos(comboBoxPromo.getValue()));
                            data.sort(Comparator.comparing(Intern::getStringComparator));
                            table.setItems(data);
                        }
                        //AJOUT DU FILTRE POUR LES NOMS
                        FilteredList<Intern> filteredData = new FilteredList<>(data, p -> true);
                        // RAJOUT DE L'EVENEMENT ET DU PREDICAT SUR LE TEXTFIELD
                        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                            String lowerCaseFilter = newValue.toLowerCase();
                            filteredData.setPredicate(myIntern ->
                                    myIntern.getLastName().toLowerCase().contains(lowerCaseFilter)
                            );
                        });

                        table.setItems(filteredData);


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return comboBoxPromo;
    }

    public Button createDeleteInternButton(
            TableView<Intern> table,
            ObservableList<Intern> data,
            TextField filterField) {
        Button deleteIntern = new Button("Supprimer ce stagiaire");
        deleteIntern.setPadding(new Insets(10,20,10,20));
        deleteIntern.setLineSpacing(10);

        deleteIntern.setOnAction(actionEvent -> {
            Intern selectedIntern = table.getSelectionModel().getSelectedItem();
            if (selectedIntern != null) {

                // Afficher la boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Voulez-vous vraiment supprimer ce stagiaire ?");
                alert.setContentText("Cette action est irréversible.");

                // Obtenir la réponse de l'utilisateur
                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

                // Si l'utilisateur a cliqué sur le bouton OK, supprimer le stagiaire
                if (result == ButtonType.OK) {
                    try {
                        catalog.deleteAnIntern(selectedIntern);
                        data.remove(selectedIntern);
                        table.setItems(data);


                        // Ajout du filtre pour les noms
                        FilteredList<Intern> filteredData = new FilteredList<>(data, p -> true);
                        // Rajout de l'événement et du prédicat sur le TextField
                        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                            String lowerCaseFilter = newValue.toLowerCase();
                            filteredData.setPredicate(myIntern ->
                                    myIntern.getLastName().toLowerCase().contains(lowerCaseFilter)
                            );
                        });

                        table.setItems(filteredData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return deleteIntern;
    }

    public Button addIntern(Stage primaryStage, ObservableList<Intern> data, TableView<Intern> table, ComboBox<String> comboBox ) throws IOException {
        Button addIntern = new Button("Ajouter un stagiaire");
        addIntern.setPadding(new Insets(10,30,10,30));


        addIntern.setOnAction(actionEvent -> {
            AddInternForm addForm;
            try {
                addForm = new AddInternForm(primaryStage, data, table, comboBox);
                Scene scene = addForm.getScene();
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return addIntern;
    }

    public Button modifyIntern(Stage primaryStage, ObservableList<Intern> data, TableView<Intern> table, ComboBox<String> comboBox ) throws IOException {
        Button modifyButton = new Button("Modifier un stagiaire");
        modifyButton.setPadding(new Insets(10,30,10,30));

        modifyButton.setOnAction(actionEvent -> {
            Intern selectedIntern = table.getSelectionModel().getSelectedItem();
            ModifyInternForm modifyForm;
            try {
                modifyForm = new ModifyInternForm(primaryStage, data, table, comboBox, selectedIntern);
                Scene scene = modifyForm.getScene();
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return modifyButton;
    }


}

