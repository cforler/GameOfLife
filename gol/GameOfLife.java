package gol;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

public class GameOfLife extends JFrame {
    private Board board;
    private final int WIDTH=1000;
    private final int HEIGHT=1000;

    
    public GameOfLife(int x) {
        JLabel footer = new JLabel();
        add(footer, BorderLayout.SOUTH);
        
        board = new Board(x,footer);
        board.setBorder(BorderFactory.createLineBorder(Color.black));
        add(board, BorderLayout.CENTER);
        board.start();
        
        setTitle("Conway's Game of Life");
        setSize(WIDTH+2, HEIGHT+40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        GameOfLife gof;
        if(args.length>0) gof =  new GameOfLife(Integer.parseInt(args[0]));
        else gof =  new GameOfLife(20);
        gof.setVisible(true);
    }
    
}
