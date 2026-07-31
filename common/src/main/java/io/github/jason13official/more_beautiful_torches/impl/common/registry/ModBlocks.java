package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.block.TorchBlockBase;
import io.github.jason13official.more_beautiful_torches.impl.common.block.WallTorchBlockBase;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public record TorchEntry(String name, Block standing, Block wall, Block source) {}

  // Pilot set: one representative per material family (wood, stone, mineral, wool, glass,
  // terrain, nether). Expand this list to cover every block that "makes sense" as a torch.
  public static final List<TorchEntry> TORCHES = List.of(
      torch(Blocks.OAK_PLANKS), torch(Blocks.CRIMSON_PLANKS),
      torch(Blocks.STONE), torch(Blocks.COBBLESTONE), torch(Blocks.BRICKS),
      torch(Blocks.IRON_BLOCK), torch(Blocks.GOLD_BLOCK), torch(Blocks.DIAMOND_BLOCK),
      torch(Blocks.EMERALD_BLOCK), torch(Blocks.COAL_BLOCK),
      torch(Blocks.WHITE_WOOL), torch(Blocks.RED_WOOL), torch(Blocks.BLACK_WOOL),
      torch(Blocks.GLASS),
      torch(Blocks.DIRT), torch(Blocks.SAND),
      torch(Blocks.NETHERRACK), torch(Blocks.OBSIDIAN),
      torch(Blocks.TERRACOTTA), torch(Blocks.BLACKSTONE)
  );

  private static TorchEntry torch(Block source) {
    String name = BuiltInRegistries.BLOCK.getKey(source).getPath() + "_torch";
    Block standing = new TorchBlockBase(torchProperties(), ParticleTypes.FLAME);
    Block wall = new WallTorchBlockBase(torchProperties().dropsLike(standing), ParticleTypes.FLAME);
    return new TorchEntry(name, standing, wall, source);
  }

  private static BlockBehaviour.Properties torchProperties() {
    return BlockBehaviour.Properties.of()
        .noCollission()
        .instabreak()
        .lightLevel(state -> 14)
        .sound(SoundType.WOOD);
  }

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {
    for (TorchEntry entry : TORCHES) {
      consumer.accept(entry.standing(), new ResourceLocation(Constants.MOD_ID, entry.name()));
      consumer.accept(entry.wall(), new ResourceLocation(Constants.MOD_ID, entry.name() + "_wall"));
    }
  }
}
