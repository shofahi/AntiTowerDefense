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
     * The method returns the new Position for going one step down
     * @return return the new Position
     */
    public Position getPosToSouth(){

        return new Position(x,y+20);

    }
    /**
     * Method returns the new position for going one step up
     * @return  the new position
     */
    public Position getPostoNorth(){

        return new Position(x,y-20);

    }
    /**
     * This method return the new Position for going one step to the left
     * @return the new position
     */
    public Position getPosToWest(){
        return new Position(x-20,y);

    }
    /**
     * The method returns the new position for going one step to the right
     * @return the new position
     */
    public Position getPosToEast(){
        return new Position (x+20,y);

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