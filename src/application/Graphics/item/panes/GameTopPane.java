package application.Graphics.item.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameTopPane extends AnchorPane {

    private Pane parent;
    public int score;
    Label scoreLabel;

    public GameTopPane() {
        super();
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if(newValue != null) {
                    parent = (Pane) newValue;
                    setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                    initSize();
                    updateSize();
                }
            }
        });


        this.scoreLabel = new Label("Score: "+ 0);
        this.getChildren().add(scoreLabel);


    }

    public void initSize() {
        setPrefHeight(parent.getWidth());
        setPrefHeight(parent.getHeight() * 0.1);
        prefWidthProperty().bind(parent.widthProperty());
    }

    public void updateSize() {
        parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setPrefHeight(newValue.doubleValue() * 0.1);
            }
        });
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: "+this.score);
    }

}
