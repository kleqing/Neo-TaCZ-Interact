package com.kleqing.neotaczinteract;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.config.util.InteractKeyConfigRead;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = NeoTaczInteract.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NeoTaczInteractEventHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        
        // Only necessary when sneaking (crouching)
        if (player.isSecondaryUseActive()) {
            ItemStack mainHandItem = player.getMainHandItem();

            // Check if player is holding a gun
            if (mainHandItem.getItem() instanceof IGun) {
                BlockState blockState = event.getLevel().getBlockState(event.getPos());
                
                // Check if the block is allowed to be interacted with according to TACZ config
                if (InteractKeyConfigRead.canInteractBlock(blockState)) {
                    // Force allow block interaction, bypassing the sneak check
                    event.setUseBlock(TriState.TRUE);
                }
            }
        }
    }
}
