package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MBTRecipeProvider extends RecipeProvider {

  public MBTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      torch(recipeOutput, entry.standing(), entry.source(),
          Ingredient.of(Items.STICK), Ingredient.of(ItemTags.COALS));
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      torch(recipeOutput, entry.standing(), entry.source(),
          Ingredient.of(Items.STICK), Ingredient.of(ItemTags.COALS), Ingredient.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND));
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      torch(recipeOutput, entry.standing(), entry.source(),
          Ingredient.of(Items.STICK), Ingredient.of(Items.REDSTONE));
    }
  }

  private void torch(RecipeOutput recipeOutput, Block standing, Block source, Ingredient... ingredients) {
    ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, standing, 4)
        .requires(source);
    for (Ingredient ingredient : ingredients) {
      builder.requires(ingredient);
    }
    builder.unlockedBy(getHasName(source), has(source))
        .save(recipeOutput);
  }
}
