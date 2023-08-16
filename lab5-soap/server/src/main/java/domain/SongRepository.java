package domain;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SongRepository {
    @WebMethod
    List<Song> getByName(@WebParam  String name);

    @WebMethod
    List<Song> getByGenre(@WebParam String genre);

    @WebMethod
    List<Song> getByYear(@WebParam int year);
}
