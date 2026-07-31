package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class MBTBlockLootProvider extends BlockLootSubProvider {

  public MBTBlockLootProvider(HolderLookup.Provider registries) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
  }

  // wall blocks are built with .overrideLootTable(standing.getLootTable()), so their
  // loot table id already resolves to the standing block's table (mirrors vanilla
  // torch/wall_torch) - getKnownBlocks() only needs to cover the standing blocks.
  @Override
  protected Iterable<Block> getKnownBlocks() {
    return Stream.of(ModBlocks.TORCHES, ModBlocks.SOUL_TORCHES, ModBlocks.REDSTONE_TORCHES)
        .flatMap(entries -> entries.stream().map(ModBlocks.TorchEntry::standing))
        .toList();
  }

  @Override
  protected void generate() {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      this.dropSelf(entry.standing());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      this.dropSelf(entry.standing());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      this.dropSelf(entry.standing());
    }
  }
}
