package com.example.examplemod.world.entity.modulable;

import com.example.examplemod.world.entity.Automaton;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientSideModulable implements Modulable {
    private Container container;
    private final Player player;

    public ClientSideModulable(Player pPlayer) {
        this.player = pPlayer;
    }

    public Player getWatchingPlayer() {
        return this.player;
    }

    public void setWatchingPlayer(@Nullable Player pPlayer) {
    }

    @Override
    public void setContainer(Container pInventory) {
        this.container = pInventory;
    }

    @Override
    public Automaton getAutomaton() {
        return (Automaton) null;
    }

    @Override
    public Container getContainer() {
        return this.container;
    }
}
