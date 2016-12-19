/**
 * Classname: Position.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
public class Position {

    private int x;
    private int y;

    /**
     * Creates a position which is made up of a x value and an y value.
     * @param x The x value of the position.
     * @param y The y value of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value of the position.
     * @return The x value of the position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value of the position.
     *
     * @return The y value of the position.
     */
    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    /**
     *Determines whether two positions are the same or not.
     * @param o An Object
     * @return True if the given position has the same coordinates as the
     * position, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     *Creates a hashcode for a position in the form of an integer.
     * @return A hash value in the form of an integer.
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}