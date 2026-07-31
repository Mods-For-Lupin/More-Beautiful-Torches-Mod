package io.github.jason13official.more_beautiful_torches;

import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class MoreBeautifulTorchesClientNeoForge {

  public MoreBeautifulTorchesClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> MoreBeautifulTorchesClient.init());
  }
}
