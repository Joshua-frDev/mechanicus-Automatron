package com.example.examplemod.world.entity;

import com.example.examplemod.network.ModEDataSerializers;
import com.example.examplemod.world.item.modulable.Modulable;
import com.example.examplemod.register.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Automaton extends PathfinderMob implements ContainerListener, Modulable, GeoEntity {

    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS;
    protected static final EntityDimensions NO_LEGS_DIMENSIONS;
    protected static final EntityDimensions LEGGED_DIMENSIONS;
    public static final int INVENTORY_SIZE = 2;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE);
    private static final EntityDataAccessor<AutomatonData> AUTOMATON_DATA;
    private LazyOptional<?> lazyItemHandler = null;
    protected SimpleContainer inventory;
    private BlockPos boundOrigin;
    private Player watchingPlayer;

    public Automaton(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.createInventory();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 10F));
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

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        //this.updateContainerEquipment();
        this.lazyItemHandler = LazyOptional.of(() -> {
            return new InvWrapper(this.inventory);
        });
    }

    private void updateContainerEquipment() {
        this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        this.setFlag(5, !this.inventory.getItem(1).isEmpty());
        this.setFlag(6, !this.inventory.getItem(2).isEmpty());
        this.setFlag(7, !this.inventory.getItem(3).isEmpty());
        this.setFlag(8, !this.inventory.getItem(4).isEmpty());
    }

    protected void setFlag(int pFlagId, boolean pValue) {
        byte b0 = (Byte) this.entityData.get(DATA_ID_FLAGS);
        if (pValue) {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | pFlagId));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & ~pFlagId));
        }
    }
//TODO AGREGAR OWNER DE ENTIDAD, QUIEN LO INVOCO
    protected boolean getFlag(int pFlagId) {
        return ((Byte) this.entityData.get(DATA_ID_FLAGS) & pFlagId) != 0;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }

        ListTag itemList = new ListTag();

        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack item = this.inventory.getItem(i);
            if (!item.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)i);
                item.save(compoundTag);
                itemList.add(compoundTag);
            }
        }

        pCompound.put("Items", itemList);
        pCompound.put("inventory", itemHandler.serializeNBT());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        ListTag itemList = pCompound.getList("Items", 10);

        for(int i = 0; i < itemList.size(); ++i) {
            CompoundTag compoundTag = itemList.getCompound(i);
            int item = compoundTag.getByte("Slot") & 255;
            if (item < this.inventory.getContainerSize()) {
                this.inventory.setItem(item, ItemStack.of(compoundTag));
            }
        }

        this.updateContainerEquipment();

        itemHandler.deserializeNBT(pCompound.getCompound("inventory"));
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.getItem() != ModItem.AUTOMATON_SPAWN_EGG.get() && this.isAlive() && !pPlayer.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                this.startWatching(pPlayer);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

//        private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
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
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    public Player getWatchingPlayer() {
        return this.watchingPlayer;
    }

    public void setWatchingPlayer(@Nullable Player pPlayer) {
        this.watchingPlayer = pPlayer;
    }

    @Override
    public void setContainer(Container pInventory) {
        this.inventory = (SimpleContainer) pInventory;
    }

    public Automaton getAutomaton() {
        return this;
    }

    public Container getContainer() {
        return this.inventory;
    }

    private void startWatching(Player pPlayer) {
        this.setWatchingPlayer(pPlayer);
        this.openModuleScreen(pPlayer, this.getDisplayName());
    }

    /**Se llama cada vez que cambia el contenedor, evento establecido en this.inventory.addListener(this);
     */
    public void containerChanged(Container container) {

    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return super.getDimensions(pPose);
        //TODO CREAR CONDICIONAL Y RETORNAR LEGGED_DIMENSIONS O NO_LEGS_DIMENSIONS SEGUN SEA EL CASO
    }

    static {
        AUTOMATON_DATA = SynchedEntityData.defineId(Automaton.class, ModEDataSerializers.AUTOMATON_DATA);
        LEGGED_DIMENSIONS = EntityDimensions.fixed(0.8f,1f);
        NO_LEGS_DIMENSIONS = EntityDimensions.fixed(0.8f,1.9f);
        DATA_ID_FLAGS = SynchedEntityData.defineId(AbstractHorse.class, EntityDataSerializers.BYTE);
    }
}
