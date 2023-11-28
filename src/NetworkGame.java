import java.net.Socket;
import java.net.UnknownHostException;

public abstract class NetworkGame extends Thread{
    protected NetworkPlayer opponent;
    private Socket socket;
    private Game game;
    NetworkAdapter network;

    public NetworkGame(Game game) {
        this.game = game;
    }

    void receiveMessages(Socket socket) {
        this.socket = socket;
        network = new NetworkAdapter(socket);
        network.setMessageListener(new NetworkAdapter.MessageListener() {
            public void messageReceived(NetworkAdapter.MessageType type, int x, int y) throws UnknownHostException {
                switch (type) {
                    case PLAY:
                        game.init(1, true);
                        game.network = true;
                        network.writePlayAck(true, false);
                        break;
                    case PLAY_ACK:
                        game.init(1, true);
                        game.network = true;
                        break;
                    case MOVE:
                        opponent.pickPlace(x,y);
                        game.nextTurn();
                        network.writeMoveAck(x,y);
                        game.gui.boardDrawing.repaint();
                        break;
                    case MOVE_ACK:
                        game.gui.boardDrawing.repaint();
                        break;
                    case CLOSE:
                        network.close();
                        break;
                }
            }
        });
        network.receiveMessagesAsync();
    }
    NetworkAdapter getNetwork(){
        return this.network;
    }
}
