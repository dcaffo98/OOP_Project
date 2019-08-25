package application.Graphics.item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerBar extends ImageView {

    public PlayerBar(String url) {
        super(url);
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if(newValue != null) {
                    double parentWidth = ((Pane) newValue).getWidth();
                    double parentHeight = ((Pane) newValue).getHeight();
                    setFitWidth(parentWidth * 0.08);
                    setFitHeight(parentHeight * 0.03);
                    resizeRelocate((parentWidth - getFitWidth()) / 2, parentHeight - getFitHeight() * 1.5, getFitWidth(), getFitHeight());
                    updateSize();
                }
            }
        });
    }

    public void updateSize() {
        ((Pane) getParent()).widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setFitWidth(newValue.doubleValue() * 0.08);
            }
        });

        ((Pane) getParent()).heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setFitHeight(newValue.doubleValue() * 0.03);
                setLayoutY(newValue.doubleValue() - getFitHeight() * 1.5);
            }
        });
    }

    public void moveRight() {
        setLayoutX(getLayoutX() + 20);
    }

    public void moveLeft() {
        setLayoutX(getLayoutX() - 20);
    }
}
