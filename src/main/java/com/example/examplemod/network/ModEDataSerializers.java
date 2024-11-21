package com.example.examplemod.network;

import com.example.examplemod.world.entity.AutomatonData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.network.syncher.EntityDataSerializers.registerSerializer;

public class ModEDataSerializers {
    public static final EntityDataSerializer<AutomatonData> AUTOMATON_DATA;

    static {
        AUTOMATON_DATA = new EntityDataSerializer.ForValueType<AutomatonData>() {
            public void write(FriendlyByteBuf friendlyByteBuf, AutomatonData automatonData) {
                friendlyByteBuf.writeVarInt(automatonData.getEnergy());
                friendlyByteBuf.writeMap(automatonData.getEntireBody(), FriendlyByteBuf::writeInt, FriendlyByteBuf::writeUtf);
            }

            public @NotNull AutomatonData read(FriendlyByteBuf friendlyByteBuf) {
                return new AutomatonData(
                        friendlyByteBuf.readVarInt(),
                        friendlyByteBuf.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readUtf)
                );
            }
        };

        registerSerializer(AUTOMATON_DATA);
    }
}
