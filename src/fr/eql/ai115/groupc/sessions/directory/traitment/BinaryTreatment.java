package fr.eql.ai115.groupc.sessions.directory.traitment;

import fr.eql.ai115.groupc.sessions.directory.traitment.Trainee;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Provides methods for reading data from a text file, creating a binary file,
 * and manipulating binary data for trainee records.
 *
 * @author SciLuv
 * @version 1.0
 */
public class BinaryTreatment {
    static public int sizeOfABinaryLine = 160;
    static public int beginFirstAdress = 68;
    static public int endFirstAdress = 74;
    static public int endSecondAdress = 80;
    static public int endOfTraineeInfo = 136;
    static public int beginSecondAdress2 = 148;

    /**
     * Reads data from a text file and processes it to create trainee records in binary format.
     * @param fileName The name of the text file to be read.
     */
    public void textFileReading(String fileName){

        try{
            FileReader in = new FileReader(fileName);
            BufferedReader bfr= new BufferedReader(in);
            StringBuffer textBuff = new StringBuffer();
            String string;
            do{
                string =  bfr.readLine();
                if(string!=null){
                    if (!string.contains(String.valueOf("*"))) {
                        textBuff.append(string + "/");
                    } else {
                        addTraineeInBinaryFile(traineeToBinaryLine(traineeFactory(textBuff.toString())));
                        textBuff.delete(0, textBuff.length());
                    }
                } else addTraineeInBinaryFile(traineeToBinaryLine(traineeFactory(textBuff.toString())));
            } while (string!=null);

        } catch (IOException e){
            System.out.println("Pb entrée sortie :" + e.getMessage());
        }
    }

