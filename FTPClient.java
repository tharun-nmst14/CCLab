import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int port = 3001; // Port number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter File Name: ");
        String fileToSend = scanner.nextLine(); // File to send

        Socket socket = null;

        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server");

            // Send file name
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(fileToSend);

            // Send file data
            FileInputStream fis = new FileInputStream(fileToSend);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }

            System.out.println("File " + fileToSend + " sent successfully.");
            fis.close();
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

