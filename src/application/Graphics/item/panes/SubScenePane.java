package application.Graphics.item.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
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
        //NOTA: Width e Height devono essere settati subito anche se il SubScenePane non è ancora visualizzato (vedi hasParent()), perchè altrimenti la MainSubScene avrà dimensioni nulle
        hasParent();
        setWidth(parent.getWidth() * 0.6);
        setHeight(parent.getHeight() * 0.5);
        //listener per ridimensionamento dinamico
        setPropertyListener();
        //a caso
        setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    public Pane getParentPane() {
        return parent;
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

    public void init() {
        setLayoutX(parent.getWidth() * 0.2);
        setLayoutY(parent.getHeight() * 0.4);
        setWidth(parent.getWidth() * 0.6);
        setHeight(parent.getHeight() * 0.5);
    }


    /*
    Listener per capire quando il SubScenePane viene aggiunto a una scena, controllando se la parentProperty è nulla o meno (se non è nulla significa che è stato aggiunto a uno scene graph e quindi è visualizzato).
    Quando viene visualizzato, ne setta le dimensioni e il layout.
    Altrimenti viene visualizzato male
     */
    public void hasParent() {
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if (newValue != null)
                    init();
            }
        });
    }

}
