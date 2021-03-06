package my.collections;

import java.util.HashMap;

public class HashCounter<K> extends Counter<K> {

    /**
     * Constructs a new (empty) Counter.
     */
    public HashCounter() {
        map = new HashMap<K, MutableInteger>();
        totalCount = 0;
    }

    /**
     * Constructs a new Counter with the contents of the given Counter.
     *
     * @param counter
     */
    public HashCounter(Counter<K> counter) {
        this();
        addAll(counter);
    }

    @Override
    public Object clone() {
        return new HashCounter<K>(this);
    }
}