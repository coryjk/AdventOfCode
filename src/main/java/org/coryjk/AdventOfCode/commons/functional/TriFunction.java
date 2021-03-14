package org.coryjk.AdventOfCode.commons.functional;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<A,B,C,R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> next) {
        Objects.requireNonNull(next);
        return (A a, B b, C c) -> next.apply(apply(a, b, c));
    }
}