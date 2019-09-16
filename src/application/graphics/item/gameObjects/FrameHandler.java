package application.graphics.item.gameObjects;

import application.graphics.FXML.PausePaneController;
import application.graphics.FXML.ResultPaneController;
import application.graphics.item.MongoDBConnector;
import application.graphics.item.panes.GameTopPane;
import application.graphics.item.stages.MainStage;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FrameHandler implements EventHandler<ActionEvent> {

      /*
         oggetto che si occupa di gestire ogni frame della GameScene e aggiornare collisioni e posizioni degli oggetti nella scena

     */

    private KeyCode code;
    private GameTopPane gameTopPane;
    private PlayerBar player;
    private Timeline timeline;
    private ArrayList<Note> notes;
    private AnchorPane gamePane;
    private MediaPlayer mediaPlayer;
    private int score;
    private int missedNotes;
    private int hitNotes;
    private int maxCombo;
    private int combo;
    private int frameCounter;
    private double bpm;
    private double frameBeat;
    private double lastNoteTime;
    private double endOfGenerationTime;
    private  MongoDBConnector mongoDBConnector;
    private  MainStage mainStage;
    private PausePaneController pausePaneController;



    public FrameHandler(AnchorPane gamePane, GameTopPane gameTopPane, PlayerBar player, Timeline timeline, MediaPlayer mediaPlayer, double bpm, MongoDBConnector mongoDBConnector, MainStage mainStage) {

        this.gamePane = gamePane;
        this.gameTopPane = gameTopPane;
        this.player = player;
        this.timeline = timeline;
        this.score = 0;
        this.frameCounter = 0;
        this.notes = new ArrayList<Note>();
        this.bpm = bpm;
        this.mediaPlayer = mediaPlayer;
        this.frameBeat = (1.0 / (bpm/60.0)) / 0.016; //formula che indica quanti frame devono passare per aggiungere una nota.
        this.lastNoteTime = (frameBeat * 8) * 16; //millisecondi necessari all'ultima nota per arrivare alla fine dello schermo
        this.endOfGenerationTime = mediaPlayer.getMedia().getDuration().toMillis() - lastNoteTime;
        this.maxCombo = 0;
        this.hitNotes = 0;
        this.missedNotes = 0;
        this.mongoDBConnector = mongoDBConnector;
        this.mainStage = mainStage;
    }

    public KeyCode getCode() {
        return code;
    }

    public void setCode(KeyCode code) {
        this.code = code;
    }



    /*
      metodo principale per la gestione della schermata di gioco

     */
    @Override
    public void handle(ActionEvent event) {


        gamePane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        frameCounter++;
        gameTopPane.getProgress().setProgress((mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getMedia().getDuration().toMillis()));

        /*
              entra nell'if alla fine della canzone e invoca il pannello dei risultati
         */

        if(mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getMedia().getDuration())) {
            timeline.stop();
            if (combo > maxCombo)
                maxCombo = combo;
            showResultPane();
        }


        /*

            controllo e gestione dei input da tastiera per spostamento e gestione menu di pausa

         */

        if (code != null) {
            switch (code) {
                case RIGHT:
                    player.moveRight();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
                case ESCAPE:
                    timeline.pause();
                    mediaPlayer.pause();
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../../FXML/PausePane.fxml"));
                        Parent pausePane = loader.load();
                        pausePaneController = loader.getController();
                        gamePane.getChildren().add(pausePane);
                        pausePaneController.setData(gamePane, this, mainStage);
                        pausePaneController.setLayout();
                        gamePane.getScene().setCursor(Cursor.DEFAULT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        /*

            richiama il metodo per aggiungere una nota quando il contatore dei frame raggiunge il frame desiderato

         */

        if ((frameCounter > this.frameBeat) && (mediaPlayer.getCurrentTime().toMillis() < endOfGenerationTime)) {
            addNote();
            frameCounter = 0;
        }

        /*

            se è presente almeno una nota sullo schermo richiama il metodo update


         */
        if (notes.size() > 0) {
            update();
        }
        


    }

    /*

       metodo che aggiorna la posizione di tutti gli oggetti all'interno della schermata di gioco

     */

    public void update() {
        Note toDelete = null;
        for (Note n: notes) {
            n.updatePosition();

            /* se la nota tocca il bordo inferiore della schermata significa
            che è stata mancata, aggiornamento degli attributi locali per il punteggio */
            if ((n.getBottomBorder() >= gamePane.getHeight())) {
                    toDelete = n;
                    this.missedNotes++;
                    score += combo * 3;
                    if (combo > maxCombo)
                        maxCombo = combo;
                    this.combo = 0;
                    gameTopPane.setShowCombo(combo);
                    gamePane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                }
            if (checkCollision(n)) {
                    toDelete = n;
            }
        }
        if (toDelete != null) {
            notes.remove(toDelete);
            gamePane.getChildren().remove(toDelete);
        }


    }

    /*
        metodo che si occupa di creare e aggiungere una nota alla schermata di gioco
     */

    public void addNote() {
        Random random = new Random();
        Note note = new Note(gameTopPane.getPrefWidth() / 2, gameTopPane.getPrefHeight(), frameBeat, random.nextInt(10));
        int leftBorder = (int)note.getRadius();
        int rightBorder = (int)gameTopPane.getPrefWidth() - (leftBorder);
        double randomInt;
        player.updatePosition();
        double possibleWidth = 0.2 * gamePane.getWidth();
        do {
            randomInt = random.nextInt(rightBorder - leftBorder) + leftBorder;
        } while ((randomInt < (player.getPosition() - possibleWidth)) || (randomInt > player.getPosition() + possibleWidth));
        if (notes.size() > 0) {
            Note previousNote = notes.get(notes.size() - 1);
            if (randomInt > (previousNote.getCenterX() + (possibleWidth * 1.2))) {
                randomInt = previousNote.getCenterX() + (possibleWidth * 1.2);
            } else if (randomInt < (previousNote.getCenterX() - (possibleWidth * 1.2))) {
                randomInt = previousNote.getCenterX() - (possibleWidth * 1.2);
            }
        }
        note.setCenterX(randomInt);
        notes.add(note);
        gamePane.getChildren().addAll(note);
    }

    /*

            metodo che si occupa di controllare se è avvenuta una collisione tra una nota e la playerBar

     */

    public boolean checkCollision(Note n) {
        if ((n.getBottomBorder() >= player.getLayoutY()) && (n.getCenterX() >= player.getLayoutX()) && (n.getCenterX() <= player.getLayoutX() + player.getFitWidth())   ) {
            this.hitNotes++;
            this.combo++;
            gameTopPane.setShowCombo(combo);
            score = score + 100 * combo;
            gameTopPane.setScore(getScore());
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*

        genera il pannello dei risultati della partita

     */
    public void showResultPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../FXML/ResultPane.fxml"));
            Parent resultPane = loader.load();
            ResultPaneController controller = loader.getController();
            controller.setData(maxCombo ,mediaPlayer.getMedia().getSource(), score, missedNotes, hitNotes, mongoDBConnector, mainStage);
            Stage tmp = ((Stage) gamePane.getScene().getWindow());
            tmp.setMinWidth(600);
            tmp.setMinHeight(400);
            tmp.setScene(new Scene(resultPane));
            //per centrare la scena
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            tmp.setX((screenBounds.getWidth() - tmp.getWidth()) / 2);
            tmp.setY((screenBounds.getHeight() - tmp.getHeight()) / 2);
            tmp.setResizable(false);
            tmp.getScene().getRoot().requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*

        necessario per far ripartire la timeline premendo il bottone di resume

     */
    public void onResumeRequest() {
        timeline.play();
        mediaPlayer.play();
        gamePane.getScene().setCursor(Cursor.NONE);
    }

    /*

        metodo per reimpostare la finestra di gioco all'inizio della canzone e giocare da capo

     */
    public void restart() {
        player.setLayoutX((gamePane.getWidth()  - player.getFitWidth()) / 2);
        gamePane.getChildren().removeAll(notes);
        notes = new ArrayList<Note>();
        timeline.playFrom(new Duration(0));
        mediaPlayer.seek(new Duration(0));
        mediaPlayer.play();
        this.score = 0;
        this.combo = 0;
        this.hitNotes = 0;
        this.missedNotes = 0;
        this.maxCombo = 0;
        gameTopPane.setShowCombo(combo);
        gameTopPane.setScore(getScore());
        gamePane.getScene().setCursor(Cursor.NONE);
    }

    /*

        richiama la resume sul menu di pausa quando viene invocato da tastiera tramire ENTER

     */

    public void resume() {

        pausePaneController.resume();


    }
}