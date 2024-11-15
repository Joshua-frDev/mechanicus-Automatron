package com.example.examplemod.entity;

import com.example.examplemod.item.modulable.Modulable;
import com.example.examplemod.register.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AutomatonEntity extends PathfinderMob implements ContainerListener, Modulable, GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private BlockPos boundOrigin;
    protected SimpleContainer inventory;
    public static final int INVENTORY_SIZE = 2;
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS;
    private LazyOptional<?> lazyItemHandler = null;
    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE);

    public AutomatonEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.createInventory();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier createAttributes() {
        return PathfinderMob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 20.00)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f).build();
    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(INVENTORY_SIZE);

        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.lazyItemHandler = LazyOptional.of(() -> {
            return new InvWrapper(this.inventory);
        });
    }

    private void updateContainerEquipment() {
        //this.setFlag(4, !this.inventory.getItem(0).isEmpty());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
    }

//    protected void setFlag(int pFlagId, boolean pValue) {
//        byte b0 = (Byte)this.entityData.get(DATA_ID_FLAGS);
//        if (pValue) {
//            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | pFlagId));
//        } else {
//            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~pFlagId));
//        }
//    }

//    protected boolean getFlag(int pFlagId) {
//        return ((Byte)this.entityData.get(DATA_ID_FLAGS) & pFlagId) != 0;
//    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }

        pCompound.put("inventory", itemHandler.serializeNBT());
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        itemHandler.deserializeNBT(pCompound.getCompound("inventory"));
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

//    @Override
//    public SimpleContainer getInventory() {
//        return null;
//    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() != ModItem.AUTOMATON_SPAWN_EGG.get() && this.isAlive() && !pPlayer.isSecondaryUseActive()) {

            if(!this.level().isClientSide) {
                this.openModuleScreen(pPlayer, this.getDisplayName(), this.inventory);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    //    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
//
//        return state.setAndContinue(state.isMoving() ?
//                RawAnimation.begin().then("ghost.walking.animation", Animation.LoopType.LOOP)
//                :
//                RawAnimation.begin().then("ghost.idle.animation", Animation.LoopType.LOOP)
//        );
//
//    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void tick() {
        super.tick();
    }

    @Override
    public void containerChanged(Container container) {

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level(), this, inventory);
    }

//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
//        return ;
//    }

    static {
        DATA_ID_FLAGS = SynchedEntityData.defineId(AutomatonEntity.class, EntityDataSerializers.BYTE);
    }
}
