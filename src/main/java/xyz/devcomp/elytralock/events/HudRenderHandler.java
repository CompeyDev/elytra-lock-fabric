package xyz.devcomp.elytralock.events;

import xyz.devcomp.elytralock.ElytraLock;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;

public class HudRenderHandler implements HudRenderCallback {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    public void onHudRender(DrawContext context, float delta) {
        // FIXME: Perhaps don't check whether the elytra is locked on every frame
        Identifier icon = new Identifier("elytra-lock",
                "textures/gui/" + (ElytraLock.isLocked() ? "locked" : "unlocked") + ".png");

        Window window = ElytraLock.client.getWindow();
        int width = window.getScaledWidth(), height = window.getScaledHeight();
        
        context.drawGuiTexture(icon, (width / 2) + 95, height - HEIGHT - 3, WIDTH, HEIGHT);
    }
} 