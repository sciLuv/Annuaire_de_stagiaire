package fr.eql.ai115.groupc.sessions.directory.affichage;

import fr.eql.ai115.groupc.sessions.directory.binaryTree.Tree;
import fr.eql.ai115.groupc.sessions.directory.traitment.BinaryTreatment;
import fr.eql.ai115.groupc.sessions.directory.traitment.Trainee;
import fr.eql.ai115.groupc.sessions.directory.user.Admin;
import fr.eql.ai115.groupc.sessions.directory.user.SuperAdmin;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.*;


public class AddTraineeWindow extends Application {

    private String labelText = "Ajouter un stagiaire";
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private boolean isAdd = true;
    private String traineeToDelete = null;

    private TextField formationTextField = new TextField();
    private TextField yearTextField = new TextField();
    private TextField lastNameTextField = new TextField();
    private TextField nameTextField = new TextField();
    private TextField deptTextField = new TextField();

    public AddTraineeWindow(){}
    public AddTraineeWindow(boolean isAdd, String traineeToDelete){
        this.isAdd = isAdd;
        if(this.isAdd == false){
            labelText = "modifier un stagiaire";
            this.traineeToDelete = traineeToDelete;
            System.out.println(traineeToDelete);
            BinaryTreatment bt = new BinaryTreatment();
            Trainee trainee = bt.traineeFactory(traineeToDelete);
            formationTextField.setText(trainee.getFormation() + " " + trainee.getPromotion());
            yearTextField.setText(trainee.getYear());
            lastNameTextField.setText(trainee.getLastName());
            nameTextField.setText(trainee.getName());
            deptTextField.setText(trainee.getDept());
        }

    }

    public void setObservableList(ObservableList<String> observableList) {
        this.observableList = observableList;
    }

