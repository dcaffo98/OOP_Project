package application.Graphics.item;

public class SongScoreTableRow {

    private int index;
    private String name;
    private int score;

    public SongScoreTableRow(int index, String name, int score) {

        this.index = index;
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
}
