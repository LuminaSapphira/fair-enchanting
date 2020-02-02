package net.cerulan.fairenchanting.mixin;

import net.cerulan.fairenchanting.FairEnchanting;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/container/AnvilContainer$2")
public class MixinAnvilContainerResultSlot {

    @Redirect(method = "onTakeItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", require = 1,
    at = @At(value = "INVOKE", target = "net.minecraft.entity.player.PlayerEntity.addExperienceLevels(I)V"))
    private void addExperienceLevels(PlayerEntity playerEntity, int levels) {
        int levelCost = -levels;
        System.out.println("[Fair Enchanting] Taking level cost : " + levelCost);
        playerEntity.addExperience(-FairEnchanting.getExperienceCostAtLevel(levelCost, levelCost));
    }

}
