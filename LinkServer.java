import java.io.*;
import java.net.*;

public class LinkServer {
    public static void main(String[] args) {
        int port = 5009;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String url = input.readLine();
                    if (url == null || url.equalsIgnoreCase("QUIT")) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                    System.out.println("Requested to open link: " + url);

                    try {
                        // Open the URL in the default web browser
                        String os = System.getProperty("os.name").toLowerCase();
                        if (os.contains("win")) {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
                        } else if (os.contains("mac")) {
                            Runtime.getRuntime().exec(new String[]{"open", url});
                        } else if (os.contains("nix") || os.contains("nux")) {
                            Runtime.getRuntime().exec(new String[]{"xdg-open", url});
                        }
                        output.println("LINK OPENED SUCCESSFULLY");
                    } catch (Exception e) {
                        output.println("ERROR: Unable to open the link. " + e.getMessage());
                    }
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
