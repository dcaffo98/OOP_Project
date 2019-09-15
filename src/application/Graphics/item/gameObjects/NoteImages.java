package application.Graphics.item.gameObjects;

import java.io.File;
import java.nio.file.Paths;

public enum NoteImages {
    BLUE(Paths.get(new File("../../../resources/images/notes/blueBall.png").getPath()).toUri().toString()),
    GREEN(Paths.get(new File("src/resources/images/notes/greenBall.png").getPath()).toUri().toString()),
    RED(Paths.get(new File("src/resources/images/notes/redBall.png").getPath()).toUri().toString()),
    YELLOW(Paths.get(new File("src/resources/images/notes/yellowBall.png").getPath()).toUri().toString());

    NoteImages(String imagePath) {
    }
}
