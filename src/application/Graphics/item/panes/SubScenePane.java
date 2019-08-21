package application.Graphics.item.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SubScenePane extends AnchorPane {

    private Pane parent;

    public SubScenePane (Pane parent) {
        super();
        this.parent = parent;
        //inizializzo le dimensioni della sottoscena
        setLayoutX(this.parent.getWidth() * 0.2);
        setLayoutY(this.parent.getHeight() * 0.4);
        setWidth(this.parent.getWidth() * 0.6);
        setHeight(this.parent.getHeight() * 0.5);
        //listener per ridimensionamento dinamico
        setPropertyListener();
        //a caso
        setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    public void setPropertyListener() {
        parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setLayoutX(parent.getWidth() * 0.2);
                setWidth(parent.getWidth() * 0.6);
            }
        });

        parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setLayoutY(parent.getHeight() * 0.4);
                setHeight(parent.getHeight() * 0.5);
            }
        });
    }
}
