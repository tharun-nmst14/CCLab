import java.io.*;
import java.net.*;
import java.util.Scanner;
 class vedioclient {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server address
        int port = 5002;              // Server port number

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Streams to communicate with the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Get the filename to open
            System.out.print("Enter the file or video name to open on the server: ");
            String fileName = scanner.nextLine();

            // Send the filename to the server
            writer.println(fileName);

            // Read and print the server response
            String serverResponse = reader.readLine();
            System.out.println("Server response: " + serverResponse);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}

