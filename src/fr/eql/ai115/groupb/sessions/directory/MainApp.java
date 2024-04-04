package fr.eql.ai115.groupb.sessions.directory;


import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import fr.eql.ai115.groupb.sessions.directory.scenes.UserHome;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MainApp extends Application {
    private static final Logger logger = LogManager.getLogger();

    private static String SOURCE_FILE = "stagiaires.txt";
    private static String BINARY_FILE = "stagiaires.bin";
    InternCatalog catalog = new InternCatalog(SOURCE_FILE, BINARY_FILE);
    List<String> listPromo = catalog.getListPromo().stream().sorted().collect(Collectors.toList());
    List<Intern> listInternAfficher = new ArrayList<>();

    private ObservableList<Intern> data
            = FXCollections.observableArrayList(
            listInternAfficher);
    private Scene scene;

    public static String getSourceFile() {
        return SOURCE_FILE;
    }

    public static String getBinaryFile() {
        return BINARY_FILE;
    }

    public ObservableList<Intern> getData() {
        return data;
    }

    public Scene getScene() {
        return scene;
    }

    public MainApp() throws IOException {
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        // appel de la Home page
        UserHome userscene = new UserHome(primaryStage);
        primaryStage.setScene(userscene.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            InternCatalog catalog = new InternCatalog(SOURCE_FILE, BINARY_FILE);
            List<String> myList = catalog.formattedString();
            List<Intern> myInternList = catalog.createVector((ArrayList<String>) myList);

            if (catalog.getRaf().length() == 0){
                for (Intern intern : myInternList) {
                    catalog.addIntern(intern);
                }
            }


            List<Intern> myBinaryList = catalog.readMyBinaryFile();

        } catch (FileNotFoundException e) {
            logger.error("Impossible d'accéder au fichier binaire des objets célestes.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        launch(args);
    }
}