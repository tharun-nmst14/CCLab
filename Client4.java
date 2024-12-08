//run java program in server from client
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client4 {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server's address
        int port = 5003;              // Server's port number

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Streams to communicate with the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Get the Java file's path
            System.out.print("Enter the path to the Java file to run on the server: ");
            String javaFilePath = scanner.nextLine();

            // Send the Java file's path to the server
            writer.println(javaFilePath);

            // Read the response from the server
            String serverResponse = reader.readLine();
            System.out.println("Server response: " + serverResponse);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
