package io.github.jason13official.more_beautiful_torches.datagen.server;

import java.util.List;
import java.util.Set;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent.Client;

public class MBTDatagenServer {

  public static void init(Client event) {
    event.createProvider(MBTRecipeProvider::new);

    event.createProvider((output, registries) -> new LootTableProvider(
        output,
        Set.of(),
        List.of(new LootTableProvider.SubProviderEntry(MBTBlockLootProvider::new, LootContextParamSets.BLOCK)),
        registries
    ));

    event.createProvider(MBTBlockTagProvider::new);
  }
}
