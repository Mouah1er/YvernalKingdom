package fr.yvernal.yvernalkingdom.data;

import java.util.List;

public interface DataManagerTemplate<T> {
    List<T> getAllFromDatabase();

    T getFromDatabase(String uniqueId);

    void addToDatabase(T object);

    void updateToDatabase(T object);

    void deleteFromDatabase(T object);
}
