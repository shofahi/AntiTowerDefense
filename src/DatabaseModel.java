/**
 * Classname: DatabaseModel.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
public class DatabaseModel {
    private int id;
    private int level;
    private String name;
    private int score;

    /**
     * Empty constructor - Used by database when variables are unknown
     */
    public DatabaseModel(){

    }

    /**
     * Constructor for DatabaseModel.
     * @param id Unique ID for the database entry
     * @param name Name of the entry
     * @param level Level reached
     * @param score The score
     */
    public DatabaseModel(int id,  String name, int level, int score){
        this.id = id;
        this.level = level;
        this.name = name;
        this.score = score;

    }

    /**
     * Override toString to make a neat format when printing entry
     * @return
     */
    @Override
    public String toString(){
        return String.format("%d\t\t%s\t\t%d\t\t%d", id, name, level, score);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
