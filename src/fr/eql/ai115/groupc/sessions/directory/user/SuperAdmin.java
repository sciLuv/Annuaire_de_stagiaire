package fr.eql.ai115.groupc.sessions.directory.user;

import javax.swing.*;
import fr.eql.ai115.groupc.sessions.directory.traitment.Database;

public class SuperAdmin extends Admin{

    public static boolean isSuperAdmin = false;
    /**
     * Method for creation an admin and writing it in the database
     */
    public void adminCreation(){
        Admin admin = new Admin();

        String givenName;
        String pw;
        String pwConfirm;





        /*
         * Sets the name of the admin, loop until a name is set
         * Calls the dbWrite() method in database to write the object admin in the database
         */
        do {
            givenName=(JOptionPane.showInputDialog("Entrez un nom d'utilisateur (maximum 15 caractères)"));
            //display a message if nothing was written for the name
            if (givenName.isEmpty()){
                JOptionPane.showMessageDialog(null, "Veuillez entrez un nom d'utilisateur");
            }

            if(givenName.length()<15){
                admin.setName(givenName);
            }
            //display a message if the name is longer than 15 characters
            else {
                JOptionPane.showMessageDialog(null, "L'identifiant est trop long, veuillez en choisir un de moins de 15 caractère");
            }
        }while (admin.getName().isEmpty());

        /*
         *Sets the password, loop until password is set
         */
        do {
            //loops until the password and its confirmation are the same
            do {

                pw = (JOptionPane.showInputDialog("Entrez un mot de passe"));
                //Display a message if no password is given
                if (pw.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un mot de passe");
                }
                //Display a message if the password is longer than 15 characters
                else if(pw.length()>15){
                    JOptionPane.showMessageDialog(null,"Le mot de passe doit faire moins de 15 caractères");
                }

                pwConfirm = (JOptionPane.showInputDialog("Confirmez le mot de passe"));
                //Display a message if no password is given
                if (pwConfirm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez confirmer le mot de passe");
                }
                //Display a message if the password is longer than 15 characters
                if(pwConfirm.length()>15){
                    JOptionPane.showMessageDialog(null,"Le mot de passe doit faire moins de 15 caractères");
                }
                //Display a message if the password and its confirmation are different
                if (!pw.equals(pwConfirm)){
                    JOptionPane.showMessageDialog(null,"Les mots de passe ne correspondent pas");
                }
            }while (!pw.equals(pwConfirm));
            admin.setPassword(pw);
        }
        while (admin.getPassword().isEmpty());

        //Creation of a new Database object
        Database db = new Database();
        //Call of the dbWrite() method to write in the database
        db.dbWrite(admin);
        }



    }
