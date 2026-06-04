package Main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the frame or window to be closed
        window.setResizable(false);
        window.setTitle("Sokoban - Level 1");
        
        GamePanel gamePanel = new GamePanel(window);
        window.add(gamePanel);
        
        window.pack(); // makes the window sized to fit preffered size and layouts of its subcomponents
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.startGameThread();
    }
}