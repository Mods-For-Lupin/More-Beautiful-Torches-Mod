package io.github.jason13official.more_beautiful_torches.datagen.server;

import java.util.List;
import java.util.Set;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class MBTDatagenServer {

  public static void init(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

    generator.addProvider(event.includeServer(), new MBTRecipeProvider(output));

    generator.addProvider(event.includeServer(), new LootTableProvider(
        output,
        Set.of(),
        List.of(new LootTableProvider.SubProviderEntry(MBTBlockLootProvider::new, LootContextParamSets.BLOCK))
    ));

    generator.addProvider(event.includeServer(),
        new MBTBlockTagProvider(output, event.getLookupProvider(), existingFileHelper));
  }
}
