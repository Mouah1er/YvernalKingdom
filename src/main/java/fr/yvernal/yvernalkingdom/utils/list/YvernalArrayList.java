package fr.yvernal.yvernalkingdom.utils.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Juste pour rajouter une méthode modify
 */
public class YvernalArrayList<T> extends ArrayList<T> {

    public YvernalArrayList() {
        super();
    }

    public YvernalArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public YvernalArrayList(Collection<? extends T> collection) {
        super(collection);
    }

    public YvernalArrayList<T> modify(Consumer<YvernalArrayList<T>> listConsumer) {
        listConsumer.accept(this);

        return this;
    }
}
