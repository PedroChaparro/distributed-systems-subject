import songs.SongsMemoryRepository;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// Related documentation

// LocateRegistry: create a remote object registry that accepts calls on a specific port.
// https://docs.oracle.com/javase/8/docs/api/java/rmi/registry/LocateRegistry.html

// Naming.rebind: Rebinds the specified name to a new remote object.
// https://docs.oracle.com/javase/8/docs/api/java/rmi/Naming.html

public class Main {
    public static void main(String[] args) {
        // Create the server to listen for remote requests
        try {
            SongsMemoryRepository songsRepositoryImplementation =  new SongsMemoryRepository();
            LocateRegistry.createRegistry(3300);
            Naming.rebind("//127.0.0.1:3300/songs", songsRepositoryImplementation);
            System.out.println("[SERVER] The remote object is ready to receive connections");
        }catch (Exception e){
            System.out.println("[SERVER] Something goes wrong");
            System.out.println(e);
        }

    }
}
