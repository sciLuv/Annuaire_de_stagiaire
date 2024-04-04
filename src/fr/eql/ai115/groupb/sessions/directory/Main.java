package fr.eql.ai115.groupb.sessions.directory;


import fr.eql.ai115.groupb.sessions.directory.intern.Intern;
import fr.eql.ai115.groupb.sessions.directory.intern.InternCatalog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static final String SOURCE_FILE = "stagiaires.txt";
    public static final String BINARY_FILE = "stagiaires.bin";
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
    }
}
