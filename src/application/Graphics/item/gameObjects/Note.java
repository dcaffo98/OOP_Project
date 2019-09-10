package application.Graphics.item.gameObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Note extends Circle {

        private double bottomBorder;
        private DoubleProperty speed;

        public Note(double X, double Y) {

            super();
            setCenterX(X);
            setCenterY(Y);
            setRadius(15);
            setBottomBorder(getCenterY() + getRadius());
            speed = new SimpleDoubleProperty();
            parentProperty().addListener(new ChangeListener<Parent>() {
                @Override
                public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                    if(newValue != null) {
                        speed.bind( ((Pane) getParent()).heightProperty().multiply(0.0041));
                    }
                }
            });

        }


        public void updatePosition() {

            setCenterY(getCenterY() + speed.doubleValue());
            setBottomBorder(getCenterY() + getRadius());
        }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(double bottomBorder) {
        this.bottomBorder = bottomBorder;
    }
}
