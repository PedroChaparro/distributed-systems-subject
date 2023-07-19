package classes;

import shared.Song;

public class Client {

    JSocketClient sk;

    public Client(String address, int port) {
        sk = new JSocketClient(address, port);
    }

    public void run() {
        sk.listen();
    }

    public void send(Song s) {
        this.sk.send(s);
    }
}
