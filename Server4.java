//run java program in server from client
import java.io.*;
import java.net.*;

public class Server4 {
    public static void main(String[] args) {
        int port = 5003; // Server's port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Streams to communicate with the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read the Java program's name or path from the client
            String javaFilePath = reader.readLine();
            System.out.println("Received request to run: " + javaFilePath);

            try {
                // Compile the Java program
                System.out.println("Compiling the Java program...");
                Process compileProcess = Runtime.getRuntime().exec("javac " + javaFilePath);
                compileProcess.waitFor();

                // Check for compilation errors
                if (compileProcess.exitValue() != 0) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
                    String line;
                    System.out.println("Compilation errors:");
                    while ((line = errorReader.readLine()) != null) {
                        System.out.println(line); // Display compilation errors on the server console
                    }
                    writer.println("Compilation failed. Check the server console for details.");
                    errorReader.close();
                } else {
                    // Run the compiled Java program
                    String className = javaFilePath.substring(0, javaFilePath.lastIndexOf('.')); // Remove .java extension
                    System.out.println("Running the Java program...");
                    Process runProcess = Runtime.getRuntime().exec("java " + className);

                    // Capture and display the program's output on the server console
                    BufferedReader outputReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));

                    String line;
                    System.out.println("Program output:");
                    while ((line = outputReader.readLine()) != null) {
                        System.out.println(line); // Display standard output
                    }
                    while ((line = errorReader.readLine()) != null) {
                        System.out.println(line); // Display error output
                    }

                    runProcess.waitFor();
                    writer.println("Program executed successfully. Check the output in server.");

                    outputReader.close();
                    errorReader.close();
                }
            } catch (Exception e) {
                writer.println("Error while running the Java program: " + e.getMessage());
                e.printStackTrace();
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
