package application.Graphics.item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;

/*
Questa classe riceve come parametri un nodo figlio e un nodo genitore e si occupa di capire quando il figlio viene aggiunto allo scene-graph che
ha come root il suo genitore ("capisce quando il figlio viene stampato a schermo")
Utile nelle classi Controller per ottenere l'elemento root
 */

public abstract class ParentGetter {
    private Node child;
    private Node parent;

    public ParentGetter(Node child, Node parent) {
        setChild(child);
        child.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if (newValue != null) {
                    setParent(newValue);
                    parentGotten();
                }
            }
        });
    }

    public abstract void parentGotten();

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
