package fr.eql.ai115.groupc.sessions.directory.affichage.promoSelection;

import fr.eql.ai115.groupc.sessions.directory.affichage.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.List;

public class PromoButton extends HBox {
    public PromoButton(String promoName){
        this.setStyle("-fx-background-radius: 5; -fx-background-color: #ac82ff;");
        this.setPadding(new Insets(3,3,3,8));

        Label promoNameLabel = new Label(promoName);
        promoNameLabel.setPadding(new Insets(5,0,5,0));
        promoNameLabel.setStyle("-fx-text-fill: white;");

        Button deletePromoFromSelection = new Button("X");
        deletePromoFromSelection.setStyle("-fx-background-color: #ac82ff; -fx-text-fill: black;");
        deletePromoFromSelection.setPadding(new Insets(5,5,5,10));
        deletePromoFromSelection.setCursor(Cursor.HAND);


        deletePromoFromSelection.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-radius: 5; -fx-background-color: #f93131;");
            promoNameLabel.setStyle("-fx-text-fill: black;");
            deletePromoFromSelection.setStyle("-fx-background-color: #f93131; -fx-text-fill: white;");
        });

        deletePromoFromSelection.setOnMouseExited(event -> {
            this.setStyle("-fx-background-radius: 5; -fx-background-color: #ac82ff;");
            promoNameLabel.setStyle("-fx-text-fill: white;");
            deletePromoFromSelection.setStyle(" -fx-background-color: #ac82ff; -fx-text-fill: black;");

        });


        this.getChildren().addAll(promoNameLabel, deletePromoFromSelection);
        deletePromoFromSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("test 1 :" + promoName);
                List<String> actualListOfPromotion = MainWindow.getSelectedPromotionList();
                actualListOfPromotion.remove(promoName);
                MainWindow.setSelectedPromotionList(actualListOfPromotion);
                System.out.println("test 2 :" + MainWindow.getSelectedPromotionList());

                MainWindow.AllSelectedPromotions.promotionSelected.getChildren().remove(PromoButton.this);
                if(MainWindow.AllSelectedPromotions.promotionSelected.getChildren().isEmpty()){
                    MainWindow.AllSelectedPromotions.promotionSelected.getChildren().add(new Label("Aucune promotion selectionnée."));
                }
            }
        });
    }
}
