import shared.Song;
import shared.SongsRepository;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

// Related documentation

// Naming.lookup: Returns a reference, a stub, for the remote object associated with the specified name.
// https://docs.oracle.com/javase/8/docs/api/java/rmi/Naming.html

public class Main {
    public static void main(String[] args) {
        // Get the connection to the remote object
        try{
            SongsRepository remoteRepository = (SongsRepository) Naming.lookup("rmi://127.0.0.1:3300/songs");

            // Add a song using the Save method in the remote object
            Song newSong = new Song("Song 1.6", "Author 4", "Rap", 480);
            remoteRepository.save(newSong);

            // Get songs by Title
            List<Song> filteredByTitleSongs = remoteRepository.getByTitle("1");
            System.out.println("[CLIENT] The following results were obtained using the getByTitle method in the remote object");
            for(Song s: filteredByTitleSongs){
                System.out.println("[CLIENT] Song title: " + s.getTitle());
            }

            // Get songs by the author Name
            List<Song> filteredByAuthorSongs = remoteRepository.getByAuthor("Author 1");
            System.out.println("\n[CLIENT] The following results were obtained using the getByAuthor method in the remote object");
            for(Song s: filteredByAuthorSongs){
                System.out.println("[CLIENT] Song author: " + s.getAuthor());
            }

            // Get song by the song Genre
            List<Song> filteredBySongGenre = remoteRepository.getByGenre("pop");
            System.out.println("\n[CLIENT] The following results were obtained using the getByGenre method in the remote object");
            for(Song s: filteredBySongGenre){
                System.out.println("[CLIENT] Song genre: " + s.getGenre());
            }
        }catch (Exception e){
            System.out.println("[CLIENT] Something goes wrong");
            System.out.println(e);
        }
    }
}
