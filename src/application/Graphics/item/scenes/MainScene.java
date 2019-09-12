package application.Graphics.item.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainScene extends Scene {

    public MainScene(Parent root, double width, double height) {
        super(root, width, height);
        getStylesheets().add("resources/style/stylesheet.css");

    }

}


/*

    public void setChangeListener() {
        sceneWidth.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newWidth = (double) newValue;
                title.setLayoutX(newWidth * 0.2);
                title.setPrefWidth(newWidth * 0.6);
                menu.setPrefWidth(newWidth * 0.2);
                menu.setPrefTileWidth(newWidth * 0.2);
                menu.setLayoutX(newWidth * 0.4);
                subSceneLayoutX = newWidth * 0.2;
                subSceneWidth = newWidth * 0.6;
                if (subSceneShowed) {
                    subScene.setLayoutX(subSceneLayoutX);
                    subScene.setWidth(subSceneWidth);
                }
            }
        });

        sceneHeight.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newHeight = (double) newValue;
                title.setLayoutY(newHeight * 0.1);
                title.setPrefHeight(newHeight * 0.2);
                menu.setPrefHeight(newHeight * 0.5);
                menu.setPrefTileHeight(sceneHeight.get() * 0.5 / menuButtons.size());
                menu.setLayoutY(newHeight * 0.4);
                subSceneLayoutY = newHeight * 0.4;
                subSceneHeight = newHeight * 0.5;
                if (subSceneShowed) {
                    subScene.setLayoutY(subSceneLayoutY);
                    subScene.setHeight(subSceneHeight);
                }
            }
        });
    }

   */
