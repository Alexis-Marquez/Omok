//import MenuGUI.java.MenuGUI;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class Game {
    public int port;
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
        if(currentTurn.name.equalsIgnoreCase("Your")){
            gui.footerText.setText(this.getCurrentTurn().name + " turn");
        }else {
            gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
        }
        myTurn = currentTurn.getClass() != NetworkPlayer.class;
    }

    private Player currentTurn;
    Player player1;
    Player player2;
    boolean myTurn;

    public void setHost(boolean host) {
        this.host = host;
    }

    boolean host = true;
    NetworkGame networkClient;

    public Game() {
        game = false;
        menu = new MenuGUI(this);
        gui = new GUI(board, this, menu.getFrame());
        this.strategy = "smart";
        port = new Random().nextInt((9000 - 8000) + 1) + 8000;
    }

    public static void main(String[] args) {
        new Game();
    }

    public void init(int numberPlayers, boolean network) throws UnknownHostException {
        port = new Random().nextInt((9000 - 8000) + 1) + 8000;
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
            if(host){
                player1 = new HumanPlayer("Your", '1', Color.BLACK);
                player2 = new NetworkPlayer("Opponent", this.board, Color.WHITE);
                myTurn = true;
            } else {
                player1 = new NetworkPlayer("Opponent", this.board, Color.BLACK);
                player2 = new HumanPlayer("Your", '2', Color.WHITE);
                myTurn = false;
            }
        } else {
            player1 = new HumanPlayer("Player 1", '1', Color.BLACK);
            player2 = new HumanPlayer("Player 2", '2', Color.WHITE);
            myTurn = true;
        }

        currentTurn = player1;
        if(currentTurn.name.equalsIgnoreCase("Your")){
            gui.footerText.setText(this.getCurrentTurn().name + " turn");
        }else {
            gui.footerText.setText(this.getCurrentTurn().name + "'s turn");
        }
    }

    public void pickPlace(int x, int y) throws IOException {
        BigDecimal xC = BigDecimal.valueOf((double) x / 25);
        BigDecimal xCord = xC.setScale(0, RoundingMode.HALF_UP);
        BigDecimal yC = BigDecimal.valueOf((double) y / 25);
        BigDecimal yCord = yC.setScale(0, RoundingMode.HALF_UP);
        if (!board.isWin()) {
            if (myTurn) {
                if (board.isOccupied(xCord.intValue(), yCord.intValue())) {
                    if (board.isFull()) {
                        gui.footerText.setText("All places full, It's a draw!");
                        port = new Random().nextInt((9000 - 8000) + 1) + 8000;
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
                        port = new Random().nextInt((9000 - 8000) + 1) + 8000;
                        menu.setButtonText("Start New Game");
                        menu.gameOngoing = false;
                        if (network) {
                            networkClient.getSocket().close();
                            networkClient.getNetwork().writeQuit();
                            networkClient.getNetwork().close();
                            network = false;
                        }
                    } else {
                        if(!this.network) {
                            this.nextTurn();
                        }
                    }
                    gui.boardDrawing.repaint();
                }
            }
        } else {
            if(board.winner.equalsIgnoreCase("Your")) {
                gui.footerText.setText("You Have Won!");
            }
            else{
                gui.footerText.setText(board.winner + " has Won!");
            }
            menu.setButtonText("Start New Game");
            menu.gameOngoing = false;
            try {
                networkClient.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gui.boardDrawing.repaint();
        }
    }

    public void startServer() {
        new Thread(()->{
            networkClient = new Server((NetworkPlayer) player2, this);
            networkClient.start();
        }).start();
    }
    public void startClient(String hostIp, int port){
        new Thread(()->{
            try {
                networkClient = new Client((NetworkPlayer) player1, this, hostIp, port);
                networkClient.start();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}