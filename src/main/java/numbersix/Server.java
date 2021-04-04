package numbersix;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(6500);
            System.out.println("Server start:");
            socket = server.accept();
            System.out.printf("Client [%s] connected. \n", socket.getInetAddress());

            Scanner in =  new Scanner(socket.getInputStream());
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            final Scanner scanner = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String msg = scanner.nextLine();
                        out.println(msg);
                    }
                }
            }).start();
            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/end")) break;
                System.out.println("Client: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
                System.out.println("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}