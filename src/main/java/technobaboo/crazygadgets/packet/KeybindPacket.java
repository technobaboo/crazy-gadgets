package technobaboo.crazygadgets.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import technobaboo.crazygadgets.CrazyGadgets;
import technobaboo.crazygadgets.util.ModKeyBindings;

public record KeybindPacket(Key keybind, boolean pressed) {

    public static final Identifier KEY_PACKET_ID = new Identifier(CrazyGadgets.MOD_ID, "key");

    public enum Key {
        JUMP, SPRINT, FORWARD, BACK, LEFT, RIGHT
    }

    public PacketByteBuf encode() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(this.keybind);
        buf.writeBoolean(this.pressed);
        return buf;
    }

    public static KeybindPacket decode(PacketByteBuf buf) {
        return new KeybindPacket(buf.readEnumConstant(Key.class), buf.readBoolean());
    }

    public static void registerServerHandler() {
        ServerPlayNetworking.registerGlobalReceiver(KEY_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            KeybindPacket packet = decode(buf);
            ModKeyBindings.pressedKeyOnServer(player.getUuid(), packet.keybind, packet.pressed);
        });
    }
}