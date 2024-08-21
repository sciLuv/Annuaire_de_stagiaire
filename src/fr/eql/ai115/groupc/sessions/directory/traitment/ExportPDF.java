package fr.eql.ai115.groupc.sessions.directory.traitment;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportPDF {

    public static void generatePDF(List<Trainee> traineeList) {
        Document document = new Document();

        try {
            // user the FileChooser to select a file to save the PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);

            if (file == null) {
                // User has cancelled the file selection
                return;
            }
            //Use the complete file path when creating the FileOutputStream instance
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
            document.open();

            //Create a bold and underlined font
            Font boldUnderlineFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD | Font.UNDERLINE);

            // Create the text "Liste des Stagiaires" with the bold and underlined style
            Chunk titleChunk = new Chunk("Liste des Stagiaires", boldUnderlineFont);

            //Create a centered header line with the formation, promotion and year
            Paragraph title = new Paragraph(titleChunk);
            title.setAlignment(Element.ALIGN_CENTER); // Aligner au centre
            title.setSpacingAfter(20f); // Espacement après l'en-tête
            document.add(title);

            String currentFormation = null;
            String currentPromotion = null;

            //Add the list content to the PDF document
            for (Trainee trainee : traineeList) {

                if (!trainee.getFormation().equals(currentFormation) || !trainee.getPromotion().equals(currentPromotion)) {
                    // New formation or promotion, add the header
                    addHeaderToDocument(document, trainee.getFormation(), trainee.getPromotion(), String.valueOf(trainee.getYear()));
                    currentFormation = trainee.getFormation();
                    currentPromotion = trainee.getPromotion();
                }
                //Add the trainee details to the document
                addTraineeDetailsToDocument(document, trainee);
            }

            document.close();

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("PDF généré avec succès !");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void addHeaderToDocument(Document document, String formation, String promotion, String annee) {
        try {
            //Create a bold font
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA,18, Font.BOLD);

            //Create a centered header line with the formation, promotion and year
            Paragraph headerLine = new Paragraph(String.format("        %s %s %s", formation, promotion, annee), boldFont);
            headerLine.setSpacingAfter(10F); // Espacement après l'en-tête
            headerLine.setSpacingBefore(20f); // Espacement après l'en-tête
            document.add(headerLine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void addTraineeDetailsToDocument(Document document, Trainee trainee) {
        try {
            //Add the trainee details to the document
            String details = String.format("                                    %s, %s, %s\n",
                    trainee.getLastName(), trainee.getName(), trainee.getDept());
            document.add(new Paragraph(details));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
