package com.example.examplemod.register;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.world.entity.Automaton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExampleMod.MOD_ID);

    public static final RegistryObject<EntityType<Automaton>> AUTOMATON = ENTITY_TYPES.register("automaton",
            () -> EntityType.Builder
                    .of(Automaton::new, MobCategory.CREATURE)
                    .sized(0.8f,1f)
                    .build(new ResourceLocation(ExampleMod.MOD_ID, "automaton").toString()));

    public static void register(IEventBus event) {
        ENTITY_TYPES.register(event);
    }
}
