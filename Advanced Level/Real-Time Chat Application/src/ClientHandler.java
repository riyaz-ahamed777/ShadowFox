import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String userName;
    private Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your username:");
            userName = in.readLine();
            System.out.println(userName + " has joined the chat.");

            broadcast(userName + " has joined the chat.", this);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(userName + ": " + message);
                broadcast(userName + ": " + message, this);
            }

        } catch (IOException e) {
            System.out.println(userName + " disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientHandlers.remove(this);
            broadcast(userName + " has left the chat.", this);
        }
    }

    private void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
}
}
