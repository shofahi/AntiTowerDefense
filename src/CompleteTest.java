/**
 * Classname: CompleteTest.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

public class CompleteTest {

    public static void main(String[] args) {

        RunGame game;
        if (args.length == 0 ){
            game = new RunGame("Anti Tower Defense Beta");
        }else
            game = new RunGame("Anti Tower Defense Beta",args[0]);

        game.startGame();
    }

}
