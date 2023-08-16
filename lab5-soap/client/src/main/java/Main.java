import domain.Song;
import infraestructure.MemorySongRepositoryService;
import infraestructure.SongRepository;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        MemorySongRepositoryService songsRepositoryService = new MemorySongRepositoryService();
        SongRepository songsRepository = songsRepositoryService.getMemorySongRepositoryPort();

        List<Song> filteredByGenreSongs = songsRepository.getByGenre("Rap");
        System.out.println("Songs filtered by genre:");
        for(Song s: filteredByGenreSongs.stream().collect(Collectors.toList())) {
            System.out.println(s.getTitle());
        }

        List<Song> filteredByYearSongs = songsRepository.getByYear(2007);
        System.out.println("\nSongs filtered by year:");
        for(Song s: filteredByYearSongs.stream().collect(Collectors.toList())) {
            System.out.println(s.getTitle());
        }

        List<Song> filteredByNameSongs = songsRepository.getByName("Song 1");
        System.out.println("\nSongs filtered by name:");
        for(Song s: filteredByNameSongs.stream().collect(Collectors.toList())) {
            System.out.println(s.getTitle());
        }
    }
}
