package com.example.examplemod.register;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            ExampleMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_CUSTOM_TAB = CREATIVE_MODE_TAB.register("mod_custom_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItem.AUTOMATON_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.mod_custom_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItem.AUTOMATON_SPAWN_EGG.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
