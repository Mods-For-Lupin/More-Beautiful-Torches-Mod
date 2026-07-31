package io.github.jason13official.more_beautiful_torches.datagen.client;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MBTItemModelProvider extends ItemModelProvider {

  public MBTItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
    super(output, modid, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      item(entry);
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      item(entry);
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      item(entry);
    }
  }

  private void item(ModBlocks.TorchEntry entry) {
    ResourceLocation torchTexture = modLoc("block/" + entry.name());
    withExistingParent(entry.name(), mcLoc("item/generated"))
        .texture("layer0", torchTexture);
  }
}
