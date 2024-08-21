package fr.eql.ai115.groupc.sessions.directory.binaryTree;

import fr.eql.ai115.groupc.sessions.directory.traitment.BinaryTreatment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tree {

    public List<String> parcoursInfixe(Node node, List<String> list, String lastName, String name) {
        if (node != null) {
            if(node.getSmallerChildAddress() != -1)
                parcoursInfixe(readBinaryFile(node.smallerChildAddress), list, lastName, name);


            if(lastName == null && name == null) list.add(node.getData());

            else{
                String[] data = node.getData().split("/");
                String dataName = data[3].toLowerCase();
                String dataLastName = data[2].toLowerCase();

                String trueName = name == null ? "" : name.toLowerCase();
                String trueLastName = lastName == null ? "" : lastName.toLowerCase();

                if(name == null){
                    if (dataLastName.equals(trueLastName)){
                        list.add(node.getData());
                    }
                }else if(lastName == null){
                    if (dataName.equals(trueName)){
                        list.add(node.getData());
                    }
                }else{
                    if (dataLastName.equals(trueLastName)){
                        if(dataName.equals(trueName)){
                            list.add(node.getData());
                        }
                    }
                }

            }

            if(node.getBiggerChildAddress() != -1)
                parcoursInfixe(readBinaryFile(node.biggerChildAddress), list, lastName, name);
        }
        return list;
    }
    public List<String> parcoursInfixe(String lastName,String name) {
        List<String> list = new ArrayList<>();
        parcoursInfixe(readBinaryFile(0),list, lastName, name);
        return list;
    }

    public List<String> getAllFormation(Node node, List<String> listFormation) {
        if (node != null) {
            if(node.getSmallerChildAddress() != -1)
                getAllFormation(readBinaryFile(node.smallerChildAddress), listFormation);

            String formation = node.getData().split("/")[0];
            if(!listFormation.contains(formation))
                listFormation.add(formation);

            if(node.getBiggerChildAddress() != -1)
                getAllFormation(readBinaryFile(node.biggerChildAddress), listFormation);
        }
        return listFormation;
    }

    public List<String> getAllFormation(List<String> listFormation){
        getAllFormation(readBinaryFile(0), listFormation);
        return listFormation;
    }

    public List<String> getPromotionsFromFormation(Node node, List<String> listPromotion, String formation) {
        if (node != null) {
            if(node.getSmallerChildAddress() != -1)
                getPromotionsFromFormation(readBinaryFile(node.smallerChildAddress), listPromotion, formation);

            String comparedFormation = node.getData().split("/")[0];
            if(Objects.equals(formation, comparedFormation)){
                String promotion = node.getData().split("/")[1];
                if(!listPromotion.contains(promotion))
                    listPromotion.add(promotion);
            }


            if(node.getBiggerChildAddress() != -1)
                getPromotionsFromFormation(readBinaryFile(node.biggerChildAddress), listPromotion, formation);
        }
        return listPromotion;
    }

    public List<String> getPromotionsFromFormation(List<String> listPromotion, String formation){
        getPromotionsFromFormation(readBinaryFile(0), listPromotion, formation);
        return listPromotion;
    }

    public List<String> getTraineeFromPromotion(Node node, List<String> listTrainee, String promotion, String lastName, String name) {
        if (node != null) {
            if(node.getSmallerChildAddress() != -1)
                getTraineeFromPromotion(readBinaryFile(node.smallerChildAddress), listTrainee, promotion, lastName, name);

            String[] data = node.getData().split("/");
            String DataPromotion = data[0] + " " + data[1];

            if(DataPromotion.equals(promotion)){

                if(lastName == null && name == null) listTrainee.add(node.getData());

                else{
                    String dataName = data[3].toLowerCase();
                    String dataLastName = data[2].toLowerCase();

                    String trueName = name == null ? "" : name.toLowerCase();
                    String trueLastName = lastName == null ? "" : lastName.toLowerCase();

                    if(name == null){
                        if (dataLastName.equals(trueLastName)){
                                listTrainee.add(node.getData());
                        }
                    } else if(lastName == null){
                        if (dataName.equals(trueName)){
                            listTrainee.add(node.getData());
                        }
                    }else{
                        if (dataLastName.equals(trueLastName)){
                            if(dataName.equals(trueName)){
                                listTrainee.add(node.getData());
                            }
                        }
                    }
                }
            }

            if(node.getBiggerChildAddress() != -1)
                getTraineeFromPromotion(readBinaryFile(node.biggerChildAddress), listTrainee, promotion, lastName, name);
        }
        return listTrainee;
    }

    public List<String> getTraineeFromPromotion(List<String> listTrainee, String promotion, String lastName, String name){
        getTraineeFromPromotion(readBinaryFile(0), listTrainee, promotion, lastName, name);
        return listTrainee;
    }


    public Node readBinaryFile(long position) {
        BinaryTreatment bt = new BinaryTreatment();
        File file = new File("db\\binary.bin");

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long ownAddress = position;
            raf.seek(position);
            StringBuffer actualLineValue = new StringBuffer();
            for (int i = 0; i < bt.sizeOfABinaryLine; i+=2) actualLineValue.append(raf.readChar());

            String data = bt.binaryToString(actualLineValue.toString(), "/").trim();

            String stringSmallerAddress = actualLineValue.substring(bt.beginFirstAdress, bt.endFirstAdress).trim();
            long smallerAddress = stringSmallerAddress.isEmpty() ? -1 : Integer.parseInt(stringSmallerAddress);


            String stringBiggerAddress = actualLineValue.substring(bt.endFirstAdress, bt.endSecondAdress).trim();
            long biggerAddress = stringBiggerAddress.isEmpty() ? -1 : Integer.parseInt(stringBiggerAddress);


            return new Node(data, ownAddress, smallerAddress, biggerAddress, 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        Tree tree = new Tree();

        List<String> formationList = new ArrayList<>();
        tree.getAllFormation(formationList);

        List<String> promotionList = new ArrayList<>();

    }
}
