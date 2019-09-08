package application.Graphics.item.gameObjects;

import javafx.scene.shape.Circle;

public class Note extends Circle {

        double bottomBorder;

        public Note(double X, double Y) {

            super();
            setCenterX(X);
            setCenterY(Y);
            setRadius(25);
            setBottomBorder(getCenterY() + getRadius());


        }


        public void updatePosition() {

            setCenterY(getCenterY() + 2);
            setBottomBorder(getCenterY() + getRadius());
        }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(double bottomBorder) {
        this.bottomBorder = bottomBorder;
    }
}
