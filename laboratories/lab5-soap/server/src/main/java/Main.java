import infraestructure.MemorySongRepository;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        String url = "http://localhost:8080/songs";
        System.out.println("Starting server on: " + url);
        Endpoint.publish(url, new MemorySongRepository());
    }
}
