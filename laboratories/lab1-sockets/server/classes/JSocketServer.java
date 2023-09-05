package classes;

import shared.Song;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JSocketServer {

    private int port;
    private ServerSocket serverSk;
    private Socket clientSk;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public JSocketServer(int port) {
        try {
            this.port = port;
            this.serverSk = new ServerSocket(port, 100);

            // OOS: Object Output Stream
            // Write primitive data types and graphs of Java objects to an OutputStream.
            this.oos = null;

            // OIS: Object Input Stream
            // Read primitive data types and graphs of Java objects from an InputStream.
            this.ois = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listening() {
        try {
            System.out.println("\n [Server]: " + "Waiting for new connections...");

            // Blocks until a connection is made.
            this.clientSk = this.serverSk.accept();

            // Allows to send data to the other part of the socket.
            this.oos = new ObjectOutputStream(this.clientSk.getOutputStream());

            // flush(): Forces any buffered output bytes to be written out.
            this.oos.flush();

            // Allows to receive data from the other part of the socket.
            this.ois = new ObjectInputStream(this.clientSk.getInputStream());
            System.out.println("\n [Server]: " + "Connection established.");

            while(
                    this.clientSk != null &&
                    this.oos != null &&
                    this.ois != null
            ) {
                System.out.println("\n [Server]: " + "Waiting for data...");

                try {
                    Object received_object = this.ois.readObject();
                    Song s = (Song) received_object;

                    if (s == null) {
                        System.out.println("\n [Server]: A null object was received.");
                        send("Received null object.");
                    } else {
                        System.out.println("\n [Server]: The following song was received: " + s.getTitle());
                        send("Received song: " + s.getTitle());
                    }
                } catch (Exception e) {
                    System.out.println("\n [Server]: Unable to receive data from the client.");
                    this.closeService();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeService() {
        try {
            // Close the I/O streams.
            this.ois.close();
            this.ois = null;
            this.oos.close();
            this.oos = null;

            // Close the client connection if is active
            if(this.clientSk.isConnected()){
                this.clientSk.close();
                this.clientSk = null;
            }

            // Lister for new connections
            System.out.println("\n [Server]: The connection to the client was closed.");
            this.listening();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n [Server]: Unable to close the connection.");
        }
    }

    private void send(String data) {
        try {
            this.oos.writeObject("[Server]: " + data);
            this.oos.flush();
        } catch (Exception e) {
            System.out.println("\n [Server]: Unable to send data to the client.");
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
