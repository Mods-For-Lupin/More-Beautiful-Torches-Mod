package io.github.jason13official.more_beautiful_torches;

import net.minecraft.resources.ResourceLocation;


public class MoreBeautifulTorches {

  public static void init() {
  }

  public static ResourceLocation identifier(final String path) {
    return new ResourceLocation(Constants.MOD_ID, path);
  }
}