import classes.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(1802);
        server.run();
    }
}
