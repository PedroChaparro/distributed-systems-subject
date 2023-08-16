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

        for(Song s: filteredByGenreSongs.stream().collect(Collectors.toList())) {
            System.out.println(s);
        }
    }
}
