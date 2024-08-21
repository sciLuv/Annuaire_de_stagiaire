package fr.eql.ai115.groupc.sessions.directory.traitment;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Database {

    ///////////////
    ///Attributs///
    ///////////////

    private static final String FOLDER = "db\\";
    private static final String FILE_WRITER_FILE = "database.txt";

    private static final String ADMIN_BASE_LOG = "Admin Admin123*";
    File folder = new File(FOLDER);
    String content;
    //////////////
    ///Methodes///
    //////////////

    public void beginDb() throws IOException {

            File folder= new File(FOLDER);
            if(!folder.exists())
            Files.createDirectories(Paths.get(FOLDER));

            File adminDb = new File(FOLDER + FILE_WRITER_FILE);
            if(!adminDb.exists())
                Files.createFile(Paths.get(FOLDER + FILE_WRITER_FILE));

            try {
                if(adminDb.length() == 0){
                    FileWriter writer = new FileWriter(FOLDER+FILE_WRITER_FILE);
                    writer.write("Admin Admin123*");
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Impossible d'écrire dans le fichier admin: " + e.getMessage());
            }
    }
    /**
     * This method is called by the adminCreation() method of the SuperAdmin
     *Method to write in the database
     * @param admin the admin being created by the SuperAdmin
     */
    public void dbWrite(Object admin){

        folder.mkdir();

        try {
            FileWriter writer = new FileWriter(FOLDER+FILE_WRITER_FILE, true);
            writer.write("\r\n"+admin.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *Method to read the database
     * Needed when an admin wants to connect
     */
    public void dbRead(){
        try {
            FileReader reader = new FileReader(FOLDER+FILE_WRITER_FILE);
            int character=0;
            content="";
            while ((character=reader.read()) !=-1){
                content+=(char)character;
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void dbBRead(){

        try {
            FileReader reader = new FileReader(FOLDER+FILE_WRITER_FILE);
            BufferedReader bReader = new BufferedReader(reader);
            content="";
            while (bReader.ready()){
                content+=bReader.readLine()+"\r\n";
            }
            reader.close();bReader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int search(String research) throws IOException {

        try {
            FileReader reader = new FileReader(FOLDER + FILE_WRITER_FILE);
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            long counter = 0;
            do {
                line = bReader.readLine();
                if(line!=null){
                    if (line.equals(research)){
                        if(counter == 0) return 0;
                        else return 1;
                    }
                    counter++;
                }
            }while (line!=null);
            return 2;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
    }
}


