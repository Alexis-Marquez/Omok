//import MenuGUI.java.MenuGUI;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;

public class Game {
    MenuGUI menu;
    GUI gui;
    Board board = new Board(16);
    String strategy;
    boolean network;
    boolean game;

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn() {
        if (player1.equals(currentTurn)) {
            currentTurn = player2;
        } else
            currentTurn = player1;
        if (currentTurn.getClass() == CpuPlayer.class) {
            CpuPlayer cpu = (CpuPlayer) currentTurn;
            cpu.pickPlace();
            nextTurn();
        } else if (currentTurn.getClass() == CpuPlayerSmart.class) {
            CpuPlayerSmart cpu = (CpuPlayerSmart) currentTurn;
            cpu.pickPlace();
            nextTurn();
        }
        gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
        myTurn = currentTurn.getClass() != NetworkPlayer.class;
    }

    private Player currentTurn;
    Player player1;
    Player player2;
    boolean myTurn;
    boolean host = false;
    NetworkGame networkClient;

    public Game() {
        game = false;
        menu = new MenuGUI(this);
        gui = new GUI(board, this, menu.getFrame());
        this.strategy = "smart";
    }

    public static void main(String[] args) {
        new Game();
    }

    public void init(int numberPlayers, boolean network) throws UnknownHostException {
        this.network = network;
        board.setWon(false, "");
        board.clear();
        if (numberPlayers == 0) {
            if (strategy.equalsIgnoreCase("random")) {
                player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
                player2 = new CpuPlayer("Player 2", this.board, Color.WHITE);
                myTurn = true;
            } else {
                player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
                player2 = new CpuPlayerSmart("Player 2", this.board, Color.WHITE, (HumanPlayer) player1);
                myTurn = true;
            }
        } else if (numberPlayers == 1 && this.network) {
            if (host) {
                player1 = new HumanPlayer("Host", '1', Color.BLACK);
                player2 = new NetworkPlayer("Client", this.board, Color.WHITE);
                networkClient = new Server((NetworkPlayer) player2, this);
                myTurn = true;
            } else {
                player1 = new NetworkPlayer("Host", this.board, Color.BLACK);
                player2 = new HumanPlayer("Client", '2', Color.WHITE);
                networkClient = new Client((NetworkPlayer) player1, this);
                myTurn = false;
            }
        } else {
            player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
            player2 = new HumanPlayer("Player 2", '2', Color.WHITE);
            myTurn = true;
        }
        currentTurn = player1;
        gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
    }

    public void pickPlace(int x, int y) {
        BigDecimal xC = BigDecimal.valueOf((double) x / 25);
        BigDecimal xCord = xC.setScale(0, RoundingMode.HALF_UP);
        BigDecimal yC = BigDecimal.valueOf((double) y / 25);
        BigDecimal yCord = yC.setScale(0, RoundingMode.HALF_UP);
        if (!board.isWin()) {
            if (myTurn) {
                if (board.isOccupied(xCord.intValue(), yCord.intValue())) {
                    if (board.isFull()) {
                        gui.footerText.setText("All places full, It's a draw!");
                    }
                    gui.footerText.setText("Place is Occupied, try another intersection!");
                } else {
                    board.placeStone(xCord.intValue(), yCord.intValue(), this.getCurrentTurn());
                    if (network)
                        networkClient.getNetwork().writeMove(xCord.intValue(), yCord.intValue());
                    board.setWon(board.isWonBy(yCord.intValue(), xCord.intValue(), this.getCurrentTurn()),
                            this.getCurrentTurn().name);
                    if (board.isWin()) {
                        gui.footerText.setText(board.winner + " has Won!");
                        menu.setButtonText("Start New Game");
                        menu.gameOngoing = false;
                        networkClient.getNetwork().writeQuit();
                        networkClient.getNetwork().close();
                        network = false;
                    } else {
                        this.nextTurn();
                    }
                    gui.boardDrawing.repaint();
                }
            }
        } else {
            gui.footerText.setText(board.winner + " has Won!");
            menu.setButtonText("Start New Game");
            menu.gameOngoing = false;
            gui.boardDrawing.repaint();
        }
    }
}