package com.kleqing.neotaczinteract.mixin;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.input.InteractKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void inject_checkSwapHandAndMakeRightClickRequest(CallbackInfo ci) {
        if (player == null || player.isSpectator() || !(player.getMainHandItem().getItem() instanceof IGun)) {
            return;
        }
        
        // If TACZ's interact key shares the same physical key as swap offhand,
        // consume the swap offhand clicks to prevent the swap from firing while holding a gun.
        if (InteractKey.INTERACT_KEY.same(options.keySwapOffhand)) {
            while (options.keySwapOffhand.consumeClick()) {}
        }
    }

    @Shadow @Final public Options options;
    @Shadow @Nullable public LocalPlayer player;
}
