package space.bbkr.gluon.mixins;

import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ItemBanner.class)
public abstract class MixinBannerTooltip {

	@Inject(method = "appendHoverTextFromTileEntityTag",
			at = @At("TAIL"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION,
			remap = false)
	private static void appendBannerHoverText(ItemStack p_appendHoverTextFromTileEntityTag_0_, List<ITextComponent> p_appendHoverTextFromTileEntityTag_1_, CallbackInfo ci,
									  NBTTagCompound lvt_2_1_, NBTTagList lvt_3_1_) {
		if (lvt_3_1_ != null) {
			if (lvt_3_1_.size() > 6) {
				p_appendHoverTextFromTileEntityTag_1_.add((new TextComponentTranslation("tooltip.gluon.banner."+(lvt_3_1_.size()-6), new Object[0])).applyTextStyle(TextFormatting.GRAY));
			}
		}
	}
}
