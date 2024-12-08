//run a app in Server from client
import java.io.*;
import java.net.*;

public class Server3 {
    public static void main(String[] args) {
        int port = 5002; // Server port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Streams to communicate with the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read the command from the client
            String appCommand = reader.readLine();
            System.out.println("Received request to run: " + appCommand);

            try {
                // Execute the command
                Process process = Runtime.getRuntime().exec(appCommand);

                // No need to wait for command output for GUI apps like VLC
                writer.println("Command executed: " + appCommand + ". Check your server's desktop environment.");
            } catch (Exception e) {
                // Send an error message to the client
                writer.println("Error while running the command: " + e.getMessage());
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
