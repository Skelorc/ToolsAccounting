package wns.services;

import java.util.List;

public interface MainService {
    <T> List<T> getAll();
    void delete(long id);
}
