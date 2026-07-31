package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.monolib.platform.Services;
import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.MoreBeautifulTorches;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ModTabs {

  public static CreativeModeTab MORE_BEAUTIFUL_TORCHES;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    MORE_BEAUTIFUL_TORCHES = Services.registry().tabBuilder().build();

    consumer.accept(MORE_BEAUTIFUL_TORCHES, MoreBeautifulTorches.identifier(Constants.MOD_ID));
  }
}
