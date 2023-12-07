import java.net.Socket;

public abstract class NetworkGame extends Thread {
    protected NetworkPlayer opponent;
    protected Socket socket;
    protected Game game;
    NetworkAdapter network;

    public NetworkGame(Game game) {
        this.game = game;
    }

    void receiveMessages(Socket socket) {
        this.socket = socket;
        network = new NetworkAdapter(socket);
        network.setMessageListener((type, x, y) -> {
            switch (type) {
                case PLAY:
                    game.gui.setVisibility(true);
                    game.gui.boardDrawing.repaint();
                    network.writePlayAck(true, false);
                    break;
                case PLAY_ACK:
                    game.init(1, true);
                    game.gui.setVisibility(true);
                    game.gui.boardDrawing.repaint();
                    break;
                case MOVE:
                    opponent.pickPlace(x, y);
                    game.nextTurn();
                    network.writeMoveAck(x, y);
                    game.gui.boardDrawing.repaint();
                    break;
                case MOVE_ACK:
                    game.nextTurn();
                    game.gui.boardDrawing.repaint();
                    break;
                case QUIT:
                    getSocket().close();
                    network.close();
                    break;
            }
        });
        network.receiveMessagesAsync();
    }

    NetworkAdapter getNetwork() {
        return this.network;
    }

    public Socket getSocket() {
        return socket;
    }
}
