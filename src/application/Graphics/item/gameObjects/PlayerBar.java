package application.Graphics.item.gameObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerBar extends ImageView {

    private DoubleProperty speed;
    private double position;

    public PlayerBar(String url) {
        super(url);
        speed = new SimpleDoubleProperty();
        position = getLayoutX() + (getFitWidth() / 2);
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if(newValue != null) {
                    fitWidthProperty().bind(((Pane) newValue).widthProperty().multiply(0.06));
                    fitHeightProperty().bind(((Pane) newValue).heightProperty().multiply(0.02));
                    setLayoutX((((Pane) newValue).getWidth() - getFitWidth()) / 2) ;
                    setManaged(false);          //altrimenti non fa fare il binding del layoutY
                    layoutYProperty().bind(((Pane) newValue).heightProperty().subtract(getFitHeight() * 1.5));
                    speed.bind( ((Pane) newValue).widthProperty().multiply(0.01));
                }
            }
        });
    }

    public void moveRight() {
        if(getLayoutX() + getFitWidth() + speed.doubleValue() > ((Pane) getParent()).getWidth())
            setLayoutX(((Pane) getParent()).getWidth() - getFitWidth());
        else
            setLayoutX(getLayoutX() + speed.doubleValue());
    }

    public void moveLeft() {
        if(getLayoutX() - speed.doubleValue() < 0)
            setLayoutX(0);
        else
            setLayoutX(getLayoutX() - speed.doubleValue());
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double length) {
        this.position = position;
    }

    public void updatePosition() {

            position = getLayoutX() + (getFitWidth() / 2);

    }
}
