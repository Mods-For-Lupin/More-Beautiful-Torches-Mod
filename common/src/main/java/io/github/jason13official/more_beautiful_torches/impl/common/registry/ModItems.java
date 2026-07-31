package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.more_beautiful_torches.Constants;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ModItems {

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      Item item = new StandingAndWallBlockItem(entry.standing(), entry.wall(), new Item.Properties(), Direction.DOWN);
      consumer.accept(item, new ResourceLocation(Constants.MOD_ID, entry.name()));
    }
  }
}
