//run command(ls) in server from client
import java.io.*;
import java.net.*;

public class Server5 {
    public static void main(String[] args) {
        int port = 5004; // Server's port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Streams to communicate with the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read the command from the client
            String command = reader.readLine();
            System.out.println("Received command: " + command);

            try {
                // Execute the command
                Process process = Runtime.getRuntime().exec(command);

                // Capture the output of the command
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                StringBuilder commandOutput = new StringBuilder();
                String line;

                // Read standard output
                while ((line = outputReader.readLine()) != null) {
                    commandOutput.append(line).append("\n");
                }

                // Read error output
                while ((line = errorReader.readLine()) != null) {
                    commandOutput.append(line).append("\n");
                }

                // Wait for the process to complete
                process.waitFor();

                // Send the command output back to the client
                writer.println("Command output:\n" + commandOutput.toString());
                outputReader.close();
                errorReader.close();
            } catch (Exception e) {
                writer.println("Error while executing the command: " + e.getMessage());
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
