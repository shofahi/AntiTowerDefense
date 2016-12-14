/**
 * Created by Samuel on 2016-12-12.
 */
public class DatabaseModel {
    private int id;
    private int level;
    private String name;
    private int score;

    public DatabaseModel(){

    }

    public DatabaseModel(int id,  String name, int level, int score){
        this.id = id;
        this.level = level;
        this.name = name;
        this.score = score;

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString(){
        return String.format("%d\t\t%s\t\t%d", id, name, score);
    }
}
