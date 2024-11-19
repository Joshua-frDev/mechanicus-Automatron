package com.example.examplemod.event;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.world.entity.Automaton;
import com.example.examplemod.register.ModEntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityType.AUTOMATON.get(), Automaton.createAttributes());

    }

}
