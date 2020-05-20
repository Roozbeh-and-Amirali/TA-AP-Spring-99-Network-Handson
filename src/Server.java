import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    static Map <String, ClientHandler> users;

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(3000);

        users = new ConcurrentHashMap<>();

        while ( true ) {
            Socket socket = serverSocket.accept();      // wait for a client to connect
            ClientHandler temp = new ClientHandler( socket );   // create a thread for handling new client
            ( new Thread( temp ) ).start();     // start the thread
        }

    }
}
