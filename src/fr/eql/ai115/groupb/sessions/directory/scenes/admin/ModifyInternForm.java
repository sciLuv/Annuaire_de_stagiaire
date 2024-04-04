package fr.eql.ai115.groupb.sessions.directory.scenes.admin;

import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ModifyInternForm {
    private static final String SOURCE_FILE = "stagiaires.txt";
    private static final String BINARY_FILE = "stagiaires.bin";
    private InternCatalog catalog = new InternCatalog(SOURCE_FILE, BINARY_FILE);


    private Scene scene;

    public Scene getScene() {
        return scene;
    }


    public ModifyInternForm(Stage primaryStage, ObservableList<Intern> data, TableView<Intern> table,
                            ComboBox<String> comboBox, Intern myIntern) throws IOException {

        // Form content
        Text addFormTitle = new Text("Modifier un stagiaire");

        Label promoTextLabel = new Label("Promotion");
        TextField promoTextField = new TextField();
        promoTextField.setText(myIntern.getPromotion());

        Label lastNameLabel = new Label("Nom");
        TextField lastNameTextField = new TextField();
        lastNameTextField.setText(myIntern.getLastName());

        Label firstNameLabel = new Label("Prénom");
        TextField firstNameTextField = new TextField();
        firstNameTextField.setText(myIntern.getFirstName());

        Label departmentLabel = new Label("Département");
        TextField departmentTextField = new TextField();
        departmentTextField.setText(Integer.toString(myIntern.getDepartment()));

        Label yearLabel = new Label("Année");
        TextField yearTextField = new TextField();
        yearTextField.setText(Integer.toString(myIntern.getYear()));

        // CSS FILE ID
        addFormTitle.setId("title");
        lastNameLabel.setId("label");
        firstNameLabel.setId("label");
        promoTextLabel.setId("label");
        yearLabel.setId("label");
        departmentLabel.setId("label");

        Button modifyButton = new Button("Modifier le stagiaire");
        modifyButton.setId("button");

        Button cancelButton = new Button("Annuler");
        cancelButton.setId("button");

        cancelButton.setOnAction(actionEvent -> {
            AdminHome adminHome = null;
            try {
                adminHome = new AdminHome(primaryStage);
                primaryStage.setScene(adminHome.getScene());
                primaryStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Intern newIntern = new Intern(lastNameTextField.getText().toUpperCase(),
                        firstNameTextField.getText(), promoTextField.getText().toUpperCase(),
                        Integer.parseInt(departmentTextField.getText()),
                        Integer.parseInt(yearTextField.getText()));
                try {
                    catalog.modifyAnIntern(myIntern,newIntern);


                    List<String> listPromo = catalog.getListPromo().stream().sorted().collect(Collectors.toList());
                    comboBox.getItems().clear();
                    comboBox.getItems().addAll(listPromo);

                    AdminHome adminHome = new AdminHome(primaryStage);
                    primaryStage.setScene(adminHome.getScene());
                    primaryStage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        // Form Layout
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setPadding(new Insets(50));

        VBox promotionBox = new VBox();
        promotionBox.setSpacing(20);
        promotionBox.getChildren().addAll(promoTextLabel, promoTextField);

        VBox lastNameBox = new VBox();
        lastNameBox.setSpacing(20);
        lastNameBox.getChildren().addAll(lastNameLabel, lastNameTextField);

        VBox firstNameBox = new VBox();
        firstNameBox.setSpacing(20);
        firstNameBox.getChildren().addAll(firstNameLabel, firstNameTextField);

        VBox departmentBox = new VBox();
        departmentBox.setSpacing(20);
        departmentBox.getChildren().addAll(departmentLabel, departmentTextField);

        VBox yearBox = new VBox();
        yearBox.setSpacing(20);
        yearBox.getChildren().addAll(yearLabel, yearTextField);


        // button hbox
        HBox btnHbox = new HBox(10);
        btnHbox.setAlignment(Pos.BASELINE_LEFT);
        btnHbox.getChildren().addAll(modifyButton, cancelButton);

        mainVbox.getChildren().addAll(promotionBox, lastNameBox, firstNameBox, departmentBox, yearBox, btnHbox);

        // Scene layout
        scene = new Scene(mainVbox, 1000, 600);
        // Showing Layout

        FileInputStream input = new FileInputStream("files/img/LogoEQL.png");
        Image icon = new Image(input);
        // Ajouter l'icône à la fenêtre principale
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");

    }

    public ModifyInternForm() throws FileNotFoundException {
    }
}
