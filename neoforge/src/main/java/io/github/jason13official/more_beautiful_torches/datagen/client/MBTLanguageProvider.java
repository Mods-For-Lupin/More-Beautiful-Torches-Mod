package io.github.jason13official.more_beautiful_torches.datagen.client;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MBTLanguageProvider extends LanguageProvider {

  public MBTLanguageProvider(PackOutput output) {
    super(output, Constants.MOD_ID, "en_us");
  }

  @Override
  protected void addTranslations() {
    add("itemGroup.moreBeautifulTorches", Constants.MOD_NAME);

    for (TorchEntry entry : ModBlocks.TORCHES) {
      addTorch(entry, "Torch", "Wall Torch");
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      addTorch(entry, "Soul Torch", "Soul Wall Torch");
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      addTorch(entry, "Redstone Torch", "Redstone Wall Torch");
    }
  }

  private void addTorch(TorchEntry entry, String standingSuffix, String wallSuffix) {
    String material = displayName(entry.source());
    String standingName = material + " " + standingSuffix;

    add(entry.standing(), standingName);

    // "block.minecraft.torch"
    // "block.minecraft.wall_torch"
    add(blockKey(entry.wall()), material + " " + wallSuffix);

    // "item.minecraft.torch"
    add(itemKey(entry.name()), standingName);
  }

  private static String blockKey(Block block) {
    return "block." + Constants.MOD_ID + "." + BuiltInRegistries.BLOCK.getKey(block).getPath();
  }

  private static String itemKey(String name) {
    return "item." + Constants.MOD_ID + "." + name;
  }

  // registry path with underscores turned into spaces and each word capitalized
  private static String displayName(Block source) {
    String path = BuiltInRegistries.BLOCK.getKey(source).getPath();
    StringBuilder result = new StringBuilder();
    for (String word : path.split("_")) {
      if (result.length() > 0) {
        result.append(' ');
      }
      result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
    }
    return result.toString();
  }
}
