import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost",3000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Scanner in = new Scanner( System.in );
        boolean isUsernameOk = false;
        while (!isUsernameOk){
            System.out.println("Enter your username pleaseee: ");
            String username = in.nextLine();
            dos.writeUTF(username);
            String userValidation = dis.readUTF();
            if (userValidation.startsWith("OK"))
                isUsernameOk = true;
            else
                System.out.println("UserName already exists");
        }

        Thread messageListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message = dis.readUTF();
                    System.out.println("Incoming message: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        messageListener.start();

        while ( true ) {
            System.out.println("Enter target's username: ");
            String targetName = in.nextLine();
            dos.writeUTF(targetName);
            System.out.println("Enter message body: ");
            String s = in.nextLine();
            dos.writeUTF( s );

        }

    }
}
