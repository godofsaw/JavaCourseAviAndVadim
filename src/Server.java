
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }

    private void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1345);
        while (true) {
            Socket socket = serverSocket.accept();
            new ServerThread(socket).start();
        }
    }
}
