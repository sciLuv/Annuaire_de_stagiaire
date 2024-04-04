package fr.eql.ai115.groupb.sessions.directory.intern;

import java.io.*;
import java.util.*;

public class BinaryFileSort {
public static void main(String[] args) {

        try {
        // Lire les données du fichier binaire
        FileInputStream fis = new FileInputStream("stagiaires.bin");
        DataInputStream dis = new DataInputStream(fis);
        List<String> dataList = new ArrayList<>();
        while (dis.available() > 0) {
        String data = dis.readUTF();
        dataList.add(data);
        }
        dis.close();

        // Trier les données
        Collections.sort(dataList);

        // Écrire les données triées dans un nouveau fichier binaire
        FileOutputStream fos = new FileOutputStream("tristagiaires.bin");
        DataOutputStream dos = new DataOutputStream(fos);
        for (String data : dataList) {
        dos.writeUTF(data);
        }
        dos.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        }
