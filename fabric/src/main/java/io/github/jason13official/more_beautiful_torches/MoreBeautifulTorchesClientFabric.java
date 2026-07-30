package io.github.jason13official.more_beautiful_torches;

import net.fabricmc.api.ClientModInitializer;

public class MoreBeautifulTorchesClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreBeautifulTorchesClient.init();
  }
}
