package classes;

import shared.Song;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class JSocketClient {

    private InetAddress address;
    private int port;
    private Socket clientSk;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Queue<Song> pendingSongs;

    public JSocketClient(String address, int port) {
        try {
            this.port = port;
            this.address = InetAddress.getByName(address);
            this.oos = null;
            this.ois = null;
            this.pendingSongs = new LinkedList<Song>();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            // Establish the connection
            this.clientSk = new Socket(this.address, this.port);
            this.oos = new ObjectOutputStream(this.clientSk.getOutputStream());
            this.oos.flush();
            this.ois = new ObjectInputStream(this.clientSk.getInputStream());
            System.out.println("\n [Client]: Connection established.");

            // Send the pending songs
            new Thread(this::dequePendingSongs).start();

            // Listen for the server responses
            while (true) {
                try {
                    String response = (String) this.ois.readObject();
                    if (response == null) {
                        System.out.println("\n [Client]: A null response was received.");
                    } else {
                        System.out.println("\n [Client]: Server responds: " + response);
                    }
                } catch (Exception e) {
                    System.out.println("\n [Client]: Unable to read the response.");
                    this.closeService();
                }
            }
        } catch (IOException e1) {
            // e1.printStackTrace();
            System.out.println("\n [Client]: Unable to establish the connection. Retrying in 5 seconds...");

            try {
                Thread.sleep(5000);
                this.listen();
            } catch (InterruptedException e2) {
                System.out.println("\n [Client]: There was an error in the 'listening thread'.");
                System.exit(1);
            }
        }
    }

    public void send(Song s) {
        try {
            this.oos.writeObject(s);
            this.oos.flush();
        } catch (Exception e) {
            System.out.println("\n [Client]: Unable to send the song.");
            this.pendingSongs.add(s);
        }
    }

    private void dequePendingSongs() {
        try {
            while (!this.pendingSongs.isEmpty()) {
                System.out.println("\n [Client]: Sending pending song...");
                Song s = this.pendingSongs.remove();
                this.send(s);
            }
        } catch (Exception e) {
            System.out.println("\n [Client]: Unable to send the pending songs.");
            System.out.println("\n [Client]: Error trace: " + e);
        }
    }

    private void closeService() {
        try {
            // Close the streams
            this.ois.close();
            this.ois = null;

            this.oos.close();
            this.oos = null;

            this.clientSk.close();

            // Listen for new connections
            System.out.println("\n [Client]: The connection was closed.");
            this.listen();
        } catch (Exception e) {
            System.out.println("\n [Client]: Unable to close the connection.");
        }
    }

}
