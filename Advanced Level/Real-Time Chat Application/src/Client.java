import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Start a thread to read messages from the server
            new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Main thread reads input from keyboard and sends to server
            String userInput;
            while ((userInput = keyboard.readLine()) != null) {
                out.println(userInput);
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
 }
}
}