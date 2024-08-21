package fr.eql.ai115.groupc.sessions.directory.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;

public class Admin{
    ///////////////
    ///Attributs///
    ///////////////

    private String name;
    private String password;
    private String type = "Admin";

    public static boolean isAdmin = false;

    /////////////////////
    ///Getter & Setter///
    /////////////////////



    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public void modifyName(String newName) {
        this.name = newName;
    }

    public void modifyPassword(String newPassword) {
        this.password = newPassword;
    }
    public static String adminConnected = "";

    public static void writeAdminsToFile(String filename, ObservableList<Admin> adminList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Admin admin : adminList) {
                writer.write(admin.getName() + " " + admin.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ///////////////////
    ///Constructeurs///
    ///////////////////

    public Admin(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public Admin() {}

    @Override
    public String toString() {
        return name;
    }


    //////////////
    ///Methodes///
    //////////////


    public void importTraineeFile(){

    }
    ////////////////
    ///Stagiaires///
    ////////////////


    public void addTrainee(){}

    public void modifyTrainee(){}

    public void deleteTrainee(){}

    ///////////////
    ///Connexion///
    ///////////////

    public void connect(){

    }

    //////////////
    ///Modifier///
    //////////////

    public void modifyName(){}
    public void modifyPassword(){}

    public static ObservableList<Admin> readAdminsFromFile(String filename) {
        ObservableList<Admin> adminList = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    adminList.add(new Admin(parts[0], parts[1], "Admin"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adminList;
    }
}

//