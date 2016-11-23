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
    	Position pos = getPos();
    	int k = 1;
    	
    	pos.setX(pos.getX()-range);
    	System.out.println("startX: "+pos.getX());
    	pos.setY(pos.getY()-range);
    	int startY = pos.getY();
    	System.out.println("startY: "+startY);
    	
    	System.out.println("length:"+reach.length);
    	reach[0] = pos;
    	
    	for(int i = 0; i < this.range*2; i++){
    		pos.setX(pos.getX()+1);
    		pos.setY(startY);
    		for(int j = 0; j < this.range*2; j++){
    			pos.setY(pos.getY()+1);
    			
    			reach[k] = pos;
    			System.out.println("X: " + pos.getX() + ", Y: " + pos.getY());
    			k++;
    		}
    	}
    	
    	for (int x = 0; x < reach.length; x++){
    		System.out.println(x+" - "+"X:" + reach[x].getX() + " Y:" + reach[x].getY());
    	}
    	
    	return reach;
    }
}
