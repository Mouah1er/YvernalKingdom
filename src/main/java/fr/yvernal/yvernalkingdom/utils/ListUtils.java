package fr.yvernal.yvernalkingdom.utils;

import java.util.List;
import java.util.function.Consumer;

public class ListUtils {

    public static <T> List<T> modifyList(List<T> list, Consumer<List<T>> consumer) {
        consumer.accept(list);

        return list;
    }
}
