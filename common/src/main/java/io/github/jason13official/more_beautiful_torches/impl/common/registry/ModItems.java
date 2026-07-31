package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ModItems {

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {
    registerAll(ModBlocks.TORCHES, consumer);
    registerAll(ModBlocks.REDSTONE_TORCHES, consumer);
    registerAll(ModBlocks.SOUL_TORCHES, consumer);
  }

  private static void registerAll(List<TorchEntry> entries, BiConsumer<Item, ResourceLocation> consumer) {
    for (TorchEntry entry : entries) {
      Item item = new StandingAndWallBlockItem(entry.standing(), entry.wall(), new Item.Properties(), Direction.DOWN);
      consumer.accept(item, new ResourceLocation(Constants.MOD_ID, entry.name()));
    }
  }
}
