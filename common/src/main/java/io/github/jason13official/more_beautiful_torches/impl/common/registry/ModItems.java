package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ModItems {

  public static void register(BiConsumer<Item, Identifier> consumer) {
    registerAll(ModBlocks.TORCHES, consumer);
    registerAll(ModBlocks.REDSTONE_TORCHES, consumer);
    registerAll(ModBlocks.SOUL_TORCHES, consumer);
  }

  private static void registerAll(List<TorchEntry> entries, BiConsumer<Item, Identifier> consumer) {
    for (TorchEntry entry : entries) {
      Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.name());
      Item.Properties properties = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
      Item item = new StandingAndWallBlockItem(entry.standing(), entry.wall(), Direction.DOWN, properties);
      consumer.accept(item, id);
    }
  }
}
