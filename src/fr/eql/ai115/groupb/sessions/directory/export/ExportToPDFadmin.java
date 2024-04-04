package fr.eql.ai115.groupb.sessions.directory.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class ExportToPDFadmin extends Application {

    private String inputFilePath = "C:\\Users\\Formation\\Documents\\Project\\SessionsDirectory";
    private String outputFilePath;
public static void main(String[] args) {
        launch(args);
        }

@Override
public void start(Stage primaryStage) {

    GridPane grille = new GridPane();
    grille.setAlignment(Pos.TOP_CENTER);
    grille.setHgap(10);
    grille.setVgap(10);
    grille.setPadding(new Insets(50, 50, 50, 50));

    primaryStage.setTitle("Exportation PDF");

    Label label = new Label();
    grille.add(label,0,0,1,35);
    grille.setVgap(10);


    // Bouton pour sélectionner le fichier texte
    Button selectInputFileButton = new Button("Importer un fichier");
    selectInputFileButton.setOnAction(e -> selectInputFile(primaryStage));
    selectInputFileButton.setStyle("-fx-background-color: #455F78; -fx-text-fill: white;");

    // Bouton pour sélectionner le chemin de destination
    Button selectOutputFileButton = new Button("Exporter le fichier en PDF");
    selectOutputFileButton.setOnAction(e -> selectOutputFile(primaryStage));
    selectOutputFileButton.setStyle("-fx-background-color: #455F78; -fx-text-fill: white;");

    // Bouton pour déclencher la conversion
    Button convertButton = new Button("Convertir le fichier en PDF");
    convertButton.setOnAction(e -> convertToPDF());
    convertButton.setStyle("-fx-background-color: #455F78; -fx-text-fill: white;");


    grille.add(selectInputFileButton,0,0,1,1);
    grille.add(selectOutputFileButton,0,0,1,8);


        VBox root = new VBox(20);
        root.getChildren().add(grille);

        Scene scene = new Scene(root, 600, 550);
        scene.getStylesheets().add(getClass().getResource("exportForm.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        }

    private void selectInputFile(Stage primaryStage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le fichier texte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            inputFilePath = file.getAbsolutePath();
        }
        System.out.println("Le fichier .txt a bien été importé !");
    }

    private void selectOutputFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le chemin de destination");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            outputFilePath = file.getAbsolutePath();
        }
        convertToPDF();
    }

    private void convertToPDF() {

        try {
            // Créer un nouveau document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
            document.open();

            // Lire le contenu du fichier .txt et l'écrire dans le document PDF
            BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath));
            String line;
            while ((line = br.readLine()) != null) {
                document.add(new Paragraph(line));
            }
            br.close();

            // Fermer le document
            document.close();

            System.out.println("Le fichier .txt a été converti en PDF avec succès !");
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la conversion du fichier .txt en PDF : " + e.getMessage());
        }
    }
}