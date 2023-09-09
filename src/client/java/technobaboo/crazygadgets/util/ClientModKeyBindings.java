package technobaboo.crazygadgets.util;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import technobaboo.crazygadgets.packet.KeybindPacket;

public class ClientModKeyBindings extends ModKeyBindings {

    public static boolean clickingJump;
    public static boolean clickingSprint;
    public static boolean clickingForward;
    public static boolean clickingBack;
    public static boolean clickingLeft;
    public static boolean clickingRight;

    private static boolean sentJumpPacket;
    private static boolean sentSprintPacket;
    private static boolean sentForwardPacket;
    private static boolean sentBackPacket;
    private static boolean sentLeftPacket;
    private static boolean sentRightPacket;

    public static void onStartTick(MinecraftClient client) {

        clickingJump = client.options.jumpKey.isPressed();
        clickingSprint = client.options.sprintKey.isPressed();
        clickingForward = client.options.forwardKey.isPressed();
        clickingBack = client.options.backKey.isPressed();
        clickingLeft = client.options.leftKey.isPressed();
        clickingRight = client.options.rightKey.isPressed();

        if (client.world != null) {

            if (clickingJump && sentJumpPacket) {
                sendKey(KeybindPacket.Key.JUMP, true);
                sentJumpPacket = false;
            }

            if (clickingSprint && sentSprintPacket) {
                sendKey(KeybindPacket.Key.SPRINT, true);
                sentSprintPacket = false;
            }

            if (clickingForward && sentForwardPacket) {
                sendKey(KeybindPacket.Key.FORWARD, true);
                sentForwardPacket = false;
            }

            if (clickingBack && sentBackPacket) {
                sendKey(KeybindPacket.Key.BACK, true);
                sentBackPacket = false;
            }

            if (clickingLeft && sentLeftPacket) {
                sendKey(KeybindPacket.Key.LEFT, true);
                sentLeftPacket = false;
            }

            if (clickingRight && sentRightPacket) {
                sendKey(KeybindPacket.Key.RIGHT, true);
                sentRightPacket = false;
            }

            if (!clickingJump && !sentJumpPacket) {
                sendKey(KeybindPacket.Key.JUMP, false);
                sentJumpPacket = true;
            }

            if (!clickingSprint && !sentSprintPacket) {
                sendKey(KeybindPacket.Key.SPRINT, false);
                sentSprintPacket = true;
            }

            if (!clickingForward && !sentForwardPacket) {
                sendKey(KeybindPacket.Key.FORWARD, false);
                sentForwardPacket = true;
            }

            if (!clickingBack && !sentBackPacket) {
                sendKey(KeybindPacket.Key.BACK, false);
                sentBackPacket = true;
            }

            if (!clickingLeft && !sentLeftPacket) {
                sendKey(KeybindPacket.Key.LEFT, false);
                sentLeftPacket = true;
            }

            if (!clickingRight && !sentRightPacket) {
                sendKey(KeybindPacket.Key.RIGHT, false);
                sentRightPacket = true;
            }
        }
    }

    protected static void sendKey(KeybindPacket.Key key, boolean pressed) {
        ClientPlayNetworking.send(KeybindPacket.KEY_PACKET_ID, new KeybindPacket(key, pressed).encode());
    }

    @Override
    public boolean isPressingBack() {
        return ClientModKeyBindings.clickingBack;
    }

    @Override
    public boolean isPressingForward() {
        return ClientModKeyBindings.clickingForward;
    }

    @Override
    public boolean isPressingJump() {
        return ClientModKeyBindings.clickingJump;
    }

    @Override
    public boolean isPressingLeft() {
        return ClientModKeyBindings.clickingLeft;
    }

    @Override
    public boolean isPressingRight() {
        return ClientModKeyBindings.clickingRight;
    }

    @Override
    public boolean isPressingSprint() {
        return ClientModKeyBindings.clickingSprint;
    }
}