package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MBTRecipeProvider extends RecipeProvider {

  public MBTRecipeProvider(PackOutput output) {
    super(output);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> writer) {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      torch(writer, entry.standing(), entry.source(), Ingredient.of(Items.STICK));
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      torch(writer, entry.standing(), entry.source(), Ingredient.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND));
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      torch(writer, entry.standing(), entry.source(), Ingredient.of(Items.REDSTONE));
    }
  }

  private void torch(Consumer<FinishedRecipe> writer, Block standing, Block source, Ingredient catalyst) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, standing, 4)
        .requires(source)
        .requires(catalyst)
        .unlockedBy(getHasName(source), has(source))
        .save(writer);
  }
}
