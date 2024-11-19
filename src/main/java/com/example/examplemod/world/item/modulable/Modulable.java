package com.example.examplemod.world.item.modulable;

import com.example.examplemod.client.gui.inventory.ModuleMenu;
import com.example.examplemod.world.entity.Automaton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public interface Modulable {

    @Nullable
    Player getWatchingPlayer();

    void setWatchingPlayer(@Nullable Player pPlayer);

    void setContainer(Container pInventory);

    Automaton getAutomaton();

    Container getContainer();

    default void openModuleScreen(Player pPlayer, Component pDisplayName) {
        pPlayer.openMenu(new SimpleMenuProvider((pContainerId, pPlayerInventory, p_45300_) -> {
            return new ModuleMenu(pContainerId, pPlayerInventory, this);
                }, pDisplayName)
        );


    }
}
