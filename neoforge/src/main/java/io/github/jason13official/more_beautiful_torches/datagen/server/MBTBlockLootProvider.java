package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

public class MBTBlockLootProvider extends BlockLootSubProvider {

  public MBTBlockLootProvider(HolderLookup.Provider registries) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
  }

  @Override
  protected void generate() {
    // wall blocks are built with .dropsLike(standing), so their loot table id already
    // resolves to the standing block's table (mirrors vanilla torch/wall_torch).
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

  // BlockLootSubProvider#generate(BiConsumer) validates that every block in the
  // registry (including vanilla's) has a loot table, since vanilla's own subclass
  // is expected to cover all of them. We only own our own blocks, so bypass that
  // whole-registry check and just emit what we actually registered above.
  @Override
  public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    this.generate();
    this.map.forEach(output);
  }
}
