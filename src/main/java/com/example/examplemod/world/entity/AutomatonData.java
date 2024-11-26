package com.example.examplemod.world.entity;

import com.example.examplemod.world.item.modifications.ItemModification;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public class AutomatonData {

    public static final int MIN_ENERGY = 0;
    public static final int MAX_ENERGY = 50;
//    private Item leftArm = Items.AIR;
//    private Item legs = Items.AIR;
//    private Item rightArm = Items.AIR;
//    private Item core = Items.AIR;
//    private Item head = Items.AIR;
    private int energy;
    private Map<Integer, ItemModification.ItemVariant> entireBody;

    public AutomatonData(int energy) {
        this.energy = energy;
        this.entireBody = ImmutableMap.of(
                0, ItemModification.ItemVariant.NONE,
                1, ItemModification.ItemVariant.NONE,
                2, ItemModification.ItemVariant.NONE,
                3, ItemModification.ItemVariant.NONE,
                4, ItemModification.ItemVariant.NONE);
    }

//LEFT LEGS RIGHT CORE HEAD
//    public AutomatonData(Item leftArm, Item legs, Item rightArm, Item core, Item head, int energy) {
//        this(energy, ImmutableMap.of(0,"a", 1,"ab",2,"abc",3,"abcd",4,"abcde"));
//        this.leftArm = leftArm;
//        this.legs = legs;
//        this.rightArm = rightArm;
//        this.core = core;
//        this.head = head;
//        this.energy = energy;
//        this.entireBody = ImmutableMap.of();
//    }

    public <K, V> AutomatonData(int energy, @Nullable Map<Integer, Item> entireBody) {
        this.energy = energy;

//        this.entireBody = ImmutableMap.copyOf(
//                entireBody.entrySet().stream()
//                        .collect(Collectors.toMap(
//                                Map.Entry::getKey,         // Mantener las claves originales
//                                entry -> entry.//.getValue().getName() // Obtener el nombre del ModItem como String
//                        ))
//        );

    }

    public int getEnergy() {
        return this.energy;
    }

//    public ModificationType getLeftArm() {
//        return this.leftArm;
//    }
//
//    public ModificationType getRightArm() {
//        return this.rightArm;
//    }
//
//    public ModificationType getHead() {
//        return this.head;
//    }
//
//    public ModificationType getCore() {
//        return this.core;
//    }
//
//    public ModificationType getLegs() {
//        return this.legs;
//    }

//    public Item getPartBySlot(int slotIndex) {
//
//    }

    public Map<Integer, String> getEntireBody() {
        ImmutableMap.Builder<Integer, String> builder = ImmutableMap.builder();

        for (Map.Entry<Integer, ItemModification.ItemVariant> entry : entireBody.entrySet()) {
            int key = entry.getKey();
            ItemModification.ItemVariant item = entry.getValue();

            for (ItemModification.ItemVariant variant : ItemModification.ItemVariant.values()) {
                if (variant == item) {
                    builder.put(key, variant.getName());
                    break;
                }
            }
        }

        return builder.build();
    }

//    public AutomatonData setEnergy(int pEnergy) {
//        return new AutomatonData(this.leftArm, this.legs, this.rightArm, this.core, this.head, pEnergy);
//    }
//
//    public AutomatonData setLeftArm(Item pLeftArm) {
//        return new AutomatonData(pLeftArm, this.legs, this.rightArm, this.core, this.head, this.energy);
//    }
//
//    public AutomatonData setRightArm(Item pRightArm) {
//        return new AutomatonData(this.leftArm, this.legs, pRightArm, this.core, this.head, this.energy);
//    }
//
//    public AutomatonData setHead(Item pHead) {
//        return new AutomatonData();
//    }
//
//    public AutomatonData setCore(Item pCore) {
//        return new AutomatonData();
//    }
//
//    public AutomatonData setLegs(Item pLegs) {
//        return new AutomatonData();
//    }
}
