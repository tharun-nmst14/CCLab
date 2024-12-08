import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server address
        int port = 5006;              // Port number to connect to

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Output stream to send data to the server
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            // Scanner for user input
            Scanner scanner = new Scanner(System.in);
            String message;

            // Continuously take input until "Over" is entered
            do {
                System.out.print("Enter a message to send to the server (type 'Over' to exit): ");
                message = scanner.nextLine();

                // Send the message to the server
                writer.println(message);

                System.out.println("Message sent to server: " + message);
            } while (!message.equalsIgnoreCase("Over")); // Exit loop if user enters "Over"

            System.out.println("Disconnected from server.");
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
