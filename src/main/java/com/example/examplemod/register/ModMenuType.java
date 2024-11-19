package com.example.examplemod.register;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.gui.inventory.ModuleMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExampleMod.MOD_ID);

    public static final RegistryObject<MenuType<ModuleMenu>> MODULE_MENU = registerMenuType("module_menu", ModuleMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerForgeMenuType(String sub, IContainerFactory<T> pFactory) {
        return MENUS.register(sub, () -> IForgeMenuType.create(pFactory));
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String sub, MenuType.MenuSupplier<T> pFactory) {
        return MENUS.register(sub, () -> new MenuType<T>(pFactory, FeatureFlags.DEFAULT_FLAGS));
    }

    public static void register(IEventBus event) {
        MENUS.register(event);
    }
}
