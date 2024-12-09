import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LinkClient {
    public static void main(String[] args) {
        String host = "127.0.0.1"; // Replace with the server's IP address if necessary
        int port = 5009;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to the server.");

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter a URL to open (or type QUIT to exit): ");
                String url = scanner.nextLine();

                output.println(url); // Send the URL to the server

                if (url.equalsIgnoreCase("QUIT")) {
                    System.out.println("Disconnecting from server...");
                    break;
                }

                String response = input.readLine(); // Get the server's response
                System.out.println("Server: " + response);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
