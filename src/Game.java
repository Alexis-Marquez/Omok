//import MenuGUI.java.MenuGUI;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Game {
    MenuGUI menu;
    GUI gui;
    Board board = new Board(16);
    String strategy;

    boolean game;
    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn() {
        if(player1.equals(currentTurn)){
            currentTurn = player2;
        }
        else currentTurn = player1;
        if(currentTurn.getClass()==CpuPlayer.class){
            CpuPlayer cpu = (CpuPlayer) currentTurn;
            cpu.pickPlace();
            nextTurn();
        } else if (currentTurn.getClass()==CpuPlayerSmart.class) {
            CpuPlayerSmart cpu = (CpuPlayerSmart) currentTurn;
            cpu.pickPlace();
            nextTurn();
        }
    }

    private Player currentTurn;
    Player player1;
    Player player2;
    public Game(){
        game = false;
        menu = new MenuGUI(this);
        gui = new GUI(board, this, menu.getFrame());
        this.strategy = "smart";
    }
    public static void main(String[] args) {
        new Game();
        }

    public void init(int numberPlayers){
        board.setWon(false, "");
        board.clear();
        if(numberPlayers==0){
            if(strategy.equalsIgnoreCase("random")) {
                player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
                player2 = new CpuPlayer("Player 2", this.board, Color.WHITE);
            }
            else{
                player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
                player2 = new CpuPlayerSmart("Player 2", this.board, Color.WHITE, (HumanPlayer) player1);
            }
        }
        else {
            player1 = new HumanPlayer("Player 1", '1',Color.BLACK);
            player2 = new HumanPlayer("Player 2", '2', Color.WHITE);
        }
        currentTurn = player1;
        gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
    }
    public void pickPlace(int x, int y){
        BigDecimal xC = BigDecimal.valueOf((double) x / 25);
        BigDecimal xCord = xC.setScale(0, RoundingMode.HALF_UP);
        BigDecimal yC = BigDecimal.valueOf((double) y / 25);
        BigDecimal yCord = yC.setScale(0, RoundingMode.HALF_UP);
        if (!board.isWin()) {
            if (board.isOccupied(xCord.intValue(), yCord.intValue())) {
                if (board.isFull()) {
                    gui.footerText.setText("All places full, It's a draw!");
                }
                gui.footerText.setText("Place is Occupied, try another intersection!");
            } else {
                board.placeStone(xCord.intValue(), yCord.intValue(), this.getCurrentTurn());
                board.setWon(board.isWonBy(yCord.intValue(), xCord.intValue(), this.getCurrentTurn()), this.getCurrentTurn().name);
                if (board.isWin()) {
                    gui.footerText.setText(board.winner + " has Won!");
                    menu.setButtonText("Start New Game");
                    menu.gameOngoing = false;
                } else {
                    this.nextTurn();
                    gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
                }
                gui.boardDrawing.repaint();
            }
        }else{
            gui.footerText.setText(board.winner + " has Won!");
            menu.setButtonText("Start New Game");
            menu.gameOngoing = false;
            gui.boardDrawing.repaint();
        }
    }
}