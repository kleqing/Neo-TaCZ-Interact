package com.kleqing.neotaczinteract.mixin;

import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InteractKeyTextOverlay.class, remap = false)
public class MixinInteractKeyTextOverlay {

    @Inject(method = "renderText", at = @At("HEAD"), cancellable = true)
    private static void inject_renderText(GuiGraphics graphics, int width, int height, Font font, String keyName, boolean willFilterByHand, CallbackInfo ci) {
        Component title = Component.literal("Press " + keyName + " to interact");
        
        int textWidth = font.width(title);
        int x = (width - textWidth) / 2;
        int y = height / 2 + 30;
        
        // Draw background
        graphics.fill(x - 4, y - 4, x + textWidth + 4, y + 9 + 4, 0x80000000);
        
        // Draw text with shadow
        graphics.drawString(font, title, x, y, 0xFFFFFF, true);
        
        if (willFilterByHand) {
            Component filter = Component.translatable("gui.tacz.interact_key.text.gun_smith_table_filter");
            int filterWidth = font.width(filter);
            int fx = (width - filterWidth) / 2;
            int fy = y + 15;
            
            // Draw background for filter text
            graphics.fill(fx - 4, fy - 4, fx + filterWidth + 4, fy + 9 + 4, 0x80000000);
            graphics.drawString(font, filter, fx, fy, ChatFormatting.GRAY.getColor(), true);
        }
        
        ci.cancel();
    }
}
