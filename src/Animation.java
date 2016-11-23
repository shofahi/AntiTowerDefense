import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

public class Animation {

	private BufferedImage step1;
	private BufferedImage step2;
	
	public Animation(){}
	
	public void startAnimation(){
		
	}
	
	public void stopAnimation(){
		
	}
	
	public static BufferedImage fetchImage(){
		LoadImage imgLoader = new LoadImage();
		System.out.println("Fetching image");
		BufferedImage tmp = imgLoader.loadTheImage("Monster1.png");

		
		return tmp;
	}
}
