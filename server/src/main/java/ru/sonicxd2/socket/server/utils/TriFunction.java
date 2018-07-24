package ru.sonicxd2.socket.server.utils;

@FunctionalInterface
public interface TriFunction<T, U, V, W> {
    W generate(T t, U u, V v);
}
