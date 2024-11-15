package com.example.examplemod.register;

import com.example.examplemod.ExampleMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<ForgeSpawnEggItem> AUTOMATON_SPAWN_EGG = ITEMS.register("automaton_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityType.AUTOMATON, 0x948e8d, 0x3b3635, new Item.Properties()));

    public static void register(IEventBus event) { ITEMS.register(event); }
}
