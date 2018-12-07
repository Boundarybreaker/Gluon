package space.bbkr.gluon;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;

public class ItemTitleScroll extends Item {

	public ItemTitleScroll(Properties properties) {
		super(properties);
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx) {
		((ITitle)ctx.getPlayer()).setTitle(ctx.getItem().getDisplayName());
		return EnumActionResult.SUCCESS;
	}


}
