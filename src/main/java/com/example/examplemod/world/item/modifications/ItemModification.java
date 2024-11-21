package com.example.examplemod.world.item.modifications;

import com.google.common.collect.ImmutableSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ItemModification extends Item {

    public ItemModification(Properties pProperties) {
        super(pProperties);
    }

    public static enum ItemVariant implements StringRepresentable {
        //DRILL_ARM(ImmutableSet.of(0, 1), "drill_arm", ItemModification.);
;
        private final String name;
        private final Set<Integer> forSlots;
        private final ItemModification item;

        private ItemVariant(Set<Integer> forSlots, String name, ItemModification item) {
            this.name = name;
            this.forSlots = forSlots;
            this.item = item;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
