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

        public Note(double X, double Y,double frameBeat) {

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
                        //la velocità dipende dai bpm, quando l'ottava nota viene creata una arriva in fondo a ritmo
                        speed.bind( ((Pane) getParent()).heightProperty().divide(frameBeat * 8));   
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
