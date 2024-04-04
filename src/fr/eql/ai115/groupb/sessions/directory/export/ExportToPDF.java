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
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ExportToPDF extends Application {
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
    grille.add(label,0,6);
    grille.setVgap(10);


    Button importButton = new Button("Importez un fichier");
    importButton.setStyle("-fx-background-color: #455F78; -fx-text-fill: white;");

    Button exportButton = new Button("Exportez le fichier choisi en PDF");
    exportButton.setStyle("-fx-background-color: #455F78; -fx-text-fill: white;");

        grille.add(exportButton,0,0,1,1);


            exportButton.setOnAction(e -> {


            String inputFilePath = "C:\\Users\\Formation\\Documents\\Project\\SessionsDirectory\\stagiaires.txt";
            String outputFilePath = "C:\\Users\\Formation\\Downloads\\stagiaires.pdf";

                    label.setText("Votre fichier a bien été crée en format PDF\r\n" +  "dans le dossier 'Téléchargements'");

            try {
                // Créer un nouveau document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
                document.open();

                // Lire le contenu du fichier .txt et l'écrire dans le document PDF
                BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
                String line;
                while ((line = br.readLine()) != null) {
                    document.add(new Paragraph(line));
                }
                br.close();

                // Fermer le document
                document.close();

                System.out.println("Le fichier .txt a été converti en PDF avec succès !");
            } catch (Exception f) {
                System.out.println("Une erreur s'est produite lors de la conversion du fichier .txt en PDF : " + f.getMessage());
            }
        }
        );

        VBox root = new VBox(20);
        root.getChildren().add(grille);

        Scene scene = new Scene(root, 600, 550);
        scene.getStylesheets().add(getClass().getResource("exportForm.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        }
