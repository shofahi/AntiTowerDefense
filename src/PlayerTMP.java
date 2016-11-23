import java.awt.*;
import java.util.LinkedList;


public class PlayerTMP {

    Position pos;
    Position nextPos;

    private LinkedList<Position> visitedPosition = new LinkedList<>();
    private LinkedList<Position> path;

    public PlayerTMP(Position pos, LinkedList <Position> path){
        this.pos = pos;
        this.path = path;
        visitedPosition.add(pos);
        nextPos = null;
    }

    public void render(Graphics g){
        g.setColor(Color.pink);
        g.fillRect(pos.getX(),pos.getY(),20,20);
    }

    public boolean isMovable(Position pos) {
        return path.contains(pos);
    }

    public boolean posVisited(Position pos){

        return visitedPosition.contains(pos);
    }

    public String getTurn(){

        if(isMovable(pos.getPostoNorth()) && !posVisited(pos.getPostoNorth())){
           System.out.println("NORTH");
           nextPos = pos.getPosToEast();
           return "NORTH";
        }
        else if(isMovable(pos.getPosToEast()) && !posVisited(pos.getPosToEast())){
            nextPos = pos.getPosToEast();
            return "LEFT";
        }
        else if(isMovable(pos.getPosToSouth()) && !posVisited(pos.getPosToSouth())){
            System.out.println("South");

            nextPos = pos.getPosToSouth();
            return "SOUTH";
        }

        return "null";
    }
    public void update() {

        String turn = getTurn();

        if(nextPos != null){

            if(turn.equals("LEFT")){
                int tmpX = pos.getX();
                tmpX++;
                pos.setX(tmpX);
                visitedPosition.add(pos);

            }
            else if(turn.equals("SOUTH")){
                int tmpY = pos.getY();
                tmpY++;
                pos.setY(tmpY);
                visitedPosition.add(pos);

            }
            else if(turn.equals(("NORTH"))){
                int tmpY = pos.getY();
                tmpY--;
                pos.setY(tmpY);
                visitedPosition.add(pos);

            }
            else{
                nextPos = null;
            }
        }


    }

}
