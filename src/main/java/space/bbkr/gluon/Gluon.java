package space.bbkr.gluon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;
import net.minecraft.potion.Potion;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import org.dimdev.rift.listener.*;

import java.util.Map;

public class Gluon implements BlockAdder, ItemAdder, RecipeAdder {

    public static final BlockRope ROPE = new BlockRope(Block.Properties.create(Material.VINE));
    public static final BlockUpsideDownAnvil UPSIDE_DOWN_ANVIL = new BlockUpsideDownAnvil(Block.Properties.create(Material.IRON));
    public static final ItemTitleScroll TITLE_SCROLL = new ItemTitleScroll(new Item.Properties().group(ItemGroup.MISC));
    public static final IRecipeSerializer<UpsideDownAnvilRecipe> CRUSHING = RecipeSerializers.register(new UpsideDownAnvilRecipe.Serializer());

    @Override
    public void registerBlocks() {
        Block.register(new ResourceLocation("gluon:rope"), ROPE);
        Block.register(new ResourceLocation("gluon:upside_down_anvil"), UPSIDE_DOWN_ANVIL);
    }


    @Override
    public void registerItems() {
        Item.register(ROPE, ItemGroup.TOOLS);
        Item.register(UPSIDE_DOWN_ANVIL, ItemGroup.DECORATIONS);
        Item.register(new ResourceLocation("gluon:title_scroll"), TITLE_SCROLL);
    }


    @Override
    public void addRecipes(Map<ResourceLocation, IRecipe> target, IResourceManager resourceManager) {
//        target.put(resourceManager.getResource(new ResourceLocation("gluon", "recipes/cobblestone"), CRUSHING.));
    }
}
