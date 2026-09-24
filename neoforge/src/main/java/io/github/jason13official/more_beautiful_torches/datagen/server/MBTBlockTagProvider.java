package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MBTBlockTagProvider extends BlockTagsProvider {

  public static final TagKey<Block> TORCHES =
      TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "torches"));

  public static final TagKey<Block> NORMAL_TORCHES =
      TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "normal_torches"));
  public static final TagKey<Block> SOUL_TORCHES =
      TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soul_torches"));
  public static final TagKey<Block> REDSTONE_TORCHES =
      TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "redstone_torches"));

  public MBTBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
      ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
  }

  @Override
  public String getName() {
    return "More Beautiful Torches Block Tags";
  }

  @Override
  protected void addTags(HolderLookup.Provider registries) {
    IntrinsicTagAppender<Block> all = tag(TORCHES);
    IntrinsicTagAppender<Block> normal = tag(NORMAL_TORCHES);
    IntrinsicTagAppender<Block> soul = tag(SOUL_TORCHES);
    IntrinsicTagAppender<Block> redstone = tag(REDSTONE_TORCHES);

    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      normal.add(entry.standing()).add(entry.wall());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      soul.add(entry.standing()).add(entry.wall());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      redstone.add(entry.standing()).add(entry.wall());
    }
  }
}
