//two way communication
import java.io.*;
import java.net.*;

public class Server2 {
    public static void main(String[] args) {
        int port = 5000; // Port number the server will listen on

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Communication loop
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Client: " + clientMessage);

                // Exit condition
                if (clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                // Respond to the client
                System.out.print("Server: ");
                String serverMessage = consoleReader.readLine();
                writer.println(serverMessage);

                // Exit condition for the server
                if (serverMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Server is shutting down.");
                    break;
                }
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
