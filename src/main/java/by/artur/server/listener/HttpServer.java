package by.artur.server.listener;


import java.net.Socket;
import java.net.ServerSocket;

public class HttpServer {
    public static void main(String[] args) throws Throwable{
        ServerSocket ss = new ServerSocket(5047);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }
}
