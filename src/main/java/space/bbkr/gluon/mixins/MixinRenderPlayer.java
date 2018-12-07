package space.bbkr.gluon.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.gluon.ITitle;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends Render {
	public MixinRenderPlayer(RenderManager manager) {
		super(manager);
	}

	@Inject(method = "renderEntityName", at = @At("HEAD"))
	public void renderTitle(AbstractClientPlayer p_renderEntityName_1_, double p_renderEntityName_2_, double p_renderEntityName_4_, double p_renderEntityName_6_, String p_renderEntityName_8_, double p_renderEntityName_9, CallbackInfo ci) {
		if (hasTitle((ITitle) p_renderEntityName_1_)) {
			this.renderLivingLabel(p_renderEntityName_1_, ((ITitle) p_renderEntityName_1_).getTitle().getFormattedText(), p_renderEntityName_2_, p_renderEntityName_4_, p_renderEntityName_6_, 64);
			p_renderEntityName_4_ += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F);
		}
	}

	private boolean hasTitle(ITitle title) {
		return title.getTitle() != null;
	}
}