    /*private Node nodeFactory(String){

    }*/
    /**
     * Adds a trainee record to the binary file.
     * @param str A string representing the trainee record in binary format.
     */
    public void addTraineeInBinaryFile(String str){
        System.out.println(str);
        boolean newTraineeIsSave = false ;
        long position = 0;
        File file = new File("db\\binary.bin");

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")){

            char[] charToAddToBinary = str.toCharArray();
            raf.seek(position);
            do{
                if (file.length() == 0) newTraineeIsSave = writeCharsAndSpacesInBinary(raf, charToAddToBinary);
                else{

                    StringBuffer actualLine = new StringBuffer();
                    for (int i = 0; i < sizeOfABinaryLine; i+=2){
                        actualLine.append(raf.readChar());
                        position += 2;
                    }

                    String comparisonLine = binaryToString(actualLine.toString(), " ");
                    int newLineComparison, number;

                    if(binaryToString(str, " ").compareTo(comparisonLine) < 0){
                        newLineComparison = nextLinePosition(actualLine.toString().substring(beginFirstAdress, endFirstAdress));
                        number = 24;
                    } else {
                        newLineComparison = nextLinePosition(actualLine.toString().substring(endFirstAdress, endSecondAdress));
                        number = 12;
                    }

                    if(newLineComparison == -1){

                        position = position - number;
                        raf.seek(position);
                        char[] newTraineeAdress = String.valueOf(file.length()).toCharArray();
                        for (char c : newTraineeAdress) raf.writeChar(c);
                        position = file.length();
                        raf.seek(position);

                        newTraineeIsSave = writeCharsAndSpacesInBinary(raf, charToAddToBinary);

                    } else {
                        position = newLineComparison;
                        raf.seek(position);
                    }
                }
            }
            while(!newTraineeIsSave);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a binary file for storing trainee records.
     */
    public void binaryFileCreation(){
        // Créer un objet Path à partir du chemin spécifié
        Path folderPath = Paths.get("db");
        Path filePath = Paths.get("db\\binary.bin");

        try {
            // Créer le fichier s'il n'existe pas
            Files.createDirectories(folderPath);
            Files.createFile(filePath);
            System.out.println("Le fichier a été créé avec succès.");
        } catch (IOException e) {
            System.err.println("Impossible de créer le fichier : " + e.getMessage());
        }

        try {
            // Créer le fichier s'il n'existe pas
            Files.createDirectories(folderPath);
            System.out.println("Le dossier a été créé avec succès.");
        } catch (IOException e) {
            System.err.println("Impossible de créer le dossier : " + e.getMessage());
        }
    }


    /**
     * construct an easy readable string from a binary element
     * @param str a string import from an element of the binary file
     * @return a string, without all useless space and characters (usefull in the binary file context)
     */
    public String binaryToString(String str, String delim){
        StringBuffer stringBuffer = new StringBuffer();
        String formationString = str.substring(0, 8).trim();
        String promotionString = str.substring(8, 11).trim();
        String lastNameString = str.substring(11, 36).trim();
        String nameString = str.substring(36, 61).trim();
        String yearString = str.substring(61, 65);
        String deptString = str.substring(65, 68);
        stringBuffer.append(
                formationString + delim + promotionString  + delim + lastNameString + delim + nameString + delim +
                        yearString + delim + deptString
        );
        return stringBuffer.toString();
    }

    private boolean writeCharsAndSpacesInBinary(RandomAccessFile raf, char[] characters) throws IOException {
        for (char c : characters) raf.writeChar(c);
        for (int i = 0; i < 12; i++) raf.writeChar(' ');
        return true;
    }

    /**
     * Construct a Trainee Instance.
     * @param str is a line with all informations for the Trainee's construction
     * @return a Trainee instance construct from the string in parameter
     */
    public Trainee traineeFactory(String str){
        String newString = str.replaceFirst(" ", "/");
        StringTokenizer st = new StringTokenizer(newString, "/");
        if(st.countTokens()==6){
            String formation = st.nextToken();
            String promotion = st.nextToken();
            String year = st.nextToken();
            String lastName = st.nextToken();
            String prenom = st.nextToken();
            String dept = st.nextToken();
            return new Trainee(formation,promotion,year,lastName,prenom,dept);

        }  else {
            return new Trainee();
        }
    }
    private String formatValue(String value, int maxLength) {
        if (value.length() > maxLength) {

            value = value.substring(0, maxLength);
        }

        StringBuilder formattedValue = new StringBuilder(value);

        for (int i = 0; i < maxLength - value.length(); i++) {
            formattedValue.append(" ");
        }
        return formattedValue.toString();
    }
    private String traineeToBinaryLine(Trainee t) {
        StringBuffer traineeBinaryFormat = new StringBuffer();

        String[] values = {t.getFormation(), t.getPromotion(), t.getLastName(), t.getName(), t.getYear(), t.getDept()};
        int[] maxLengths = {8, 3, 25, 25, 4, 3};

        for (int i = 0; i < values.length; i++) {
            traineeBinaryFormat.append(formatValue(values[i], maxLengths[i]));
        }
        return traineeBinaryFormat.toString();
    }

    private int nextLinePosition(String nextLineNumber){
        if (Objects.equals(nextLineNumber, "      ")) return -1;
        else return Integer.parseInt(nextLineNumber.trim());
    }

    public void addTrainee(String newTrainee){

        addTraineeInBinaryFile(traineeToBinaryLine(traineeFactory(newTrainee)));
    }

    private String readingBinary(RandomAccessFile raf, int number) throws IOException {
        StringBuffer strbuff = new StringBuffer();
        for (int i = 0; i < number; i += 2) {
            strbuff.append(raf.readChar());
        }
        return strbuff.toString();
    }
    private void writeCharInBinary(RandomAccessFile raf,long address, char[] arrayOfChar) throws IOException {
        raf.seek(address);
        for (char c : arrayOfChar) raf.writeChar(c);
    }

    private void writeSpaceInBinary(RandomAccessFile raf, Long address, int number) throws IOException {
        raf.seek(address);
        for (int i = 0; i < number; i += 2) raf.writeChar(' ');
    }

    private long[] findTraineeInBinaryFile(String str){

        String traineeToFind = traineeToBinaryLine(traineeFactory(str));
        boolean newTraineeIsSave = false;
        long isLeftChild = 0;
        long position = 0;
        long lastPosition = 0;
        File file = new File("db\\binary.bin");

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            do {
                raf.seek(position);
                String dataLine = readingBinary(raf, endOfTraineeInfo);
                String nextAdressLeft = readingBinary(raf, 12);
                String nextAdressRight = readingBinary(raf, 12);

                if (traineeToFind.compareTo(dataLine) < 0) {
                    lastPosition = position;
                    position = Integer.parseInt(nextAdressLeft.trim());
                    isLeftChild = 1;
                } else if (traineeToFind.compareTo(dataLine) > 0) {
                    lastPosition = position;
                    position = Integer.parseInt(nextAdressRight.trim());
                    isLeftChild = -1;
                } else {
                    return new long[]{lastPosition, position, isLeftChild};
                }
            }
            while(!newTraineeIsSave);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new long[]{0, 0};
    }

    private void deleteTrainee(long[] addresses){
        File file = new File("db\\binary.bin");
        long lastAdress = addresses[0];
        long address = addresses[1];
        boolean isLeft = addresses[2] == 1;

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {

            raf.seek(address+endOfTraineeInfo);
            StringBuffer constructorNextAdressLeft = new StringBuffer();
            StringBuffer constructorNextAdressRight = new StringBuffer();
            for (int i = 0; i < 12; i += 2) constructorNextAdressLeft.append(raf.readChar());
            for (int i = 0; i < 12; i += 2) constructorNextAdressRight.append(raf.readChar());

            long nextAdressLeft = constructorNextAdressLeft.toString().trim().isEmpty() ? -1
                    : Long.parseLong(constructorNextAdressLeft.toString().trim());
            long nextAdressRight = constructorNextAdressRight.toString().trim().isEmpty() ? -1
                    : Long.parseLong(constructorNextAdressRight.toString().trim());

            if(nextAdressLeft == -1 && nextAdressRight == -1){
                //Si le noeud n'a pas d'enfant du tout
                if(isLeft){
                    raf.seek(lastAdress+endOfTraineeInfo);
                    for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                } else {
                    raf.seek(lastAdress+beginSecondAdress2);
                    for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                }
            }
            else if (nextAdressLeft == -1 || nextAdressRight == -1){
                //Si le noeud n'a qu'un seul enfant
                StringBuffer childConstructor = new StringBuffer();
                //si le noeud n'a pas d'enfant gauche on cherche l'adresse de son enfant droit
                if(nextAdressLeft == -1) raf.seek(address+beginSecondAdress2);
                    //si le noeud n'a pas d'enfant Droit on cherche l'adresse de son enfant gauche
                else raf.seek(address+endOfTraineeInfo);
                for (int i = 0; i < 12; i += 2) childConstructor.append(raf.readChar());

                char[] childAdress = childConstructor.toString().toCharArray();

                //si le noeud actuel est l'enfant gauche
                if(isLeft) raf.seek(lastAdress+endOfTraineeInfo);
                    //si le noeud actuel est l'enfant droit
                else raf.seek(lastAdress+beginSecondAdress2);

                for (char c : childAdress) raf.writeChar(c);

            }
            else if (nextAdressLeft != 1 && nextAdressRight != 1){
                //condition a deux enfants
                long actualNode = 0 ;
                long actualNodeParent = 0;

                raf.seek(nextAdressLeft);
                String test1 = "";
                for (int i = 0; i < 160; i += 2) test1 += raf.readChar();


                raf.seek(nextAdressLeft + beginSecondAdress2);
                StringBuffer lastRightChildConstruct = new StringBuffer();
                for (int i = 0; i < 12; i += 2) lastRightChildConstruct.append(raf.readChar());
                long addressOfLastRightChild =
                        lastRightChildConstruct.toString().trim().isEmpty() ? -1 :
                                Long.parseLong(lastRightChildConstruct.toString().trim());

                actualNodeParent = nextAdressLeft;
                actualNode = addressOfLastRightChild;

                if(addressOfLastRightChild != -1){
                    do{
                        raf.seek(addressOfLastRightChild + beginSecondAdress2);
                        StringBuffer RightNodeConstruct = new StringBuffer();
                        for (int i = 0; i < 12; i += 2) RightNodeConstruct.append(raf.readChar());
                        if(!RightNodeConstruct.toString().trim().isEmpty()){
                            actualNodeParent = actualNode;
                        }
                        actualNode = addressOfLastRightChild;
                        addressOfLastRightChild =
                                RightNodeConstruct.toString().trim().isEmpty() ? -1 :
                                        Long.parseLong(RightNodeConstruct.toString().trim());

                        if(addressOfLastRightChild == -1){

                            //ajout du nouveau noeud dans l'indexation du noeud parent qu'on viens de supp
                            char[] actualNodeCharArray = String.valueOf(actualNode).toCharArray();

                            if(isLeft){
                                raf.seek(lastAdress+endOfTraineeInfo);
                                for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                                raf.seek(lastAdress+endOfTraineeInfo);
                                for (char c : actualNodeCharArray) raf.writeChar(c);
                            } else {
                                raf.seek(lastAdress+beginSecondAdress2);
                                for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                                raf.seek(lastAdress+beginSecondAdress2);
                                for (char c : actualNodeCharArray) raf.writeChar(c);
                            }


                            //enlevé la relation vers le noeud remplacant et son ancien pere suhaila
                            raf.seek(actualNodeParent+beginSecondAdress2);
                            for (int i = 0; i < 12; i += 2) raf.writeChar(' ');

                            //si le noeud remplacant a un noeud enfant, le relier a son ancien pere
                            StringBuffer sb = new StringBuffer();
                            raf.seek(actualNode+endOfTraineeInfo);
                            for (int i = 0; i < 12; i += 2) sb.append(raf.readChar());
                            long oldLastChildleft =
                                    sb.toString().trim().isEmpty() ? -1 :
                                            Long.parseLong(sb.toString().trim());
                            if(oldLastChildleft != -1){
                                raf.seek(actualNodeParent+beginSecondAdress2);
                                char[] oldLastChildleftCharArray = String.valueOf(oldLastChildleft).toCharArray();
                                for (char c : oldLastChildleftCharArray) raf.writeChar(c);
                            }

                            //ajout des enfant du noeud supprimer au noeud remplacant
                            raf.seek(actualNode+endOfTraineeInfo);
                            for (int i = 0; i < 24; i += 2) raf.writeChar(' ');

                            char[] nextAdressLeftChar = String.valueOf(nextAdressLeft).toCharArray();
                            raf.seek(actualNode+endOfTraineeInfo);
                            for (char c : nextAdressLeftChar) raf.writeChar(c);

                            char[] nextAdressRightChar = String.valueOf(nextAdressRight).toCharArray();
                            raf.seek(actualNode+beginSecondAdress2);
                            for (char c : nextAdressRightChar) raf.writeChar(c);

                            //enlevé l'index du noeud enlevé
                            raf.seek(address+endOfTraineeInfo);
                            for (int i = 0; i < 24; i += 2) raf.writeChar(' ');
                        }
                    }
                    while(addressOfLastRightChild != -1);
                }
                else{
                    char[] leftIsTheNewParent = String.valueOf(nextAdressLeft).toCharArray();

                    if(isLeft){
                        //si l'enfant gauche n'a pas d'enfant droit et que le parent était le parent droit
                        raf.seek(lastAdress+endOfTraineeInfo);
                        for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                        raf.seek(lastAdress+endOfTraineeInfo);
                        for (char c : leftIsTheNewParent) raf.writeChar(c);
                    } else {
                        //si l'enfant gauche n'a pas d'enfant droit et que le parent était le parent gauche
                        raf.seek(lastAdress+beginSecondAdress2);
                        for (int i = 0; i < 12; i += 2) raf.writeChar(' ');
                        raf.seek(lastAdress+beginSecondAdress2);
                        for (char c : leftIsTheNewParent) raf.writeChar(c);
                    }

                    raf.seek(address + beginSecondAdress2);
                    StringBuffer right = new StringBuffer();
                    for (int i = 0; i < 12; i += 2) right.append(raf.readChar());
                    char[] trueRight = right.toString().toCharArray();

                    raf.seek(nextAdressLeft+beginSecondAdress2);
                    for (char c : trueRight) { raf.writeChar(c); }

                    raf.seek(address+endOfTraineeInfo);
                    for (int i = 0; i < 24; i += 2) raf.writeChar(' ');
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void  findAndDelete(String str){
        deleteTrainee(findTraineeInBinaryFile(str));
    }

    public static void main(String[] args) {
        String path = "src\\fr\\eql\\ai115\\groupc\\sessions\\directory\\binary\\stagiaire.txt";

        BinaryTreatment test = new BinaryTreatment();
//        test.binaryFileCreation();
//        test.textFileReading(path);

//        "KAPLA/12/2008/GUIYING/Cai/93"
//        "BLOB/77/2022/TALAMO/Ruggero/28" exemple sans enfant
//        BLOB/77/2022/KANAAN/Bassma/77 exemple avec un seul enfant, sur la branche droite
//        BLOB/77/2022/MADJER/Asim/78 exemple avec un seul enfant, sur la branche gauche
//        "KAPLA/12/2008/FIORE/Prassede/94" exemple avec deux enfants
//        KAPLA/13/2009/BARRIERE/David/94
//        KAPLA/12/2008/KANAAN/Suhaila/94 exemple de suppression du 1er element du tableau
//        KAPLA/12/2008/VAN DEN BORRE/Sebastian/77
        long[] testFind = test.findTraineeInBinaryFile("KAPLA 12/2008/VAN DEN BORRE/Sebastian/77");
        test.deleteTrainee(testFind);

    }
}
