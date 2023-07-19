package shared;

import java.io.Serializable;

public class Song implements Serializable {
    private String title;
    private String artist;

    private int length;

    public Song(String title, String artist, int length) {
        this.title = title;
        this.artist = artist;
        this.length = length;
    }


    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public String getArtist() {
        return artist;
    }
}
