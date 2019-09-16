package application.Graphics.item.gameObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Note extends Circle {

        private final String BLACK_CHIP = "src/resources/images/notes/chipBlack.png";
        private final String BLUE_CHIP = "src/resources/images/notes/chipBlue.png";
        private final String DARK_GREEN_CHIP = "src/resources/images/notes/chipDarkGreen.png";
        private final String GRAY_CHIP = "src/resources/images/notes/chipGray.png";
        private final String GREEN_CHIP = "src/resources/images/notes/chipGreen.png";
        private final String LIGHT_BLUE_CHIIP = "src/resources/images/notes/chipLightBlue.png";
        private final String ORANGE_CHIP = "src/resources/images/notes/chipOrange.png";
        private final String PURPLE_CHIP = "src/resources/images/notes/chipPurple.png";
        private final String RED_CHIP = "src/resources/images/notes/chipRed.png";
        private final String YELLOW_CHIP = "src/resources/images/notes/chipYellow.png";
        private String[] colors = {BLACK_CHIP, BLUE_CHIP, DARK_GREEN_CHIP, GRAY_CHIP, GREEN_CHIP, LIGHT_BLUE_CHIIP, ORANGE_CHIP, PURPLE_CHIP, RED_CHIP, YELLOW_CHIP };
        private ArrayList<Color> colors2 = new ArrayList<>(Arrays.asList(Color.CORAL, Color.BLUE, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHTBLUE, Color.FUCHSIA, Color.GREEN, Color.GRAY, Color.SIENNA));
        private double bottomBorder;
        private DoubleProperty speed;

        public Note(double X, double Y,double frameBeat,int colorIndex) {
            super();
            //setFill(new ImagePattern(new Image(Paths.get(new File(colors[colorIndex]).getPath()).toUri().toString())));
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
