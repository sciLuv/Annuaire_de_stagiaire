package fr.eql.ai115.groupb.sessions.directory.scenes.admin;

import fr.eql.ai115.groupb.sessions.directory.eventhandler.superadmin.ChangeIdHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChangeIdForm {
    private Scene scene;
    private Stage modifyAdminStage;
    private TextField idField;
    private PasswordField pswdField;
    private TextField newIdField;
    private PasswordField newpswdField;

    public Scene getScene() {return scene;}

    public Stage getModifyAdminStage() {return modifyAdminStage;}

    public ChangeIdForm(Stage primaryStage) throws FileNotFoundException {
        //Login Form content
        Text adminTitle = new Text("Modifier mes identifiants");
        Label idLabel = new Label("Identifiant / email actuel :");
        idField = new TextField();
        Label pswdLabel = new Label("Mot de passe actuel :");
        pswdField = new PasswordField();
        Label newIdLabel = new Label("Nouvel identifiant / email :");
        newIdField = new TextField();
        Label newpswdLabel = new Label("Nouveau mot de passe :");
        newpswdField = new PasswordField();
        Button validButton = new Button("Valider");
        Text actionText = new Text();
        Button closeButton = new Button("Retour à l'accueil");
        closeButton.setOnAction(actionEvent -> {
            modifyAdminStage.close();
        });
        Text closeText = new Text();

        // "Connexion" button action
        modifyAdminStage = new Stage();
        ChangeIdHandler changeIdHandler = new ChangeIdHandler(modifyAdminStage,
                idField, pswdField, newIdField, newpswdField, actionText, primaryStage);
        validButton.setOnAction(changeIdHandler);


        //CSS FILE ID
        adminTitle.setId("title");
        idLabel.setId("label");
        newIdLabel.setId("label");
        pswdLabel.setId("Label");
        newpswdLabel.setId("Label");
        validButton.setId("button");

        // Form Layout
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setPadding(new Insets(50));

        // Creating HBox for labels, textfields and button
        // label vbox
        VBox idBox = new VBox();
        idBox.setSpacing(20);
        idBox.getChildren().addAll(idLabel,idField);

        // field vbox
        VBox pswdBox = new VBox();
        pswdBox.setSpacing(20);
        pswdBox.getChildren().addAll(pswdLabel,pswdField);

        VBox newIdBox = new VBox();
        idBox.setSpacing(20);
        idBox.getChildren().addAll(newIdLabel,newIdField);

        // field vbox
        VBox newpswdBox = new VBox();
        pswdBox.setSpacing(20);
        pswdBox.getChildren().addAll(newpswdLabel,newpswdField);

        // button hbox
        HBox btnHbox = new HBox(10);
        btnHbox.setAlignment(Pos.BASELINE_LEFT);
        btnHbox.getChildren().add(validButton);
        btnHbox.setAlignment(Pos.BASELINE_RIGHT);
        btnHbox.getChildren().add(closeButton);

        // Adding all elements to mainVBOX
        mainVbox.getChildren().addAll(adminTitle,idBox,pswdBox,btnHbox,actionText);


        // Scene layout
        scene = new Scene(mainVbox, 500, 600);
        modifyAdminStage = new Stage();
        modifyAdminStage.setX(primaryStage.getX()+200);
        modifyAdminStage.setY(primaryStage.getY()+100);

        // Showing Layout
        FileInputStream input = new FileInputStream("files/img/LogoEQL.png");
        Image icon = new Image(input);
        // Ajouter l'icône à la fenêtre principale
        modifyAdminStage.getIcons().add(icon);
        modifyAdminStage.initModality(Modality.WINDOW_MODAL);
        modifyAdminStage.initOwner(primaryStage);
        modifyAdminStage.setScene(scene);
        modifyAdminStage.setTitle("Modifier mes identifiants");
        modifyAdminStage.show();
        scene.getStylesheets().add("fr/eql/ai115/groupb/sessions/directory/style/styles.css");
    }
}





