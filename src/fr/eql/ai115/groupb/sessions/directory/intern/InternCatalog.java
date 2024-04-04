package fr.eql.ai115.groupb.sessions.directory.intern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InternCatalog {
    private static final Logger logger = LogManager.getLogger();
    private BufferedReader bfr;


    private static final int LASTNAME_LENGTH = 30;
    private static final int FIRSTNAME_LENGTH = 30;
    private static final int PROMOTION_LENGTH = 15;
    private static final int DEPARTMENT_LENGTH = 2;
    private static final int YEARS_LENGTH = 2;
    private static final long CHILD_LEFT_NODE_POINTER_LENGTH = 4;
    private static final long CHILD_RIGHT_NODE_POINTER_LENGTH = 4;
    private static final long CURRENT_NODE_POINTER_LENGTH = 4;
    private static final long PARENT_NODE_POINTER_LENGTH = 4;
    private static final int INTERN_LENGTH = LASTNAME_LENGTH + FIRSTNAME_LENGTH + PROMOTION_LENGTH +
                DEPARTMENT_LENGTH + YEARS_LENGTH;
    private static final int INTERN_3_INFORMATION = PROMOTION_LENGTH + LASTNAME_LENGTH + FIRSTNAME_LENGTH;

    private static final int INTERN_RAF_LENGTH = (int) ((INTERN_LENGTH * 2) + (CURRENT_NODE_POINTER_LENGTH +CHILD_LEFT_NODE_POINTER_LENGTH + CHILD_RIGHT_NODE_POINTER_LENGTH +
                PARENT_NODE_POINTER_LENGTH));
    private static final int POSITION_CURRENT_NODE = (int) (PROMOTION_LENGTH + LASTNAME_LENGTH + FIRSTNAME_LENGTH + DEPARTMENT_LENGTH
            + YEARS_LENGTH);
    private static final int POSITION_RIGHT_CHILD = (int) (INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH + CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH + CHILD_LEFT_NODE_POINTER_LENGTH) * 2;
    private static final int POSITION_LEFT_CHILD = (int) (INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH +CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH) * 2;

    private final String sourceFile;

    public RandomAccessFile getRaf() {
        return raf;
    }

    private final RandomAccessFile raf;
    private int internsCount;




    /**
     * This constructor will create the RandomAccessFile instance used to write and read interns.
     * @param sourceFile The path to the text file containing the list of
     *                   interns to write in the binary file.
     * @param binaryFile The path to the binary file.
     * @throws FileNotFoundException When the creation or access to the binary file fails.
     */
    public InternCatalog(String sourceFile, String binaryFile) throws FileNotFoundException {
        this.sourceFile = sourceFile;
        raf = new RandomAccessFile(binaryFile, "rw");

        try {
            FileReader in = new FileReader(sourceFile);
            bfr= new BufferedReader(in);
        } catch (IOException e){
            System.out.println("Pb entrée sortie :" + e.getMessage());
        }
    }

    /**
     * format the information in the text file to be easier to be used.
     * @return an Array List where each index as the right formatted string
     * @throws IOException When the access to the text files fails
     */
    public List<String> formattedString() throws IOException {
        List<String> arrayListString = new ArrayList<>();
        try {
            // Variable pour stocker chaque ligne formatée
            StringBuilder formattedLine = new StringBuilder();

            // Boucle de lecture des lignes du fichier d'entrée
            String line;
            while ((line = bfr.readLine()) != null) {
                // Supprime les astérisques (*)
                line = line.replace("*", "");


                // Ajoute chaque élément de la ligne formatée à la StringBuilder
                formattedLine.append("*").append(line);
                // Si la ligne contient un espace vide, écrire la ligne formatée dans le fichier
                if (line.trim().isEmpty()) {
                    arrayListString.add(formattedLine.toString().trim());

                    // Réinitialise la StringBuilder pour la ligne suivante
                    formattedLine.setLength(0);
                }
            }

            // Ferme les lecteurs et flux d'écriture
            bfr.close();

        } catch (IOException e) {
            logger.error(e);
        }
        return arrayListString;
    }


    //Transformer le fichier en une collection de stagiaire
    public List<Intern> createVector(ArrayList<String> myList){
        String chaine;
        Intern intern;
        List<Intern> interns = new Vector<Intern>();
        for (String str : myList) {
            chaine = str;
            if(chaine!=null){
                intern= createIntern(chaine);
                interns.add(intern);
            }
        }

        return interns;
    }

    // Transforme une chaine en un objet de type Intern
    //format de la chaine : *AZERTY 01*2008*KANAAN*Victor*94*
    private Intern createIntern(String chaine){
        Intern intern=null;
        StringTokenizer st = new StringTokenizer(chaine, "*");
        if(st.countTokens()==5){
            String promotion = st.nextToken();
            int year = Integer.parseInt(st.nextToken());
            String lastName= st.nextToken();
            String firstName = st.nextToken();
            int department= Integer.parseInt(st.nextToken());
            intern = new Intern(lastName,firstName,promotion,department,year);
        }
        return intern;
    }


    /**
     * Adds the necessary spaces to a given word, in order to make it
     * the required length to be written in the binary file.
     * @param word The word to complete.
     * @param categoryLength The length required for that category of word.
     * @return The correctly sized word.
     */
    private String addSpaces(String word, int categoryLength) {
        int spaces = categoryLength - word.length();
        StringBuilder builder = new StringBuilder();
        builder.append(word);
        for (int i = 0; i < spaces; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    public void writeOneIntern(Intern intern) throws IOException {
            raf.seek(raf.length());
            intern.setCurrentNodePos((int) raf.getFilePointer());
            raf.writeChars(intern.getPromotion());
            raf.writeChars(intern.getLastName());
            raf.writeChars(intern.getFirstName());
            raf.writeInt(intern.getDepartment());
            raf.writeInt(intern.getYear());
            raf.writeLong(intern.getCurrentNodePos());
            raf.writeLong(intern.getParentNodePointer());
            raf.writeLong(intern.getChildNodeLeftPointer());
            raf.writeLong(intern.getChildNodeRightPointer());

    }

    public void addIntern(Intern intern) throws IOException {
        formattedIntern(intern);
        if (raf.length() == 0){
            writeOneIntern(intern);
        } else {
            compare(0, intern);
        }

    }

    public void formattedIntern(Intern intern){
        intern.setPromotion(addSpaces(intern.getPromotion(), PROMOTION_LENGTH));
        intern.setLastName(addSpaces(intern.getLastName(), LASTNAME_LENGTH));
        intern.setFirstName(addSpaces(intern.getFirstName(), FIRSTNAME_LENGTH));
    }


    private void compare(long pointer, Intern intern) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(intern.getPromotion());
        sb.append(intern.getLastName());
        sb.append(intern.getFirstName());
        sb.append(intern.getCurrentNodePos());
        sb.append(intern.getParentNodePointer());
        sb.append(intern.getChildNodeLeftPointer());
        sb.append(intern.getChildNodeRightPointer());

        raf.seek(pointer);

        String object = readChars(INTERN_LENGTH);
        if (object.compareTo(sb.toString()) < 0){
            raf.seek(pointer + POSITION_RIGHT_CHILD);
            long myRightPos = raf.readLong();

            if (myRightPos == -1){
                long myNewPos = raf.length();
                intern.setCurrentNodePos(myNewPos);
                raf.seek(pointer + POSITION_RIGHT_CHILD);
                raf.writeLong(myNewPos);
                intern.setParentNodePointer(pointer);
                writeOneIntern(intern);
            } else {
                compare(myRightPos, intern);
            }
        }
        if (object.compareTo(sb.toString()) > 0){
            raf.seek(pointer + POSITION_LEFT_CHILD);
            long myLeftPos = raf.readLong();
            if (myLeftPos == -1 ){
                long myNewPos = raf.length();
                intern.setCurrentNodePos(myNewPos);
                raf.seek(pointer + POSITION_LEFT_CHILD);
                raf.writeLong(myNewPos);
                intern.setParentNodePointer(pointer);
                writeOneIntern(intern);

            } else {
                compare(myLeftPos, intern);
            }
        }

    }

    private String readChars(int length) throws IOException {
        StringBuilder chars = new StringBuilder();
        for (int i = 0; i < INTERN_3_INFORMATION; i++) {
            chars.append(raf.readChar());
        }
        chars.append(raf.readInt());
        chars.append(raf.readInt());
        chars.append(raf.readLong());
        chars.append(raf.readLong());
        chars.append(raf.readLong());
        chars.append(raf.readLong());

        return chars.toString();
    }


    public List<String> getListPromo() throws IOException {
        List<Intern> myListOfIntern = new ArrayList<>();
        long position = 0;
        do {
            Intern myIntern = new Intern();
            raf.seek(position);
            writeAnIntern(myIntern);
            position = raf.getFilePointer();
            myListOfIntern.add(myIntern);
        } while (raf.getFilePointer() != raf.length());
       return myListOfIntern.stream().map(Intern::getPromotion).distinct().collect(Collectors.toList());
    }
    public List<Intern> readMyBinaryFile() throws IOException {
        List<Intern> myListOfIntern = new ArrayList<>();
        long position = 0;
        do {
            Intern myIntern = new Intern();
            raf.seek(position);
            writeAnIntern(myIntern);
            position = raf.getFilePointer();
                myListOfIntern.add(myIntern);
        } while (raf.getFilePointer() != raf.length());
        return myListOfIntern;
    }

    public List<Intern> readMyBinaryFileForMyPromos(String myPromo) throws IOException {
        List<Intern> myListOfIntern = new ArrayList<>();
        long position = 0;
        do {
            Intern myIntern = new Intern();
            raf.seek(position);
            writeAnIntern(myIntern);
            position = raf.getFilePointer();
            if(myIntern.getPromotion().equals(myPromo)){
                if ((myIntern.getChildNodeRightPointer() != -1
                        && myIntern.getChildNodeLeftPointer() != -1)
                        || myIntern.getParentNodePointer() != -1){
                    myListOfIntern.add(myIntern);
                }
            }
        } while (raf.getFilePointer() != raf.length());
        return myListOfIntern;
    }


//   public Set<String> readMyPromo() throws IOException {
//        Set<String> myListOfPromo = new HashSet<>();
//        long position = 0;
//        do {
//            raf.seek(position);
//            writeAnPromotion(myListOfPromo);
//            position = raf.getFilePointer();
//        } while (raf.getFilePointer() != raf.length());
//        return myListOfPromo;
//    }

    private void writeAnIntern(Intern myIntern) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PROMOTION_LENGTH; i++){
            sb.append(raf.readChar());
        }
        myIntern.setPromotion(sb.toString());
        sb.delete(0, sb.length());
        for (int i = 0; i < LASTNAME_LENGTH; i++){
            sb.append(raf.readChar());
        }
        myIntern.setLastName(sb.toString());
        sb.delete(0, sb.length());
        for (int i = 0; i < FIRSTNAME_LENGTH; i++){
            sb.append(raf.readChar());
        }
        myIntern.setFirstName(sb.toString());
        sb.delete(0, sb.length());

        int myDepartment = raf.readInt();
        myIntern.setDepartment(myDepartment);

        int myPromoYear = raf.readInt();
        myIntern.setYear(myPromoYear);

        long myCurrentNode = raf.readLong();
        myIntern.setCurrentNodePos(myCurrentNode);

        long parentNode = raf.readLong();
        myIntern.setParentNodePointer(parentNode);

        long leftChildNode = raf.readLong();
        myIntern.setChildNodeLeftPointer(leftChildNode);

        long rightChildNode = raf.readLong();
        myIntern.setChildNodeRightPointer(rightChildNode);

    }

