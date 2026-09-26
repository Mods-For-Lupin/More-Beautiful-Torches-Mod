package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class MBTBlockTagProvider extends BlockTagsProvider {

  public static final TagKey<Block> TORCHES =
      TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "torches"));

  public static final TagKey<Block> NORMAL_TORCHES =
      TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "normal_torches"));
  public static final TagKey<Block> SOUL_TORCHES =
      TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "soul_torches"));
  public static final TagKey<Block> COPPER_TORCHES =
      TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "copper_torches"));
  public static final TagKey<Block> REDSTONE_TORCHES =
      TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "redstone_torches"));

  public MBTBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(output, lookupProvider, Constants.MOD_ID);
  }

  @Override
  public String getName() {
    return "More Beautiful Torches Block Tags";
  }

  @Override
  protected void addTags(HolderLookup.Provider registries) {
    TagAppender<Block, Block> all = tag(TORCHES);
    TagAppender<Block, Block> normal = tag(NORMAL_TORCHES);
    TagAppender<Block, Block> soul = tag(SOUL_TORCHES);
    TagAppender<Block, Block> copper = tag(COPPER_TORCHES);
    TagAppender<Block, Block> redstone = tag(REDSTONE_TORCHES);

    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      normal.add(entry.standing()).add(entry.wall());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      soul.add(entry.standing()).add(entry.wall());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.COPPER_TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      copper.add(entry.standing()).add(entry.wall());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      all.add(entry.standing()).add(entry.wall());
      redstone.add(entry.standing()).add(entry.wall());
    }
  }
}
