package io.github.jason13official.more_beautiful_torches.datagen.server;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

public class MBTBlockLootProvider extends BlockLootSubProvider {

  public MBTBlockLootProvider() {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags());
  }

  @Override
  protected void generate() {
    // wall blocks are built with .dropsLike(standing), so their loot table id already
    // resolves to the standing block's table (mirrors vanilla torch/wall_torch).
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      this.dropSelf(entry.standing());
    }
  }

  @Override
  public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
    this.generate();
    this.map.forEach(output);
  }
}
