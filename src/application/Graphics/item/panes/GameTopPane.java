package application.Graphics.item.panes;

import application.Graphics.item.gameObjects.SongProgress;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameTopPane extends AnchorPane {

    private Pane parent;
    private int score;
    private Label scoreLabel;
    private Label showCombo;
    private SongProgress progress;

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


    }

    public void initSize() {
        setPrefHeight(parent.getWidth());
        setPrefHeight(parent.getHeight() * 0.1);
        prefWidthProperty().bind(parent.widthProperty());
        this.progress = new SongProgress(this);
        this.scoreLabel = new Label("Score: "+ 0);
        scoreLabel.setLayoutY(20);
        scoreLabel.setLayoutX(20);
        scoreLabel.setFont(new Font("Times new Roman",30));
        this.getChildren().add(scoreLabel);
        this.showCombo = new Label("COMBO: 0");
        showCombo.setLayoutX(this.getPrefWidth() / 2);
        showCombo.setTextAlignment(TextAlignment.CENTER);
        showCombo.setFont(new Font("Times new Roman",30));
        showCombo.setLayoutY(20);
        this.getChildren().add(showCombo);
        this.getChildren().add(progress);

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

    public Label getShowCombo() {
        return showCombo;
    }

    public void setShowCombo(int combo) {
        this.showCombo.setText("COMBO: "+combo);
    }

    public SongProgress getProgress() {
        return progress;
    }

    public void setProgress(SongProgress progress) {
        this.progress = progress;
    }
}
