import java.io.*;
import java.net.*;

public class Server1 {
    public static void main(String[] args) {
        int port = 5006; // Port number to listen on

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Input stream to receive data from the client
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String message;

            // Continuously read messages from the client
            while (true) {
                message = reader.readLine();  // Read the message from the client
                if (message == null || message.equalsIgnoreCase("Over")) {
                    // If message is "Over" or the client disconnects, stop the server
                    System.out.println("Client disconnected or sent 'Over'. Server is shutting down.");
                    break;
                }
                System.out.println("Message received: " + message);
            }

            socket.close(); // Close the socket after communication is done
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
