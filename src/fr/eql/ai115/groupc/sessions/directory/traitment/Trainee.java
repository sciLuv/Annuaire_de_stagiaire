package fr.eql.ai115.groupc.sessions.directory.traitment;

import javafx.collections.ObservableList;

public class Trainee {
    String formation,promotion,year,lastName,name,dept;

    public Trainee() {
    }

    public Trainee(String formation, String promotion, String year, String lastName, String name, String dept) {
        this.formation = formation;
        this.promotion = promotion;
        this.year = year;
        this.lastName = lastName;
        this.name = name;
        this.dept = dept;
    }


    @Override
    public String toString() {
        return "Trainee{" +
                "formation='" + formation + '\'' +
                ", promotion='" + promotion + '\'' +
                ", year='" + year + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public static ObservableList<Trainee> getTraineeList() {
        ObservableList<Trainee> trainees = javafx.collections.FXCollections.observableArrayList();
        return trainees;
    }
}





