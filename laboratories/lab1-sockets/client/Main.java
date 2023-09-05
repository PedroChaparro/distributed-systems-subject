import classes.Client;
import shared.Song;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 1802);

        // Run the client in a separate thread
        new Thread(client::run).start();

        // 1) Send some initial songs
        Song[] songs = {
                new Song("Song name 1", "Author 1", 300),
                new Song("Song name 2", "Author 2", 400),
                new Song("Song name 3", "Author 3", 500),
                new Song("Song name 4", "Author 4", 600),
        };

        for(Song s : songs) {
            System.out.println("\n [Client]: Sending the song: " + s.getTitle());
            client.send(s);
        }

        // 2) Menu to dynamically send songs to the server
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("\n [Menu]: Enter the title of the song to send: ");
            String title = scanner.nextLine();

            System.out.println("\n [Menu]: Enter the artist of the song to send: ");
            String artist = scanner.nextLine();

            System.out.println("\n [Menu]: Enter the duration of the song to send: ");
            int duration = scanner.nextInt();
            scanner.nextLine();


            Song s = new Song(title, artist, duration);
            client.send(s);

            System.out.println("\n [Menu]: Do you want to send another song? (y/n)");
            String answer = scanner.nextLine();
            if(!answer.toUpperCase().equals("Y")) break;
        }
    }
}
