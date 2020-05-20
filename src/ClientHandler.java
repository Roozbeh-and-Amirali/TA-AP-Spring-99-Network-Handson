import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

    	// Get a username from client. Check if it is unique. if not, send an error message to client and wait for another username.
        try {
            boolean isUsernameOk = false;
            String username = "";
            while (!isUsernameOk) {
                username = this.dis.readUTF();
                if (Server.users.containsKey(username)) {
                    String errorMessage = "err: UserName already exists... please enter another one: ";
                    this.dos.writeUTF(errorMessage);
                } else {
                    String okMessage = "OK: Username was OK.";
                    this.dos.writeUTF(okMessage);
                    isUsernameOk = true;
                }
            }
            Server.users.put(username, this);       // Create the user in server
            System.out.println("User " + username + " Connected!");

            while (true) {
                String targetName = this.dis.readUTF();     // Get a string from client. It's supposed to be the target username.
                String message = this.dis.readUTF();        // Get another string from client, indicating message.
                ClientHandler targetHandler = Server.users.get(targetName);     // Get the handler for the target username.
                targetHandler.dos.writeUTF(username + " says: " + message);     // Write the message to target username's outputstream.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
