package net.cerulan.fairenchanting.mixin;

import net.cerulan.fairenchanting.FairEnchanting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	@Shadow public abstract void addExperience(int experience);

	@Shadow protected int enchantmentTableSeed;

	@Shadow public ScreenHandler currentScreenHandler;

	protected PlayerMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "applyEnchantmentCosts", cancellable = true)
	private void modifyEnchantmentCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo info) {
		if (currentScreenHandler instanceof EnchantmentScreenHandler) {
			FairEnchanting.logger.debug("FairEnchanting Replacing level cost");
			int costLevel = ((EnchantmentScreenHandler) currentScreenHandler).enchantmentPower[experienceLevels - 1];
			addExperience(-FairEnchanting.getExperienceCostAtLevel(costLevel, experienceLevels));
			this.enchantmentTableSeed = this.random.nextInt();
			info.cancel();
		} else {
			FairEnchanting.logger.debug("Skipping fair cost calculation (not done through normal enchanting table)");
		}
	}


}