    @Override
    public void start(Stage stage){

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
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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

        Label oldFormationSelection = new Label("Sélectionnez une formation existante.");
        MainWindow mw = new MainWindow();
        Tree tree = new Tree();
        ComboBox<String> formationComboBox = new ComboBox<>();
        formationComboBox.setItems(observableList);
        formationComboBox.setMaxWidth(100);
        formationComboBox.setMinWidth(100);

        ComboBox<String> promotionComboBox = new ComboBox<>();
        ObservableList<String> promotionObservableList = FXCollections.observableArrayList();
        promotionComboBox.setItems(promotionObservableList);
        promotionComboBox.setMaxWidth(70);
        promotionComboBox.setMinWidth(70);
        promotionComboBox.setDisable(true);

        HBox selectFormationAndPromotionContainer = new HBox();
        selectFormationAndPromotionContainer.setSpacing(10);
        selectFormationAndPromotionContainer.getChildren().addAll(oldFormationSelection,formationComboBox,promotionComboBox);
        selectFormationAndPromotionContainer.setAlignment(Pos.CENTER);
        selectFormationAndPromotionContainer.setPadding(new Insets(50,5,5,5));


        formationComboBox.setOnAction(event -> {
            promotionComboBox.getItems().clear();
            String selectedValue = formationComboBox.getSelectionModel().getSelectedItem();
            promotionObservableList.addAll(tree.getPromotionsFromFormation(new ArrayList<>(), selectedValue));
            promotionComboBox.setDisable(false);
        });

        formationComboBox.setOnMouseClicked(event -> {
            formationTextField.setText("");

        });


        formationTextField.setOnMouseClicked(event -> {
            promotionComboBox.getSelectionModel().select(null);
            formationComboBox.getSelectionModel().select(null);
            promotionComboBox.setDisable(true);
        });


        Label formationLabel = new Label("Ajoutez une nouvelle formation");
        HBox formationContainer = new HBox(5);
        formationContainer.getChildren().addAll(formationLabel, formationTextField);
        formationContainer.setAlignment(Pos.CENTER);
        formationContainer.setPadding(new Insets(5));
        formationContainer.setSpacing(40);

        Label yearLabel = new Label("Année");
        HBox yearContainer = new HBox(5);
        yearContainer.getChildren().addAll(yearLabel, yearTextField);
        yearContainer.setAlignment(Pos.CENTER);
        yearContainer.setPadding(new Insets(5));
        yearContainer.setSpacing(205);
        yearTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label lastNameLabel = new Label("Nom");
        HBox lastNameContainer = new HBox(5);
        lastNameContainer.getChildren().addAll(lastNameLabel, lastNameTextField);
        lastNameContainer.setAlignment(Pos.CENTER);
        lastNameContainer.setPadding(new Insets(5));
        lastNameContainer.setSpacing(215);

        Label nameLabel = new Label("Prénom");
        HBox nameContainer = new HBox(5);
        nameContainer.getChildren().addAll(nameLabel, nameTextField);
        nameContainer.setAlignment(Pos.CENTER);
        nameContainer.setPadding(new Insets(5));
        nameContainer.setSpacing(195);

        Label deptLabel = new Label("Département");
        HBox deptContainer = new HBox(5);
        deptContainer.getChildren().addAll(deptLabel, deptTextField);
        deptContainer.setAlignment(Pos.CENTER);
        deptContainer.setPadding(new Insets(5,5,30,5));
        deptContainer.setSpacing(158);

        deptTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    deptTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Button addTrainee = new Button(labelText);
        addTrainee.setStyle("-fx-background-color: #3667C7; -fx-padding: 10; -fx-text-fill: white; -fx-font-size: 15px;");

        Label errorInput = new Label("");
        errorInput.setStyle("-fx-text-fill : red;");

        addTrainee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isFormOk = true;

                    if(promotionComboBox.getSelectionModel().getSelectedItem() == null){
                        if(formationTextField.getText().equals("") || formationTextField.getText().isEmpty()){
                            errorInput.setText("Vous n'avez pas indiqué de formation.");
                            isFormOk = false;
                        }
                        else if(!formationTextField.getText().contains(" ")){
                                errorInput.setText("Vous n'avez pas indiqué la nouvelle " +
                                        "formation et sa promotion convenablement. " +
                                        "Il faut laisser un espace entre les deux informations.");
                            isFormOk = false;
                        }
                        else if (formationTextField.getText().contains(" ") && formationTextField.getText().split(" ")[1].matches("[^\\d]")){

                            errorInput.setText("Le numéro de promotion doit etre écrite sous forme de nombre");
                            isFormOk = false;
                        } else if (formationTextField.getText().split(" ")[0].length()>8){
                            errorInput.setText("Le nom de votre formation doit faire moins de 8 caractères.");
                            isFormOk = false;
                        } else if (formationTextField.getText().split(" ")[1].length()>3){
                            errorInput.setText("Le numéro de promotion doit faire moins de 3 chiffres.");
                            isFormOk = false;
                        }
                    }

                System.out.println(formationTextField.getText().split(" ")[0].length());


                if(yearTextField.getText().isEmpty()){
                    errorInput.setText("Vous n'avez pas indiqué l'année de la formation.");
                    isFormOk = false;
                } else if (yearTextField.getText().length()>4){
                    errorInput.setText("L'année de votre formation doit faire moins de 5 chiffres.");
                    isFormOk = false;
                }
                if(lastNameTextField.getText().isEmpty()){
                    errorInput.setText("Vous n'avez pas indiqué le nom de famille du stagiaire.");
                    isFormOk = false;
                }else if (lastNameTextField.getText().length()>24){
                    errorInput.setText("Le nom de famille de votre stagiaire doit faire moins de 25 caractères.");
                    isFormOk = false;
                }
                if(nameTextField.getText().isEmpty()){
                    errorInput.setText("Vous n'avez pas indiqué le prénom du stagiaire.");
                    isFormOk = false;
                }else if (nameTextField.getText().length()>24){
                    errorInput.setText("Le prénom de votre stagiaire doit faire moins de 25 caractères.");
                    isFormOk = false;
                }
                if(deptTextField.getText().isEmpty()){
                    errorInput.setText("Vous n'avez pas indiqué le département du stagiaire.");
                    isFormOk = false;
                } else if (nameTextField.getText().length()>24){
                    errorInput.setText("Le département de votre stagiaire doit faire moins de 4 chiffres.");
                    isFormOk = false;
                }



                if(isFormOk){
                    StringBuffer buffer = new StringBuffer();
                    if (promotionComboBox.getSelectionModel().getSelectedItem() != null) {
                        buffer.append(formationComboBox.getSelectionModel().getSelectedItem());
                        buffer.append("/");
                        buffer.append(promotionComboBox.getSelectionModel().getSelectedItem());
                    } else {
                        buffer.append(formationTextField.getText().toUpperCase().replace(" ", "/"));
                    }
                    buffer.append(
                            "/" +
                            yearTextField.getText() + "/" +
                            lastNameTextField.getText().toUpperCase() + "/" +
                            nameTextField.getText() + "/" +
                            deptTextField.getText()
                    );

                    if(!isAdd){
                        BinaryTreatment bt = new BinaryTreatment();
                        bt.findAndDelete(traineeToDelete);
                    }
                    System.out.println(buffer.toString());
                    BinaryTreatment binaryTreatment = new BinaryTreatment();
                    binaryTreatment.addTrainee(buffer.toString());

                    MainWindow newWindow = new MainWindow();
                    newWindow.start(stage);
                }
            }
        });


        Label addOrModifTitle = new Label(labelText);
        addOrModifTitle.setStyle("-fx-font-size : 20px; -fx-font-weight: bold;");
        addOrModifTitle.setPadding(new Insets(20));
        addOrModifTitle.setAlignment(Pos.CENTER);
        HBox addOrModifTitleContainer = new HBox(addOrModifTitle);
        addOrModifTitleContainer.setAlignment(Pos.CENTER);
        VBox formContainer = new VBox(10);
        formContainer.getChildren().add(addOrModifTitleContainer);
        if(isAdd){
            formContainer.getChildren().add(selectFormationAndPromotionContainer);
        }
        formContainer.getChildren().addAll(
                formationContainer,
                yearContainer,
                lastNameContainer,
                nameContainer,
                deptContainer,
                errorInput,
                addTrainee
        );
        formContainer.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.setStyle("-fx-font-size: 15px;");


        root.getChildren().addAll(borderPane, formContainer);

        Scene scene = new Scene(root,1250, 780);
        stage.setTitle("Annuaire de stagiaires");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
