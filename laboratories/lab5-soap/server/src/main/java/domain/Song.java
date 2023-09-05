package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Song {
    private final String title;
    private final int year;
    private final String genre;

    public Song(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    // Required by JAXB
    public Song() {
        this.title = "";
        this.genre = "";
        this.year = 0;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }


    @XmlElement
    public String getGenre() {
        return genre;
    }

    @XmlElement

    public int getYear() {
        return year;
    }
}