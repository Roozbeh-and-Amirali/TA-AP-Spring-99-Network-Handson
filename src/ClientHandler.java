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
            Server.users.put(username, this);
            System.out.println("User " + username + " Connected!");

            while (true) {
                String targetName = this.dis.readUTF();
                String message = this.dis.readUTF();
                ClientHandler targetHandler = Server.users.get(targetName);
                targetHandler.dos.writeUTF(username + " says: " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
