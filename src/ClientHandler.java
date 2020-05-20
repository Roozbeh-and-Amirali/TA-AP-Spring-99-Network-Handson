import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	ClientHandler( Socket socket ) throws Exception {
		this.socket = socket;
		this.dis = new DataInputStream( socket.getInputStream() );
		this.dos = new DataOutputStream( socket.getOutputStream() );
	}

	@Override
	public void run() {

		try {
			while (true) {
				String message = this.dis.readUTF();
				System.out.println( message );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}
}
