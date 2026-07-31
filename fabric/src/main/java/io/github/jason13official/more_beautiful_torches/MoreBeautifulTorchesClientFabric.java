package io.github.jason13official.more_beautiful_torches;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class MoreBeautifulTorchesClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreBeautifulTorchesClient.init();

    registerCutout(ModBlocks.TORCHES);
    registerCutout(ModBlocks.SOUL_TORCHES);
    registerCutout(ModBlocks.REDSTONE_TORCHES);
  }

  private static void registerCutout(List<TorchEntry> entries) {
    for (TorchEntry entry : entries) {
      BlockRenderLayerMap.INSTANCE.putBlock(entry.standing(), RenderType.cutout());
      BlockRenderLayerMap.INSTANCE.putBlock(entry.wall(), RenderType.cutout());
    }
  }
}
