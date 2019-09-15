package application.Graphics.item.gameObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Note extends Circle {

        private final String BLACK_CHIP = "src/resources/images/notes/chipBlackWhite_border.png";
        private final String BLUE_CHIP = "src/resources/images/notes/chipBlueWhite_border.png";
        private final String GREEN_CHIP = "src/resources/images/notes/chipGreenWhite_border.png";
        private final String RED_CHIP = "src/resources/images/notes/chipRedWhite_border.png";
        private final String ORANGE_CHIP = "src/resources/images/notes/chipOrangeWhite_border.png";
        private final String BLACK_PIECE = "src/resources/images/notes/pieceBlack_single10.png";
        private final String BLUE_PIECE = "src/resources/images/notes/pieceBlue_single09.png";
        private final String GREEN_PIECE = "src/resources/images/notes/pieceGreen_single10.png";
        private final String PURPLE_PIECE = "src/resources/images/notes/piecePurple_single11.png";
        private final String RED_PIECE = "src/resources/images/notes/pieceRed_single11.png";
        private final String YELLOW_PIECE = "src/resources/images/notes/pieceYellow_single10.png";


    private String[] colors = {
            BLACK_CHIP,
            BLUE_CHIP,
            GREEN_CHIP,
            RED_CHIP,
            ORANGE_CHIP
            /*BLACK_PIECE,
            BLUE_PIECE,
            GREEN_PIECE,
            PURPLE_PIECE,
            RED_PIECE,
            YELLOW_PIECE,*/
    };
        private double bottomBorder;
        private DoubleProperty speed;

        public Note(double X, double Y,double frameBeat,int colorIndex) {
            super();
            setFill(new ImagePattern(new Image(Paths.get(new File(colors[colorIndex]).getPath()).toUri().toString())));
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
