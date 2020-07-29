package net.cerulan.fairenchanting.mixin;

import net.cerulan.fairenchanting.FairEnchanting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class MixinAnvilScreenHandler {

    @Redirect(method = "onTakeOutput", require = 1,
    at = @At(value = "INVOKE", target = "net.minecraft.entity.player.PlayerEntity.addExperienceLevels(I)V"))
    private void addExperienceLevels(PlayerEntity playerEntity, int levels) {
        int levelCost = -levels;
        System.out.println("[Fair Enchanting] Taking level cost : " + levelCost);
        playerEntity.addExperience(-FairEnchanting.getExperienceCostAtLevel(levelCost, levelCost));
    }

}
