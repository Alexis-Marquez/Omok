import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends NetworkGame {
    Socket socket;

    public Server(NetworkPlayer opponent, Game game) {
        super(game);
        this.opponent = opponent;
    }

    @Override
    public void start() {
        super.start();
        try {
            socket = new ServerSocket(8500).accept();
            receiveMessages(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
