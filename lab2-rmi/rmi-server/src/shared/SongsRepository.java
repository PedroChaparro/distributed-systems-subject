package shared;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SongsRepository extends Remote {
    void save(Song song) throws  RemoteException;
    List<Song> getByTitle(String title) throws RemoteException;
    List<Song> getByAuthor(String author) throws RemoteException;
    List<Song> getByGenre(String genre) throws RemoteException;
}
