package xyz.devcomp.elytralock.events;

import xyz.devcomp.elytralock.ElytraLock;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.renderer.RenderPipelines;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import com.mojang.blaze3d.platform.Window;

import org.jetbrains.annotations.NotNull;

public class HudRenderHandler implements HudElement {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath("elytra-lock", "toggle-status-indicator");

    public static void register() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, LAYER_ID, new HudRenderHandler());
    }

    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, @NotNull DeltaTracker deltaTracker) {
        if (ElytraLock.client.options.hideGui || !ElytraLock.config.getInstance().displayHUDIcon)
            return;

        assert ElytraLock.client.player != null;
        int offset = switch (ElytraLock.client.player.getMainArm()) {
            case HumanoidArm.RIGHT -> 95;
            case HumanoidArm.LEFT -> -115;
        };

        Identifier icon = Identifier.fromNamespaceAndPath("elytra-lock",
                "textures/gui/" + (ElytraLock.isLocked() ? "locked" : "unlocked") + ".png");

        Window window = ElytraLock.client.getWindow();
        int width = window.getGuiScaledWidth(), height = window.getGuiScaledHeight();

        graphics.blit(RenderPipelines.GUI_TEXTURED, icon, (width / 2) + offset, height - HEIGHT - 3, 0, 0, WIDTH,
                HEIGHT, WIDTH, HEIGHT);
    }
}