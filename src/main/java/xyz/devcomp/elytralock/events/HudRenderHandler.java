package xyz.devcomp.elytralock.events;

import xyz.devcomp.elytralock.ElytraLock;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class HudRenderHandler implements HudRenderCallback {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
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

        context.drawTexture(icon, (width / 2) + offset, height - HEIGHT - 3, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
    }
}