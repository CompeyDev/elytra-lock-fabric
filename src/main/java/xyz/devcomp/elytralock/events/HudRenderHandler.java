package xyz.devcomp.elytralock.events;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import xyz.devcomp.elytralock.ElytraLock;

public class HudRenderHandler implements HudLayerRegistrationCallback {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private static final Identifier LAYER_ID = Identifier.of("elytralock", "toggle-status-indicator");

    @Override
    public void register(LayeredDrawerWrapper layeredDrawer) {
        layeredDrawer.attachLayerAfter(IdentifiedLayer.HOTBAR_AND_BARS, LAYER_ID, HudRenderHandler::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!MinecraftClient.isHudEnabled())
            return;

        int offset = switch (ElytraLock.client.player.getMainArm()) {
            case Arm.RIGHT:
                yield 95;
            case Arm.LEFT:
                yield -115;
        };

        Identifier icon = Identifier.of("elytra-lock",
                "textures/gui/" + (ElytraLock.isLocked() ? "locked" : "unlocked") + ".png");

        Window window = ElytraLock.client.getWindow();
        int width = window.getScaledWidth(), height = window.getScaledHeight();

        context.drawTexture(RenderLayer::getGuiTextured, icon, (width / 2) + offset, height - HEIGHT - 3, 0, 0, WIDTH,
                HEIGHT, WIDTH, HEIGHT);
    }
}