import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3000);

        while ( true ) {
            Socket socket = serverSocket.accept();
            ClientHandler temp = new ClientHandler( socket );
            ( new Thread( temp ) ).start();

        }

    }
}
