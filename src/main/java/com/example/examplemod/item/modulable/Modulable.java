package com.example.examplemod.item.modulable;

import com.example.examplemod.inventory.ModuleMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

public interface Modulable {

    default void openModuleScreen(Player pPlayer, Component pDisplayName, Container pInventory){
        OptionalInt $$1 = pPlayer.openMenu(new SimpleMenuProvider((pContainerId, pPlayerInventory, p_45300_) -> {
            return new ModuleMenu(pContainerId, pPlayerInventory, this);
                }, pDisplayName)
        );
    }
}
