package mortytheplatypus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkUtil {
    private DataInputStream in;
    private DataOutputStream out;
    private static NetworkUtil instance;

    private NetworkUtil() {
        try {
            Socket socket = new Socket("127.0.0.1", 44444);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Issue in NetworkUtil class!");
        }
    }

    public void send(String message){
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive(){
        try {
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NetworkUtil getInstance() {
        if (instance==null) instance = new NetworkUtil();
        return instance;
    }
}
