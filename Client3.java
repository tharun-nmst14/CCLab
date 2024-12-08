//run a app in Server from client
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client3 {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server address
        int port = 5002;              // Server port number

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Streams to communicate with the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Get the application/command to run
            System.out.print("Enter the command or application to run on the server: ");
            String appCommand = scanner.nextLine();

            // Send the command to the server
            writer.println(appCommand);

            // Read the response from the server
            String serverResponse;
            System.out.println("Server response:");
            while ((serverResponse = reader.readLine()) != null) {
                System.out.println(serverResponse);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
