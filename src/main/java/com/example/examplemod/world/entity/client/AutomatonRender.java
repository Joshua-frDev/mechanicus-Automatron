package com.example.examplemod.world.entity.client;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.world.entity.Automaton;
import com.example.examplemod.world.entity.model.AutomatonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AutomatonRender extends GeoEntityRenderer<Automaton> {

    public AutomatonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AutomatonModel(new ResourceLocation(ExampleMod.MOD_ID, "automaton")));
        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderType getRenderType(Automaton animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, Automaton animatable, RenderType renderType, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        System.out.println("reRender called here");

//        if(animatable.getFlag()) {
//            System.out.println("getflag true");
//            poseStack.scale(0.8F, 1F, 1F);
//        } else {
//            System.out.println("getflag false");
//            poseStack.scale(0.4F, 0.5F, 0.5F);
//        }

        super.reRender(model, poseStack, bufferSource, animatable, renderType, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
