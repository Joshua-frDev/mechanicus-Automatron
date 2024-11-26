package com.example.examplemod.world.item.modifications;

import com.example.examplemod.register.ModItem;
import com.example.examplemod.world.entity.Automaton;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class ItemModification extends Item {

    public ItemModification(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public static enum ItemVariant implements StringRepresentable {
        DRILL_ARM(ImmutableSet.of(Automaton.SLOT_LEFT_ARM, Automaton.SLOT_RIGHT_ARM), "drill_arm", ModItem.DRILL_ARM.get()),
        SPEAR_ARM(ImmutableSet.of(Automaton.SLOT_LEFT_ARM, Automaton.SLOT_RIGHT_ARM), "spear_arm", ModItem.SPEAR_ARM.get()),
        GRIPPER_ARM(ImmutableSet.of(Automaton.SLOT_LEFT_ARM, Automaton.SLOT_RIGHT_ARM), "gripper_arm", ModItem.GRIPPER_ARM.get()),
        TARGETING_VISOR(ImmutableSet.of(Automaton.SLOT_HEAD), "targeting_visor", ModItem.TARGETING_VISOR.get()),
        NONE(ImmutableSet.of(0, 1, 2, 3, 4), "none", Items.AIR);

        private final String name;
        private final Set<Integer> forSlots;
        private final Item item;

        private ItemVariant(Set<Integer> forSlots, String name, Item item) {
            this.name = name;
            this.forSlots = forSlots;
            this.item = item;
        }

        public static Set<Item> bySlot(int slot) {
            ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

            for (ItemVariant variant : values()) {

                for (Integer forSlot : variant.forSlots) {
                    if(forSlot == slot) {
                        builder.add(variant.item);
                    }
                }
            }

            return builder.build();
        }

        public String getName() {
            return this.name;
        }

        public static ImmutableMap<Integer, Item> getItemsFromString(Map<Integer, String> map) {
            ImmutableMap.Builder<Integer, Item> builder = ImmutableMap.builder();

            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                int key = entry.getKey();
                String name = entry.getValue();

                for (ItemVariant variant : ItemVariant.values()) {
                    if (variant.getSerializedName().equals(name)) {
                        builder.put(key, variant.getItem());
                        break;
                    }
                }
            }

            return builder.build();
        }

        public Item getItem() {
            return this.item;
        }

        public Set<Integer> getSlots() {
            return this.forSlots;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
