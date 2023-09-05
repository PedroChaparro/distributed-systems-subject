package songs;

import shared.Song;
import shared.SongsRepository;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

// Related documentation
// JAVA filtering with lambda functions: https://www.baeldung.com/java-stream-filter-lambda

public class SongsMemoryRepository extends UnicastRemoteObject implements SongsRepository {
    private ArrayList<Song> inMemorySongs;

    public SongsMemoryRepository() throws RemoteException {
        // Instance the local repository with a few songs
        this.inMemorySongs = new ArrayList<>();
        this.save(new Song("Song 1", "Author 1", "Pop",140));
        this.save(new Song("Song 1.1", "Author 1", "Pop", 140));
        this.save(new Song("Song 2", "Author 1", "Rock", 160));
        this.save(new Song("Song 3", "Author 2", "Rap", 240));
        this.save(new Song("Song 4", "Author 2", "Urban", 320));
        this.save(new Song("Song 5", "Author 3",  "Urban",245));
    }

    @Override
    public void save(Song song) throws RemoteException {
        this.inMemorySongs.add(song);
    }

    @Override
    public List<Song> getByTitle(String title) throws RemoteException {
        // Uncomment the following line to verify that the method is being executed in the server side
        // System.out.println("[SERVER] Filtering by query: " + title);

        List<Song> filteredSongs= this.inMemorySongs.stream().filter(song -> {
           String currentSongTitle = song.getTitle().toUpperCase();
           return currentSongTitle.contains(title.toUpperCase());
        }).toList();

        return filteredSongs;
    }

    @Override
    public List<Song> getByAuthor(String author) throws RemoteException {
        // Uncomment the following line to verify that the method is being executed in the server side
        // System.out.println("[SERVER] Filtering by query: " + title);

        List<Song> filteredSongs= this.inMemorySongs.stream().filter(song -> {
            String currentSongTitle = song.getAuthor().toUpperCase();
            return currentSongTitle.contains(author.toUpperCase());
        }).toList();

        return filteredSongs;
    }

    @Override
    public List<Song> getByGenre(String genre) throws RemoteException {
        // Uncomment the following line to verify that the method is being executed in the server side
        // System.out.println("[SERVER] Filtering by query: " + title);

        List<Song> filteredSongs= this.inMemorySongs.stream().filter(song -> {
            String currentSongGenre = song.getGenre().toUpperCase();
            return currentSongGenre.contains(genre.toUpperCase());
        }).toList();

        return filteredSongs;
    }
}
