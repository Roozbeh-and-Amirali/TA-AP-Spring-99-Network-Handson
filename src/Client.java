import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost",3000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        Scanner in = new Scanner( System.in );
        while ( true ) {
            String s = in.nextLine();
            dos.writeUTF( s );
        }

    }
}
