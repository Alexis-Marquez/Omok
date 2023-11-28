import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends NetworkGame{
    public Server(NetworkPlayer opponent, Game game) {
        super(game);
        Socket socket;
        this.opponent = opponent;
        try {
            socket = new ServerSocket(8000).accept();
            receiveMessages(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
