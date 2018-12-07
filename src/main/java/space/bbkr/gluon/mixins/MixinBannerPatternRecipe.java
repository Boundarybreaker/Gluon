package space.bbkr.gluon.mixins;

import net.minecraft.item.crafting.BannerAddPatternRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BannerAddPatternRecipe.class)
public abstract class MixinBannerPatternRecipe {

	@ModifyConstant(method = "matches", constant = @Constant(intValue = 6))
	private int changeBannerPatternLimit(int orig) {
		return 16;
	}
}
