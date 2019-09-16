package application.graphics.item.gameObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;

public class Note extends Circle {

        private ArrayList<Color> colors2 = new ArrayList<>(Arrays.asList(Color.CORAL, Color.BLUE, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHTBLUE, Color.FUCHSIA, Color.GREEN, Color.GRAY, Color.SIENNA));
        private double bottomBorder;
        private DoubleProperty speed;

        public Note(double X, double Y,double frameBeat,int colorIndex) {
            super();
            setFill(colors2.get(colorIndex));
            setStroke(Color.BLACK);
            setStrokeWidth(3);
            setCenterX(X);
            setCenterY(Y);
            setRadius(17);
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
