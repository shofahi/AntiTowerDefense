import java.awt.*;
import java.util.LinkedList;


public class PlayerTMP {

    Position pos;
    Position nextPos;
    String turn = "EAST";

    private LinkedList<Block> turns;
    private Rectangle playerRect;

    public PlayerTMP(Position pos, LinkedList <Block> path){
        this.pos = pos;
        this.turns = path;
        nextPos = null;
        playerRect = new Rectangle(pos.getX(),pos.getY(),20,20);
    }

    public void render(Graphics g){
        g.setColor(Color.pink);
        g.fillRect(playerRect.x,playerRect.y,playerRect.width,playerRect.height);
    }


    public void getTurn(){
        for (int i = 0; i < turns.size();i++){

            if(playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNWEST){
                turn = "WEST";
            }
            else if(playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNSOUTH){
                turn = "SOUTH";
            }
            else if(playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNNORTH){
                turn = "NORTH";
            }
            else if(playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNEAST){
                turn = "EAST";
            }
        }
    }
    public void update(){

        getTurn();

        if(turn.equals("WEST")){
            playerRect.x--;

        }
        else if(turn.equals("SOUTH")){
            playerRect.y++;
        }
        else if(turn.equals("NORTH")){
            playerRect.y--;
        }
        else if(turn.equals("EAST")){
            playerRect.x++;
        }

    }
}
