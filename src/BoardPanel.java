import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardPanel extends JPanel {
    /** Board to be painted. */
    private final Board board;
    private final Game game;

    public BoardPanel(Board board, Game game) {
        this.board=board;
        this.game = game;
        setLayout(new GridLayout(15,15));
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.GRAY));
    }
    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        g.setColor(new Color(235, 236, 194));
        g.fillRect(0,0,d.width,d.height);
        int boardWidth = d.width;
        int startX = 0;
        int boardHeight= d.height;
        int startY = 0;
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        g.setColor(new Color(255, 206, 11, 255));
        g.fillRect(startX, startY, boardWidth, boardHeight);
        g.setColor(new Color(0, 0,0));
        int row = startY;
        for(int i =0; i<15;i++) {
            g.drawLine(startX, row, boardWidth+startX,row);
            row+=boardHeight/15;
        }
        int col = startX;
        for(int i =0; i<15;i++) { //draw vertical lines
            g.drawLine(col, startY, col, startY+boardHeight);
            col+=boardWidth/15;
        }
        drawPieces(g, boardWidth, boardHeight);
    }
    public void drawPieces(Graphics g, int width, int height) {
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.isOccupied(i, j)) {
                    g.setColor(board.playerAt(i,j, game.player1, game.player2).color);
                    int pieceSize;
                    pieceSize = 20;
                    g.fillOval(25*i- pieceSize /2, 25*j- pieceSize /2, pieceSize, pieceSize);
                    if(board.isWin()){
                        if(board.isInWinningRow(i,j)){
                            Iterable< Board.Place> winning = board.winningRow();
                            for(Board.Place win: winning){
                                System.out.println(win);
                            }
                            g.setColor(Color.red);
                            g.fillOval(25*i- pieceSize /2, 25*j- pieceSize /2, pieceSize, pieceSize);
                        }
                    }
                }
            }
        }
    }
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(375, 375);
    }
}
