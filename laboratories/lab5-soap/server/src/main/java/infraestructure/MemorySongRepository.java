package infraestructure;

import domain.Song;
import domain.SongRepository;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebService(endpointInterface = "domain.SongRepository")
public class MemorySongRepository implements SongRepository {
    private List<Song> inMemorySongs;


    public MemorySongRepository() {
        this.inMemorySongs = new ArrayList<>();
        this.save(new Song("Song 1", "Rap", 2003));
        this.save(new Song("Song 2", "Rap", 2004));
        this.save(new Song("Song 3", "Pop", 2005));
        this.save(new Song("Song 4", "Pop", 2006));
        this.save(new Song("Song 5", "Latin", 2007));
        this.save(new Song("Song 6", "Latin", 2007));
    }

    private void save(Song song) {
        this.inMemorySongs.add(song);
    }

    @Override
    public List<Song> getByName(String name) {
        List <Song> filtereddSongs = this.inMemorySongs.stream().filter(song -> {
            String currentSongTitle = song.getTitle().toUpperCase();
            return currentSongTitle.contains(name.toUpperCase());
        }).collect(Collectors.toList());

        return filtereddSongs;
    }

    @Override
    public List<Song> getByGenre(String genre) {
        List <Song> filtereddSongs = this.inMemorySongs.stream().filter(song -> {
            String currentSongGenre = song.getGenre().toUpperCase();
            return currentSongGenre.contains(genre.toUpperCase());
        }).collect(Collectors.toList());

        return filtereddSongs;
    }

    @Override
    public List<Song> getByYear(int year) {
        List <Song> filtereddSongs = this.inMemorySongs.stream().filter(song -> {
            int currentSongYear = song.getYear();
            return currentSongYear == year;
        }).collect(Collectors.toList());

        return filtereddSongs;
    }
}
