package gol;



import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

 class Board extends JPanel {
     private final int BOARD_WIDTH  = 100;
     private final int BOARD_HEIGHT = 100;
     private final int INTERVAL     = 500;
     
     private boolean[][] board;
     private boolean[][] nb;
     
     private Random rand;
     private Timer timer;
     private int generation;
     private JLabel footer;

     /******************************************************************/
     
     public Board(int i, JLabel footer) {
         rand = new Random();
         this.footer = footer;
         initBoard(i);
     }
     
     
     private void initBoard(int x){
         board = new  boolean[BOARD_WIDTH][BOARD_HEIGHT];
         nb = new  boolean[BOARD_WIDTH][BOARD_HEIGHT];

         for (int j = 0; j < BOARD_WIDTH; j++) {
             for (int i = 0; i < BOARD_HEIGHT; i++) {
                 board[j][i] = (rand.nextInt(100) < x) ? true : false ;
             }
         }
         footer.setText("Generation: " + generation);
         setFocusable(true);
     }
     
     
     /******************************************************************/
     
    private int squareWidth() {
        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

     /******************************************************************/

     private int squareHeight() {
         
         return (int) getSize().getHeight() / BOARD_HEIGHT;
     }
    

     /******************************************************************/
     

   @Override
   public void paintComponent(Graphics g) {
       super.paintComponent(g);
       doDrawing(g);
   }

     /******************************************************************/
     
     private void  drawGrid(Graphics g) {
         var size = getSize();
         int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
           
        for (int i = 0; i <= BOARD_HEIGHT; i++) {
            int y= i * squareHeight();
            for (int j = 0; j <= BOARD_WIDTH; j++) {
                int x= j * squareWidth();                
                g.drawRect(x,boardTop+y, squareWidth(), squareHeight());
            }
        }
     }

     /******************************************************************/

     private void drawSquares(Graphics g) {
         var size = getSize();
         int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
         Color c = g.getColor();
         
         for (int i = 0; i < BOARD_HEIGHT; i++) {
             int y= i * squareHeight();
             for (int j = 0; j < BOARD_WIDTH; j++) {
                 int x= j * squareWidth();
                 if(board[j][i]) {
                     g.setColor(new Color(204, 102, 102));
                     g.fillRect(x+1, boardTop + y +1,
                                squareWidth()-1, squareHeight() - 1);
                     g.setColor(c);
                 }                     
             }
         }
     }
     
     /******************************************************************/

     public void doDrawing(Graphics g) {
         drawGrid(g);
         drawSquares(g);
     }

     /******************************************************************/

     private int getNeighbours(int x, int y) {
         int count =0;
         int lx =-1;
         int rx = 1;
         int ly =-1;
         int ry = 1;
         
         if(x==0) lx=0;
         if(x==BOARD_WIDTH-1) rx=0;

         if(y==0) ly=0;
         if(y==BOARD_HEIGHT-1) ry=0;
                                   
         for(int i=lx; i<= rx;i++) {
             for(int j=ly; j<= ry; j++) {
                 count += board[x+i][y+j] ? 1: 0;
             }
         }

         count -= board[x][y] ? 1: 0;
         return count;
     }

     /******************************************************************/

     void start() {
         
        timer = new Timer(INTERVAL, new Ticks());
        timer.start();
    }
     
     /******************************************************************/

     public void update() {
         boolean[][] temp = nb;
         
         for (int j = 0; j < BOARD_WIDTH; j++) {
             for (int i = 0; i < BOARD_HEIGHT; i++) {
                 int n = getNeighbours(j, i);
                 nb[j][i] = false;
                 if(!board[j][i] && n==3) nb[j][i] = true; 
                 else if (board[j][i] && (n==2 || n==3))  nb[j][i] = true;
             }
         }
         nb = board;
         board = temp;
         generation +=1;
         footer.setText("Generation: " + generation);
     }
 


     /******************************************************************/

     private class Ticks implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) { tick(); }
     }

          /******************************************************************/
     
     private void tick() {
         update();
         repaint();
     }
 }
