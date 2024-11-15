package com.example.examplemod.entity.model;

import com.example.examplemod.entity.AutomatonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AutomatonModel extends DefaultedEntityGeoModel<AutomatonEntity> {

    public AutomatonModel(ResourceLocation assetSubpath) {
        super(assetSubpath, true);
    }

    @Override
    public RenderType getRenderType(AutomatonEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }

}
