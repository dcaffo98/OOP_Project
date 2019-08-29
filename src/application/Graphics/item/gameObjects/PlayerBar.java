package application.Graphics.item.gameObjects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerBar extends ImageView {

    private double currentParentWidth;
    private double currentParentHeight;
    private double speed;

    public PlayerBar(String url) {
        super(url);
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if(newValue != null) {
                    currentParentWidth = ((Pane) newValue).getWidth();
                    currentParentHeight = ((Pane) newValue).getHeight();
                    setFitWidth(currentParentWidth * 0.08);
                    setFitHeight(currentParentHeight * 0.03);
                    speed = getFitWidth() * 0.2;
                    resizeRelocate((currentParentWidth - getFitWidth()) / 2, currentParentHeight - getFitHeight() * 1.5, getFitWidth(), getFitHeight());
                    updateSize();
                }
            }
        });
    }

    public double getCurrentParentWidth() {
        return currentParentWidth;
    }

    public void setCurrentParentWidth(double currentParentWidth) {
        this.currentParentWidth = currentParentWidth;
    }

    public double getCurrentParentHeight() {
        return currentParentHeight;
    }

    public void setCurrentParentHeight(double currentParentHeight) {
        this.currentParentHeight = currentParentHeight;
    }

    public void updateSize() {
        ((Pane) getParent()).widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentParentWidth = newValue.doubleValue();
                setFitWidth(currentParentWidth * 0.08);
                speed = getFitWidth() * 0.6;
            }
        });

        ((Pane) getParent()).heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentParentHeight = newValue.doubleValue();
                setFitHeight(currentParentHeight * 0.03);
                setLayoutY(currentParentHeight - getFitHeight() * 1.5);
            }
        });
    }

    public void moveRight() {
        if(getLayoutX() + getFitWidth() + speed > currentParentWidth)
            setLayoutX(currentParentWidth - getFitWidth());
        else
            setLayoutX(getLayoutX() + speed);
    }

    public void moveLeft() {
        if(getLayoutX() - speed < 0)
            setLayoutX(0);
        else
            setLayoutX(getLayoutX() - speed);
    }
}
