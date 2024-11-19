package com.example.examplemod.datagen;

import com.google.common.collect.MapMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class ResourceKeyGen<T> implements Comparable<ResourceKeyGen<?>> {

    private static final ConcurrentMap<ResourceKeyGen.InternKey, ResourceKeyGen<?>> VALUES = (new MapMaker()).weakValues().makeMap();
    private static final ResourceLocation MOD_REGISTRY_NAME = new ResourceLocation("examplemod");
    private final ResourceLocation registryName;
    private final ResourceLocation location;

    public static <T> Codec<ResourceKeyGen<T>> codec(ResourceKeyGen<? extends Registry<T>> pRegistryKey) {
        return ResourceLocation.CODEC.xmap((p_195979_) -> {
            return create(pRegistryKey, p_195979_);
        }, ResourceKeyGen::location);
    }

    public static <T> ResourceKeyGen<T> create(ResourceKeyGen<? extends Registry<T>> pRegistryKey, ResourceLocation pLocation) {
        return create(pRegistryKey.location, pLocation);
    }

    public static <T> ResourceKeyGen<Registry<T>> createRegistryKey(ResourceLocation pLocation) {
        return create(MOD_REGISTRY_NAME, pLocation);
    }

    private static <T> ResourceKeyGen<T> create(ResourceLocation pRegistryName, ResourceLocation pLocation) {
        return (ResourceKeyGen)VALUES.computeIfAbsent(new ResourceKeyGen.InternKey(pRegistryName, pLocation), (p_258225_) -> {
            return new ResourceKeyGen(p_258225_.registry, p_258225_.location);
        });
    }

    private ResourceKeyGen(ResourceLocation pRegistryName, ResourceLocation pLocation) {
        this.registryName = pRegistryName;
        this.location = pLocation;
    }

    public String toString() {
        return "ResourceKey[" + this.registryName + " / " + this.location + "]";
    }

    public boolean isFor(ResourceKeyGen<? extends Registry<?>> pRegistryKey) {
        return this.registryName.equals(pRegistryKey.location());
    }

    public <E> Optional<ResourceKeyGen<E>> cast(ResourceKeyGen<? extends Registry<E>> pRegistryKey) {
        return this.isFor(pRegistryKey) ? (Optional<ResourceKeyGen<E>>) Optional.of(this) : Optional.empty();
    }

    public ResourceLocation location() {
        return this.location;
    }

    public ResourceLocation registry() {
        return this.registryName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            return this.location.equals(((ResourceKeyGen)o).location) && this.registryName.equals(((ResourceKeyGen)o).registryName);
        } else {
            return false;
        }
    }

    public int compareTo(ResourceKeyGen<?> o) {
        int ret = this.registry().compareTo(o.registry());
        if (ret == 0) {
            ret = this.location().compareTo(o.location());
        }

        return ret;
    }

    static record InternKey(ResourceLocation registry, ResourceLocation location) {
        InternKey(ResourceLocation registry, ResourceLocation location) {
            this.registry = registry;
            this.location = location;
        }

        public ResourceLocation registry() {
            return this.registry;
        }

        public ResourceLocation location() {
            return this.location;
        }
    }
}
