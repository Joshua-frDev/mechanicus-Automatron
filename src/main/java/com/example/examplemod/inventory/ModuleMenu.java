package com.example.examplemod.inventory;

import com.example.examplemod.item.modulable.Modulable;
import com.example.examplemod.register.ModMenuType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ModuleMenu extends AbstractContainerMenu {

//    private final Level level;
//    private final ContainerData data;

    public ModuleMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, new Modulable() {
            @Override
            public void openModuleScreen(Player pPlayer, Component pDisplayName, Container pInventory) {
                Modulable.super.openModuleScreen(pPlayer, pDisplayName, pInventory);
            }
        });
    }

    public ModuleMenu(int pContainerId, Inventory inventory, Modulable pModulable) {
        super(ModMenuType.MODULE_MENU.get(), pContainerId);
        checkContainerSize(inventory, 2);
        createPlayerInventory(inventory);
//        this.addSlot(new Slot(this., 0, 136, 37));
//        this.addSlot(new Slot(this., 1, 162, 37));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    private void createPlayerInventory(Inventory pInvetory) {
        int $$5;
        for($$5 = 0; $$5 < 3; ++$$5) {
            for(int $$4 = 0; $$4 < 9; ++$$4) {
                this.addSlot(new Slot(pInvetory, $$4 + $$5 * 9 + 9, 108 + $$4 * 18, 84 + $$5 * 18));
            }
        }

        for($$5 = 0; $$5 < 9; ++$$5) {
            this.addSlot(new Slot(pInvetory, $$5, 108 + $$5 * 18, 142));
        }
    }
}
