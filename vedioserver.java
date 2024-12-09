	import java.io.*;
import java.net.*;
import java.awt.Desktop;

 class vedioserver {
    public static void main(String[] args) {
        int port = 5002; // Server port number

        // Get the home directory
        String homeDirectory = System.getProperty("user.home");

        System.out.println("Server is running and waiting for connections...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // Accept client connections
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                // Streams to communicate with the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                // Read the filename from the client
                String fileName = reader.readLine();
                System.out.println("Received request to open file: " + fileName);

                // Construct the full path to the file in the home directory
                File file = new File(homeDirectory, fileName);

                if (file.exists() && !file.isDirectory()) {
                    try {
                        // Open the file on the server
                        Desktop.getDesktop().open(file);
                        writer.println("File opened successfully: " + fileName);
                    } catch (IOException ex) {
                        writer.println("Error opening the file: " + ex.getMessage());
                    }
                } else {
                    writer.println("File not found in home directory: " + fileName);
                }

                socket.close();
            }
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
