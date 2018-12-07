package space.bbkr.gluon;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class UpsideDownAnvilRecipe implements IRecipe {

	private final ResourceLocation id;
	private final String group;
	private final Ingredient input;
	private final ItemStack output;

	public UpsideDownAnvilRecipe(ResourceLocation id, String group, Ingredient input, ItemStack output ) {
		this.id = id;
		this.group = group;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(IInventory inv, World world) {
		return inv instanceof DummyAnvilInventory && input.test(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(IInventory iInventory) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int i, int i1) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Gluon.CRUSHING;
	}

	public static class Serializer implements IRecipeSerializer<UpsideDownAnvilRecipe> {
		public Serializer() {
		}

		public UpsideDownAnvilRecipe read(ResourceLocation id, JsonObject json) {
			String group = JsonUtils.getString(json, "group", "");
			Ingredient input;
			if (JsonUtils.isJsonArray(json, "ingredient")) {
				input = Ingredient.deserialize(JsonUtils.getJsonArray(json, "ingredient"));
			} else {
				input = Ingredient.deserialize(JsonUtils.getJsonObject(json, "ingredient"));
			}

			String result = JsonUtils.getString(json, "result");
			Item resultItem = IRegistry.ITEM.get(new ResourceLocation(result));
			if (resultItem != null) {
				ItemStack output = new ItemStack(resultItem);
				return new UpsideDownAnvilRecipe(id, group, input, output);
			} else {
				throw new IllegalStateException(result + " did not exist");
			}
		}

		public UpsideDownAnvilRecipe read(ResourceLocation id, PacketBuffer buffer) {
			String group = buffer.readString(32767);
			Ingredient input = Ingredient.read(buffer);
			ItemStack output = buffer.readItemStack();
			return new UpsideDownAnvilRecipe(id, group, input, output);
		}

		public void write(PacketBuffer buffer, UpsideDownAnvilRecipe recipe) {
			buffer.writeString(recipe.group);
			recipe.input.write(buffer);
			buffer.writeItemStack(recipe.output);
		}

		public String getId() {
			return "crushing";
		}
	}
}
