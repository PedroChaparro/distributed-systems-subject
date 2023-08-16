package domain;

public class Song {
    private final String title;
    private final int year;
    private final String genre;

    public Song(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }


    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }
}