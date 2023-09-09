package technobaboo.crazygadgets.util;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import technobaboo.crazygadgets.packet.KeybindPacket;

/**
 * Checks if a key is pressed, on both the client or server. On the server, it's
 * saved for every PlayerEntity by mapping their UUID to an instance of this
 * class.
 * Then, when a specific PlayerEntity presses a key,
 * it's only pressed for that player's UUID.
 */
public class ModKeyBindings {
    public static final HashMap<UUID, ModKeyBindings> PLAYER_KEYS = new HashMap<>();

    public static ModKeyBindings getPlayer(PlayerEntity player) {
        PLAYER_KEYS.putIfAbsent(player.getUuid(), new ModKeyBindings());
        return PLAYER_KEYS.get(player.getUuid());
    }

    private boolean pressingJump;
    private boolean pressingSprint;
    private boolean pressingForward;
    private boolean pressingBack;
    private boolean pressingLeft;
    private boolean pressingRight;

    public boolean isPressingJump() {
        return pressingJump;
    }

    public boolean isPressingSprint() {
        return pressingSprint;
    }

    public boolean isPressingForward() {
        return pressingForward;
    }

    public boolean isPressingBack() {
        return pressingBack;
    }

    public boolean isPressingLeft() {
        return pressingLeft;
    }

    public boolean isPressingRight() {
        return pressingRight;
    }

    public static void pressedKeyOnServer(UUID uuid, KeybindPacket.Key key, boolean keyDown) {
        PLAYER_KEYS.putIfAbsent(uuid, new ModKeyBindings());

        switch (key) {
            case JUMP -> PLAYER_KEYS.get(uuid).pressingJump = keyDown;

            case SPRINT -> PLAYER_KEYS.get(uuid).pressingSprint = keyDown;

            case FORWARD -> PLAYER_KEYS.get(uuid).pressingForward = keyDown;

            case BACK -> PLAYER_KEYS.get(uuid).pressingBack = keyDown;

            case LEFT -> PLAYER_KEYS.get(uuid).pressingLeft = keyDown;

            case RIGHT -> PLAYER_KEYS.get(uuid).pressingRight = keyDown;
        }
    }

    public static void onPlayerQuit(PlayerEntity player) {
        PLAYER_KEYS.remove(player.getUuid());
    }
}