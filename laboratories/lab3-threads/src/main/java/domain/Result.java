package domain;

public class Result {
    private String name;
    private String path;

    public Result(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public String toString() {
        return "Result(name=" + this.getName() + ", path=" + this.getPath() + ")";
    }
}
