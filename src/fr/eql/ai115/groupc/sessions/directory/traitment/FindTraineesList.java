package fr.eql.ai115.groupc.sessions.directory.traitment;

import fr.eql.ai115.groupc.sessions.directory.affichage.MainWindow;
import fr.eql.ai115.groupc.sessions.directory.binaryTree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class FindTraineesList {

    public static List<Trainee> listOfTrainee(){

        Tree tree = new Tree();
        List<String> stringList = new ArrayList<>();
        List<Trainee> traineesList = new ArrayList<>();
        if(MainWindow.isAllSelected){
            stringList.addAll(tree.parcoursInfixe(MainWindow.lastName, MainWindow.name));
        } else {
            Collections.sort(MainWindow.getSelectedPromotionList());
            List<String> listOfPromotion = MainWindow.getSelectedPromotionList();
            for (String s : listOfPromotion) {
                stringList.addAll(tree.getTraineeFromPromotion(new ArrayList<>(), s, MainWindow.lastName, MainWindow.name));
            }
        }

        for (String s : stringList) {
            StringTokenizer st = new StringTokenizer(s, "/");
            String formation = st.nextToken();
            String promotion = st.nextToken();
            String lastName = st.nextToken();
            String name = st.nextToken();
            String year = st.nextToken();
            String dept = st.nextToken();
            traineesList.add(new Trainee(formation,promotion,year,lastName,name,dept));
        }
        return  traineesList;

    }

}
