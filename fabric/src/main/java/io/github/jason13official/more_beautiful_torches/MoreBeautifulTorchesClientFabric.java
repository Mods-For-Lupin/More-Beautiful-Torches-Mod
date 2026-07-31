package io.github.jason13official.more_beautiful_torches;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class MoreBeautifulTorchesClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreBeautifulTorchesClient.init();

    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      BlockRenderLayerMap.INSTANCE.putBlock(entry.standing(), RenderType.cutout());
      BlockRenderLayerMap.INSTANCE.putBlock(entry.wall(), RenderType.cutout());
    }
  }
}
