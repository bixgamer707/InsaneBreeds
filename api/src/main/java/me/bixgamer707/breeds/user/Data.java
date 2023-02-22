package me.bixgamer707.breeds.user;

public interface Data<V> {

    V get();

    void set(V value);

    void add(V value);

    void reset();

    void remove(V value);

}
