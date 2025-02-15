package com.example.examplemod.client.gui.inventory;

import com.example.examplemod.world.entity.Automaton;
import com.example.examplemod.world.entity.modulable.ClientSideModulable;
import com.example.examplemod.world.entity.modulable.Modulable;
import com.example.examplemod.register.ModMenuType;
import com.example.examplemod.world.item.modifications.ItemModification;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModuleMenu extends AbstractContainerMenu {

    private final Container automatonContainer;
    private final Modulable source;
    private final Automaton automaton;

    public ModuleMenu(int pContainerId, Inventory pInventory) {
        this(pContainerId, pInventory, new SimpleContainer(5), new ClientSideModulable(pInventory.player));
    }

    public ModuleMenu(int pContainerId, Inventory inventory, Container pContainer, Modulable pModulable) {
        super(ModMenuType.MODULE_MENU.get(), pContainerId);
        checkContainerSize(pContainer, 5);
        this.automatonContainer = pContainer;
        this.source = pModulable;
        this.automaton = source.getAutomaton();
        checkContainerSize(inventory, 2);

        this.addSlot(new Slot(this.automatonContainer, 0, 136, 37) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return isValidModification(itemStack, ItemModification.ItemVariant.bySlot(0)) && !this.hasItem();
            }

            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.automatonContainer, 1, 162, 37) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return isValidModification(itemStack, ItemModification.ItemVariant.bySlot(1)) && !this.hasItem();
            }

            public int getMaxStackSize() {
                return 1;
            }
        });

        createPlayerInventory(inventory);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return this.source.getWatchingPlayer() == pPlayer && this.automaton.distanceTo(pPlayer) < 8.0F && this.automaton.isAlive();//TODO TESTEAR ESTO
    }

    public boolean isValidModification(ItemStack pItemStack, Set<Item> validItems) {
        return validItems.contains(pItemStack.getItem());
    }

    private void createPlayerInventory(Inventory pInvetory) {
        int $$5;
        for ($$5 = 0; $$5 < 3; ++$$5) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.addSlot(new Slot(pInvetory, $$4 + $$5 * 9 + 9, 8 + $$4 * 18, 84 + $$5 * 18));
            }
        }

        for ($$5 = 0; $$5 < 9; ++$$5) {
            this.addSlot(new Slot(pInvetory, $$5, 8 + $$5 * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack $$2 = ItemStack.EMPTY;
        Slot $$3 = (Slot)this.slots.get(pIndex);

        if ($$3 != null && $$3.hasItem()) {
            ItemStack $$4 = $$3.getItem();
            $$2 = $$4.copy();
            int $$5 = this.automatonContainer.getContainerSize();
            if (pIndex < $$5) {
                if (!this.moveItemStackTo($$4, $$5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

            } else if (this.getSlot(1).mayPlace($$4) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo($$4, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }

            } else if (this.getSlot(0).mayPlace($$4)) {
                if (!this.moveItemStackTo($$4, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }

            } else if ($$5 <= 2 || !this.moveItemStackTo($$4, 2, $$5, false)) {
                int $$6 = $$5;
                int $$7 = $$6 + 27;
                int $$8 = $$7;
                int $$9 = $$8 + 9;

                if (pIndex >= $$8 && pIndex < $$9) {
                    if (!this.moveItemStackTo($$4, $$6, $$7, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= $$6 && pIndex < $$7) {
                    if (!this.moveItemStackTo($$4, $$8, $$9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo($$4, $$8, $$7, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if ($$4.isEmpty()) {
                $$3.setByPlayer(ItemStack.EMPTY);
            } else {
                $$3.setChanged();
            }
        }

        return $$2;
    }
}
