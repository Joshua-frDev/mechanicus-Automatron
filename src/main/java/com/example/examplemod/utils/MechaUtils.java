package com.example.examplemod.utils;

import com.example.examplemod.world.item.modifications.ItemModification;
import com.google.common.collect.ImmutableMap;

public class MechaUtils {

    public static <K, V> ImmutableMap<K, V> mapOfSame(int maxValues, ValueProvider<K, V> value) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for (int i = 0; i <= maxValues; i++) {
            builder.put(value.getKey(i), value.getValue(i));
        }
        return builder.build();
    }

    @FunctionalInterface
    public interface ValueProvider<K, V> {
        K getKey(int index);

        default V getValue(int index) {
            return null;
        }
    }
}
