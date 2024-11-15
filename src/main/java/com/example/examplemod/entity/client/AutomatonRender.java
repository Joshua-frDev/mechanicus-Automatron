package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.AutomatonEntity;
import com.example.examplemod.entity.model.AutomatonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AutomatonRender extends GeoEntityRenderer<AutomatonEntity> {

    public AutomatonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AutomatonModel(new ResourceLocation(ExampleMod.MOD_ID, "automaton")));
        this.shadowRadius = 0.4f;
    }
}
