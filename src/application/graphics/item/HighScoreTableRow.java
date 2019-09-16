package application.graphics.item;


public class HighScoreTableRow {
    private int index;
    private String songName;
    private String name;
    private int score;

    public HighScoreTableRow(String songName, String name, int score){

        this.songName = songName;
        this.score = score;
        this.name = name;

    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

}
