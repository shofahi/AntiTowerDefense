
public class Defender {

    private Integer ID;
    private Position pos;
    private Integer dmg;
    private Integer range;
    private Integer fireRate;

    public Defender(Integer ID, Position pos, Integer dmg, Integer range, Integer fireRate){
    	this.ID = ID;
    	this.pos = pos;
    	this.dmg = dmg;
    	this.range = range;
    	this.fireRate = fireRate;
    }

    public void setID(Integer ID){
    	this.ID = ID;
    }

    public Integer getID(){
    	return ID;
    }

    public void setPos(int x, int y){
    	this.pos.setX(x);
    	this.pos.setY(y);
    }

    public Position getPos(){
    	return pos;
    }

    public void setFireRate(Integer fireRate){
    	this.fireRate = fireRate;
    }

    public Integer getFireRate(){
    	return fireRate;
    }

    public void setDmg(Integer dmg){
    	this.dmg = dmg;    	
    }

    public Integer getDmg(){
    	return dmg;
    }
    
    public double getDPS(){
    	return dmg * fireRate;
    }

    public void setRange(Integer range){
    	this.range = range;
    }

    public Integer getRange(){
    	return range;
    }
    
    public Position[] getReachableBlocks(){
    	Position[] reach = new Position[range*range*4];
    	int k = 0;

    	for(int i = this.pos.getX()-range; i < this.pos.getX()+range; i++){
    		for(int j = this.pos.getY()-range; j < this.pos.getY()+range; j++){
    			reach[k] = new Position(i,j);
    			k++;
    		}
    	}
    	
    	return reach;
    }
}
