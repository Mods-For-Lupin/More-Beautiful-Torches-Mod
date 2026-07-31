package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MBTRecipeProvider extends RecipeProvider.Runner {

  public MBTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  @Override
  public String getName() {
    return "More Beautiful Torches Recipes";
  }

  @Override
  protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
    return new RecipeProvider(registries, output) {
      @Override
      protected void buildRecipes() {
        for (TorchEntry entry : ModBlocks.TORCHES) {
          torch(output, entry.standing(), entry.source(), Ingredient.of(Items.STICK));
        }
        for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
          torch(output, entry.standing(), entry.source(), Ingredient.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND));
        }
        for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
          torch(output, entry.standing(), entry.source(), Ingredient.of(Items.REDSTONE));
        }
      }

      private void torch(RecipeOutput output, Block standing, Block source, Ingredient catalyst) {
        shapeless(RecipeCategory.DECORATIONS, standing, 4)
            .requires(source)
            .requires(catalyst)
            .unlockedBy(getHasName(source), has(source))
            .save(output);
      }
    };
  }
}
