package space.bbkr.gluon.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import space.bbkr.gluon.ITitle;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends Entity implements ITitle {
	public MixinEntityPlayer(EntityType<?> type, World world) {
		super(type, world);
	}

	private ITextComponent title = null;

	public ITextComponent getTitle() {
		return title;
	}

	public void setTitle(ITextComponent newTitle) {
		this.title = newTitle;
	}

//	public boolean hasTitle(ITitle title) {
//		return title.getTitle() != null && !title.getTitle().equals("");
//	}
}
