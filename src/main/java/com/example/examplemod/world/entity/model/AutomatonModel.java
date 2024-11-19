package com.example.examplemod.world.entity.model;

import com.example.examplemod.world.entity.Automaton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AutomatonModel extends DefaultedEntityGeoModel<Automaton> {

    public AutomatonModel(ResourceLocation assetSubpath) {
        super(assetSubpath, true);
    }

    @Override
    public RenderType getRenderType(Automaton animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }



}
