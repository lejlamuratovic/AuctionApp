package com.example.auctionapp.util.builderpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericBuilder<T> {
    // supplier is used to create a new instance of a generic type T
    private final Supplier<T> instantiator;

    // list of consumer functions, where each modifies a T instance in some way.
    private final List<Consumer<T>> instanceModifiers = new ArrayList<>();

    public GenericBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    // creates a new builder with the given instantiator
    public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
        return new GenericBuilder<>(instantiator);
    }

    // method that takes a biconsumer and a value of type U, and adds a new consumer to the list of instanceModifiers.
    // the new consumer uses the biconsumer to modify a T instance with the given value
    public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
        final Consumer<T> c = instance -> consumer.accept(instance, value);

        instanceModifiers.add(c);

        return this;
    }

    // creates a new instance of T using the instantiator, applies all of the instanceModifiers to it,
    // and returns the result.
    public T build() {
        final T value = instantiator.get();

        instanceModifiers.forEach(modifier -> modifier.accept(value));
        instanceModifiers.clear();

        return value;
    }
}
