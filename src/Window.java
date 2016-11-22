import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{


    public Window(String title, int width,int height){

        super(title);
        setSize(new Dimension(width,height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false); //Temporary
    }

}