//    private void writeAnPromotion(final Set<String> listMyPromos) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < PROMOTION_LENGTH; i++){
//            sb.append(raf.readChar());
//        }
//
//        for (int i = 0; i < LASTNAME_LENGTH; i++){
//           raf.readChar();
//        }
//
//        for (int i = 0; i < FIRSTNAME_LENGTH; i++){
//            raf.readChar();
//        }
//
//        int myDepartment = raf.readInt();
//
//
//        int myPromoYear = raf.readInt();
//
//
//        long myCurrentNode = raf.readLong();
//
//
//        long parentNode = raf.readLong();
//
//
//        long leftChildNode = raf.readLong();
//
//
//        long rightChildNode = raf.readLong();
//
//        listMyPromos.add(sb.toString());
//    }


    public void readOneInternInBinary(long position) throws IOException {
        raf.seek(position);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PROMOTION_LENGTH; i++){
            sb.append(raf.readChar());
        }
        for (int i = 0; i < LASTNAME_LENGTH; i++){
            sb.append(raf.readChar());
        }
        for (int i = 0; i < FIRSTNAME_LENGTH; i++){
            sb.append(raf.readChar());
        }

        int myDepartment = raf.readInt();

        int myPromoYear = raf.readInt();

        long myCurrentNode = raf.readLong();

        long parentNode = raf.readLong();

        long leftChildNode = raf.readLong();

        long rightChildNode = raf.readLong();

        sb.append(myDepartment);
        sb.append(myPromoYear);
        sb.append(myCurrentNode);
        sb.append(parentNode);
        sb.append(leftChildNode);
        sb.append(rightChildNode);

        System.out.println(sb);
    }

    public void deleteAnIntern(Intern myIntern) throws IOException {
        if (myIntern.getChildNodeLeftPointer() == -1 && myIntern.getChildNodeRightPointer() == -1){
            deleteMyInternWithoutChild(myIntern);
            System.out.println(myIntern.toString());
        }
        if (myIntern.getChildNodeLeftPointer() != -1){
            deleteMyInternWithLeftChild(myIntern);
        }
        else if (myIntern.getChildNodeRightPointer() != -1){
            deleteMyInternWithRightChild(myIntern);
        }
    }

    // Fonction pour supprimer un intern qui n'a pas d'enfant
    private void deleteMyInternWithoutChild(Intern myIntern) throws IOException {
        raf.seek(myIntern.getCurrentNodePos() + ((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH ) * 2));
        myIntern.setParentNodePointer(-1);
        raf.writeLong(-1);
    }


    // Fonction pour supprimer un intern qui a un enfant gauche
    private void deleteMyInternWithLeftChild(Intern myIntern) throws IOException {
        long placementInMyDeletedIntern = myIntern.getCurrentNodePos() + ((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH) * 2);
        long currentNodePointer = myIntern.getCurrentNodePos();
        long parentPointer = myIntern.getParentNodePointer();
        long rightChildPointer = myIntern.getChildNodeRightPointer();
        long leftChildPointer = myIntern.getChildNodeLeftPointer();

        mostRightChild(currentNodePointer , parentPointer, leftChildPointer, rightChildPointer);
        raf.seek(placementInMyDeletedIntern);
        raf.writeLong(-1);
        raf.writeLong(-1);
        raf.writeLong(-1);
    }

    // Fonction pour supprimer un intern qui a un enfant gauche
    private void deleteMyInternWithRightChild(Intern myIntern) throws IOException {
        long placementInMyDeletedIntern = myIntern.getCurrentNodePos() + ((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH) * 2);
        long currentNodePointer = myIntern.getCurrentNodePos();
        long parentPointer = myIntern.getParentNodePointer();
        long rightChildPointer = myIntern.getChildNodeRightPointer();
        long leftChildPointer = myIntern.getChildNodeLeftPointer();

        mostLeftChild(currentNodePointer , parentPointer, rightChildPointer, leftChildPointer);
        raf.seek(placementInMyDeletedIntern);
        raf.writeLong(-1);
        raf.writeLong(-1);
        raf.writeLong(-1);
    }

    // Fonction pour aller a l'enfant le plus a droite, qui quand il n'y a plus d'enfant droit, appelle d'autre méthode
    private void mostRightChild(long originNodePointer, long parentPosition, long leftChildPosition, long rightChildPosition) throws IOException {
        long internRightChildPosition = leftChildPosition + ((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH + CHILD_LEFT_NODE_POINTER_LENGTH) * 2);
        raf.seek(internRightChildPosition);
        long myLongRead = raf.readLong();
        raf.seek(leftChildPosition + (INTERN_3_INFORMATION +DEPARTMENT_LENGTH + YEARS_LENGTH) * 2);
        long myLongToWrite = raf.readLong();
        if (myLongRead != -1){
            mostRightChild(originNodePointer, parentPosition, myLongRead, rightChildPosition);
        }
        else {
                changeMyLastParent(parentPosition, originNodePointer, myLongToWrite);
                if (leftChildPosition != -1){
                    rewriteNewChild(leftChildPosition, parentPosition);


                }
                if (rightChildPosition != -1){
                    rewriteNewChild(rightChildPosition, parentPosition);
                }
            }
        }

    // Fonction pour aller a l'enfant le plus a gauche de l'enfant droit, qui quand il n'y a plus d'enfant gauche, appelle d'autre méthode
    private void mostLeftChild(long originNodePointer, long parentPosition,long rightChildPosition , long leftChildPosition) throws IOException {
        // Lecture enfant gauche de l'enfant droit
        long internLeftChildPosition = rightChildPosition + ((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH) * 2);
        raf.seek(internLeftChildPosition);
        long myLongRead = raf.readLong();
        raf.seek(leftChildPosition + (INTERN_3_INFORMATION +DEPARTMENT_LENGTH + YEARS_LENGTH) * 2);
        long myLongToWrite = raf.readLong();

        if (myLongRead != -1){
            mostLeftChild(originNodePointer, parentPosition, myLongRead, leftChildPosition);
        }
        else {
            changeMyLastParent(parentPosition, originNodePointer, myLongToWrite);
            if (leftChildPosition != -1){
                rewriteNewChild(leftChildPosition, parentPosition);

            }
            if (rightChildPosition != -1){
                rewriteNewChild(rightChildPosition, parentPosition);
            }
        }
    }


    // Fonction pour réécrire les enfants de l'ancien parent pour qu'il retourne -1
    private void changeMyLastParent(long parentPos, long lastPos, long newPos) throws IOException {
        long posToCheckMyLeftChild = parentPos +((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH) * 2);
        long posToCheckMyRightChild = parentPos +((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH + PARENT_NODE_POINTER_LENGTH + CHILD_LEFT_NODE_POINTER_LENGTH) * 2);
        raf.seek(posToCheckMyLeftChild);
        long leftChild = raf.readLong();
        raf.seek(posToCheckMyRightChild);
        long rightChild = raf.readLong();
        if (leftChild == lastPos){
            raf.seek(posToCheckMyLeftChild);
            raf.writeLong(newPos);
        } else if (rightChild == lastPos){
            raf.seek(posToCheckMyRightChild);
            raf.writeLong(newPos);
        }

    }

    private void rewriteNewChild(long childPos, long newParentPos) throws IOException {
        long posToWriteMyParent = childPos +((INTERN_3_INFORMATION + DEPARTMENT_LENGTH + YEARS_LENGTH
                + CURRENT_NODE_POINTER_LENGTH) * 2);
        raf.seek(posToWriteMyParent);
        raf.writeLong(newParentPos);
    }




    public void modifyAnIntern(Intern lastIntern, Intern newIntern) throws IOException {
        deleteAnIntern(lastIntern);
        addIntern(newIntern);
    }
}
