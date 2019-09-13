package application.Graphics.FXML;

import application.Graphics.item.stages.MainStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ResultPaneController {
    
    private String username;

    @FXML
    private BorderPane resultBorderPane;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label hitNotesLabel;

    @FXML
    private Label missedNotesLabel;

    @FXML
    private Label maxComboLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
    }

    @FXML
    public void exitButtonClicked(ActionEvent event) throws Exception {
        MainStage mainStage = new MainStage(800, 600, "RythmUp");
        mainStage.show();
        ((Stage) exitButton.getScene().getWindow()).close();

    }

    @FXML
    public void saveButtonClicked(ActionEvent event) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Insert a username");
        ((Stage) textInputDialog.getDialogPane().getScene().getWindow()).initStyle(StageStyle.UNDECORATED);
        textInputDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                username = textInputDialog.getEditor().getText();
                if (username.isBlank()) {
                    System.out.println("Empty username or null username");
                    dialogEvent.consume();
                }
                else {
                    System.out.println(username);
                }
            }
        });
        textInputDialog.showAndWait();
        System.out.println("YOUR USERNAME: " + username);
    }


    public void setData(int maxCombo, String songName, int score, int missedNotes, int hitNotes) {
        scoreLabel.setText(((Integer) score).toString());
        maxComboLabel.setText("Max combo: " + ((Integer) maxCombo).toString());
        hitNotesLabel.setText("Hit notes: " + ((Integer) hitNotes).toString());
        missedNotesLabel.setText("Missed notes: " + ((Integer) missedNotes).toString());
        songNameLabel.setText(songName.split("/")[songName.split("/").length - 1]);
    }

}
