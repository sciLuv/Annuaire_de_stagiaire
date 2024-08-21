package fr.eql.ai115.groupc.sessions.directory.affichage.promoSelection;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PromotionSelection extends VBox{
    public FlowPane promotionSelected = new FlowPane(Orientation.HORIZONTAL,5,5);
    public PromotionSelection (double spacing){
        super(spacing);
        ObservableList components = this.getChildren();

        Label PromotionSelectionneesLabel = new Label("Promotion(s) selectionnée(s) :");
        PromotionSelectionneesLabel.setStyle("-fx-font-weight: bold;-fx-underline: true;");
        promotionSelected.setPadding(new Insets(5));
        promotionSelected.getChildren().add(new Label("Aucune promotion selectionnée."));

        components.addAll(PromotionSelectionneesLabel, promotionSelected);

    }
}
