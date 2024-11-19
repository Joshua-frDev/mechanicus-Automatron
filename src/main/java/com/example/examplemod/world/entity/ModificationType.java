package com.example.examplemod.world.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ModificationType {

    public static final Map<ModificationType, Item> MODIFICATIONS_SET;
    public static final ModificationType DRILL_ARM = key("drill_arm");
    public static final ModificationType SWORD_ARM = key("sword_arm");
    public static final ModificationType TACTIC_VISOR = key("tactic_visor");
    public static final ModificationType SHIELD = key("shield");
    public static final ModificationType NONE = key("none");
    private static final Map<Integer, Set<ModificationType>> BY_SLOT = Util.make(Maps.newHashMap(), (x) -> {
        x.put(0, (Set<ModificationType>) Set.of(TACTIC_VISOR, NONE));
        x.put(1, (Set<ModificationType>) Set.of(DRILL_ARM, SWORD_ARM, NONE));
        x.put(2, (Set<ModificationType>) Set.of(DRILL_ARM, SWORD_ARM, SHIELD, NONE));
        //x.put(3, SWORD_ARM);
        //x.put(4, SWORD_ARM);
    });
//TODO CREAR CLASES PARA CADA SECCION MODULAR EJEMPLO ModItem extends LeftArmType
    private ModificationType(String pName) {

    }

    private static ModificationType key(String pName) {
        return new ModificationType(pName);
    }

    public static boolean isValidItem(Item item) {
        return MODIFICATIONS_SET.containsValue(item);
    }

    public static Item getItemByModif(ModificationType pModification) {
        return MODIFICATIONS_SET.get(pModification);
    }

//    public static ModificationType getModifByItem(Item pItem) {
//        return MODIFICATIONS_SET.
//    }

    public static Set<ModificationType> bySlot(int pEquipmentSlot) {
        Map<Integer, Set<ModificationType>> bySlot = BY_SLOT;
        Objects.requireNonNull(bySlot);
        return bySlot.get(pEquipmentSlot);
    }

    static {
        MODIFICATIONS_SET = ImmutableMap.of(
                DRILL_ARM, Items.STICK,
                SWORD_ARM, Items.BOOK,
                TACTIC_VISOR, Items.DIAMOND,
                SHIELD, Items.SHIELD,
                NONE, Items.AIR
        );
    }
}
