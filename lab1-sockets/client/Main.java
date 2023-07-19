import classes.Client;
import shared.Song;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 1802);

        // Run the client in a separate thread
        new Thread(client::run).start();

        Song[] songs = {
                new Song("Nothing Else Matters", "Metallica", 300),
                new Song("The Unforgiven", "Metallica", 400),
                new Song("The Unforgiven II", "Metallica", 500),
                new Song("The Unforgiven III", "Metallica", 600),
        };

        for(Song s : songs) {
            System.out.println("\n [Client]: Enviando la canción: " + s.getTitle());
            client.send(s);
        }

    }
}
