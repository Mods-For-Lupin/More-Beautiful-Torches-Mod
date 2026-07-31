package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.monolib.platform.Services;
import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.MoreBeautifulTorches;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab MORE_BEAUTIFUL_TORCHES;

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {

    MORE_BEAUTIFUL_TORCHES = Services.registry().tabBuilder()
        .icon(() -> new ItemStack(ModBlocks.TORCHES.get(0).standing()))
        .title(Component.translatable("itemGroup.moreBeautifulTorches"))
        .displayItems(((itemDisplayParameters, output) -> {

          ModBlocks.TORCHES.forEach(torch -> output.accept(torch.standing()));
          ModBlocks.SOUL_TORCHES.forEach(torch -> output.accept(torch.standing()));
          ModBlocks.REDSTONE_TORCHES.forEach(torch -> output.accept(torch.standing()));
        })).build();

    consumer.accept(MORE_BEAUTIFUL_TORCHES, MoreBeautifulTorches.identifier(Constants.MOD_ID));
  }
}
