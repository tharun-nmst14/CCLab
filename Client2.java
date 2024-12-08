//two way communication
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server address
        int port = 5000;              // Port number to connect to

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Communication loop
            String serverMessage;
            while (true) {
                // Send a message to the server
                System.out.print("Client: ");
                String clientMessage = scanner.nextLine();
                writer.println(clientMessage);

                // Exit condition for the client
                if (clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client is disconnecting...");
                    break;
                }

                // Read the response from the server
                serverMessage = reader.readLine();
                if (serverMessage == null || serverMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Server disconnected.");
                    break;
                }
                System.out.println("Server: " + serverMessage);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
