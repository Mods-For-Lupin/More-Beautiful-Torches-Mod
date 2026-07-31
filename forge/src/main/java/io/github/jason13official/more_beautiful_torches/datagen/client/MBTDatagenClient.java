package io.github.jason13official.more_beautiful_torches.datagen.client;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.datagen.server.MBTDatagenServer;
import io.github.jason13official.more_beautiful_torches.platform.Services;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class MBTDatagenClient {

  public static void init(GatherDataEvent event) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      Constants.LOG.info("GatherDataEvent firing.");
    }

    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

    generator.addProvider(event.includeClient(), new MBTTorchTextureProvider(output, existingFileHelper));
    generator.addProvider(event.includeClient(), new MBTBlockStateProvider(output, Constants.MOD_ID, existingFileHelper));
    generator.addProvider(event.includeClient(), new MBTItemModelProvider(output, Constants.MOD_ID, existingFileHelper));
    generator.addProvider(event.includeClient(), new MBTLanguageProvider(output));

    MBTDatagenServer.init(event);
  }
}
