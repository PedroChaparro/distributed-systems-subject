package shared;

import java.io.Serializable;

public class Song implements Serializable {
    private final String title;
    private final String author;
    private final String genre;
    private final int length;

    public Song(String title, String author, String genre, int length) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getLength() {
        return length;
    }

    public String getGenre() {
        return genre;
    }
}
