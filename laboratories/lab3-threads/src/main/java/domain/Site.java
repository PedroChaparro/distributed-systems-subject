package domain;

public class Site {
    private String url;
    private String name;

    public Site(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }
}
