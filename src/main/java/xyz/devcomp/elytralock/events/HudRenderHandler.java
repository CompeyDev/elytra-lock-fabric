package xyz.devcomp.elytralock.events;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import xyz.devcomp.elytralock.ElytraLock;

public class HudRenderHandler implements HudElement {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private static final Identifier LAYER_ID = Identifier.of("elytralock", "toggle-status-indicator");

    public static void register() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, LAYER_ID, new HudRenderHandler());
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {
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

        context.drawTexture(RenderPipelines.GUI_TEXTURED, icon, (width / 2) + offset, height - HEIGHT - 3, 0, 0, WIDTH,
                HEIGHT, WIDTH, HEIGHT);
    }
}