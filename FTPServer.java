import java.io.*;
import java.net.*;

public class FTPServer {
    public static void main(String[] args) {
        int port = 3001; // Port number to listen on
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            // Create a server socket and start listening
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            // Wait for a connection
            socket = serverSocket.accept();
            System.out.println("Client connected");

            // Input stream to receive data from the client
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            
            // Read the file name from the client
            String fileName = dis.readUTF();
            System.out.println("Receiving file: " + fileName);

            // Ensure the "received_files" directory exists
            File directory = new File("received_files");
            if (!directory.exists()) {
                directory.mkdir(); // Create directory if it doesn't exist
            }

            // Create a file output stream to save the received file
            FileOutputStream fos = new FileOutputStream(directory.getAbsolutePath() + File.separator + fileName);
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read file data from the client and write to the file
            while ((bytesRead = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("File " + fileName + " received successfully.");

            // Close streams
            fos.close();
            dis.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the server socket and client socket
            try {
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
