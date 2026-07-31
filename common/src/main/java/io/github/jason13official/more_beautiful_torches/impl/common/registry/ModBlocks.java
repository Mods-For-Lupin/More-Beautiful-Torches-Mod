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

  // Every vanilla block whose own texture is a single flat square i.e. Blocks/<name>.png
  // exists as-is;
  // excludes multi-face blocks (logs' end caps, pumpkin, basalt, etc.) for now
  public static final List<TorchEntry> TORCHES = List.of(
      torch(Blocks.ACACIA_LOG), torch(Blocks.ACACIA_PLANKS), torch(Blocks.AMETHYST_BLOCK), torch(Blocks.AMETHYST_CLUSTER),
      torch(Blocks.ANDESITE), torch(Blocks.BAMBOO_BLOCK), torch(Blocks.BAMBOO_PLANKS), torch(Blocks.BIRCH_LOG),
      torch(Blocks.BIRCH_PLANKS), torch(Blocks.BLACK_TERRACOTTA), torch(Blocks.BLACK_WOOL), torch(Blocks.BLACKSTONE),
      torch(Blocks.BLUE_ICE), torch(Blocks.BLUE_TERRACOTTA), torch(Blocks.BLUE_WOOL), torch(Blocks.BOOKSHELF),
      torch(Blocks.BRAIN_CORAL_BLOCK), torch(Blocks.BRICKS), torch(Blocks.BROWN_MUSHROOM), torch(Blocks.BROWN_MUSHROOM_BLOCK),
      torch(Blocks.BROWN_TERRACOTTA), torch(Blocks.BROWN_WOOL), torch(Blocks.BUBBLE_CORAL_BLOCK), torch(Blocks.BUDDING_AMETHYST),
      torch(Blocks.CALCITE), torch(Blocks.CHISELED_DEEPSLATE), torch(Blocks.CHISELED_NETHER_BRICKS), torch(Blocks.CHISELED_POLISHED_BLACKSTONE),
      torch(Blocks.CHISELED_QUARTZ_BLOCK), torch(Blocks.CHISELED_RED_SANDSTONE), torch(Blocks.CHISELED_SANDSTONE), torch(Blocks.CHISELED_STONE_BRICKS),
      torch(Blocks.CHORUS_FLOWER), torch(Blocks.CHORUS_PLANT), torch(Blocks.CLAY), torch(Blocks.COAL_BLOCK),
      torch(Blocks.COAL_ORE), torch(Blocks.COARSE_DIRT), torch(Blocks.COBBLED_DEEPSLATE), torch(Blocks.COBBLESTONE),
      torch(Blocks.COPPER_BLOCK), torch(Blocks.COPPER_ORE), torch(Blocks.CRACKED_DEEPSLATE_BRICKS), torch(Blocks.CRACKED_DEEPSLATE_TILES),
      torch(Blocks.CRACKED_NETHER_BRICKS), torch(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS), torch(Blocks.CRACKED_STONE_BRICKS), torch(Blocks.CRIMSON_FUNGUS),
      torch(Blocks.CRIMSON_NYLIUM), torch(Blocks.CRIMSON_PLANKS), torch(Blocks.CRIMSON_ROOTS), torch(Blocks.CRYING_OBSIDIAN),
      torch(Blocks.CUT_COPPER), torch(Blocks.CUT_RED_SANDSTONE), torch(Blocks.CUT_SANDSTONE), torch(Blocks.CYAN_TERRACOTTA),
      torch(Blocks.CYAN_WOOL), torch(Blocks.DARK_OAK_LOG), torch(Blocks.DARK_OAK_PLANKS), torch(Blocks.DARK_PRISMARINE),
      torch(Blocks.DEAD_BRAIN_CORAL_BLOCK), torch(Blocks.DEAD_BUBBLE_CORAL_BLOCK), torch(Blocks.DEAD_FIRE_CORAL_BLOCK), torch(Blocks.DEAD_HORN_CORAL_BLOCK),
      torch(Blocks.DEAD_TUBE_CORAL_BLOCK), torch(Blocks.DEEPSLATE), torch(Blocks.DEEPSLATE_BRICKS), torch(Blocks.DEEPSLATE_COAL_ORE),
      torch(Blocks.DEEPSLATE_COPPER_ORE), torch(Blocks.DEEPSLATE_DIAMOND_ORE), torch(Blocks.DEEPSLATE_EMERALD_ORE), torch(Blocks.DEEPSLATE_GOLD_ORE),
      torch(Blocks.DEEPSLATE_IRON_ORE), torch(Blocks.DEEPSLATE_LAPIS_ORE), torch(Blocks.DEEPSLATE_TILES), torch(Blocks.DIAMOND_BLOCK),
      torch(Blocks.DIAMOND_ORE), torch(Blocks.DIORITE), torch(Blocks.DIRT), torch(Blocks.DRIPSTONE_BLOCK),
      torch(Blocks.EMERALD_BLOCK), torch(Blocks.EMERALD_ORE), torch(Blocks.END_STONE), torch(Blocks.END_STONE_BRICKS),
      torch(Blocks.EXPOSED_COPPER), torch(Blocks.EXPOSED_CUT_COPPER), torch(Blocks.FIRE_CORAL_BLOCK), torch(Blocks.GILDED_BLACKSTONE),
      torch(Blocks.GLOWSTONE), torch(Blocks.GOLD_BLOCK), torch(Blocks.GOLD_ORE), torch(Blocks.GRANITE),
      torch(Blocks.GRAVEL), torch(Blocks.GRAY_TERRACOTTA), torch(Blocks.GRAY_WOOL), torch(Blocks.GREEN_TERRACOTTA),
      torch(Blocks.GREEN_WOOL), torch(Blocks.HONEYCOMB_BLOCK), torch(Blocks.HORN_CORAL_BLOCK), torch(Blocks.ICE),
      torch(Blocks.IRON_BARS), torch(Blocks.IRON_BLOCK), torch(Blocks.IRON_ORE), torch(Blocks.JUNGLE_LOG),
      torch(Blocks.JUNGLE_PLANKS), torch(Blocks.LAPIS_BLOCK), torch(Blocks.LAPIS_ORE), torch(Blocks.LIGHT_BLUE_TERRACOTTA),
      torch(Blocks.LIGHT_BLUE_WOOL), torch(Blocks.LIGHT_GRAY_TERRACOTTA), torch(Blocks.LIGHT_GRAY_WOOL), torch(Blocks.LIME_TERRACOTTA),
      torch(Blocks.LIME_WOOL), torch(Blocks.MAGENTA_TERRACOTTA), torch(Blocks.MAGENTA_WOOL), torch(Blocks.MANGROVE_LOG),
      torch(Blocks.MANGROVE_PLANKS), torch(Blocks.MOSS_BLOCK), torch(Blocks.MOSSY_COBBLESTONE), torch(Blocks.MOSSY_STONE_BRICKS),
      torch(Blocks.MUD), torch(Blocks.MUD_BRICKS), torch(Blocks.MUSHROOM_STEM), torch(Blocks.NETHER_BRICKS),
      torch(Blocks.NETHER_GOLD_ORE), torch(Blocks.NETHER_QUARTZ_ORE), torch(Blocks.NETHER_WART_BLOCK), torch(Blocks.NETHERITE_BLOCK),
      torch(Blocks.NETHERRACK), torch(Blocks.OAK_LOG), torch(Blocks.OAK_PLANKS), torch(Blocks.OBSIDIAN),
      torch(Blocks.ORANGE_TERRACOTTA), torch(Blocks.ORANGE_WOOL), torch(Blocks.OXIDIZED_COPPER), torch(Blocks.OXIDIZED_CUT_COPPER),
      torch(Blocks.PACKED_ICE), torch(Blocks.PACKED_MUD), torch(Blocks.PINK_TERRACOTTA), torch(Blocks.PINK_WOOL),
      torch(Blocks.POLISHED_ANDESITE), torch(Blocks.POLISHED_BLACKSTONE), torch(Blocks.POLISHED_BLACKSTONE_BRICKS), torch(Blocks.POLISHED_DEEPSLATE),
      torch(Blocks.POLISHED_DIORITE), torch(Blocks.POLISHED_GRANITE), torch(Blocks.PRISMARINE_BRICKS), torch(Blocks.PURPLE_TERRACOTTA),
      torch(Blocks.PURPLE_WOOL), torch(Blocks.PURPUR_BLOCK), torch(Blocks.PURPUR_PILLAR), torch(Blocks.QUARTZ_BRICKS),
      torch(Blocks.QUARTZ_PILLAR), torch(Blocks.RAW_COPPER_BLOCK), torch(Blocks.RAW_GOLD_BLOCK), torch(Blocks.RAW_IRON_BLOCK),
      torch(Blocks.RED_MUSHROOM), torch(Blocks.RED_MUSHROOM_BLOCK), torch(Blocks.RED_NETHER_BRICKS), torch(Blocks.RED_SAND),
      torch(Blocks.RED_SANDSTONE), torch(Blocks.RED_TERRACOTTA), torch(Blocks.RED_WOOL), torch(Blocks.REDSTONE_BLOCK),
      torch(Blocks.REDSTONE_ORE), torch(Blocks.ROOTED_DIRT), torch(Blocks.SAND), torch(Blocks.SANDSTONE),
      torch(Blocks.SCULK), torch(Blocks.SHROOMLIGHT), torch(Blocks.SLIME_BLOCK), torch(Blocks.SMOOTH_BASALT),
      torch(Blocks.SMOOTH_STONE), torch(Blocks.SOUL_SAND), torch(Blocks.SOUL_SOIL), torch(Blocks.SPONGE),
      torch(Blocks.SPRUCE_LOG), torch(Blocks.SPRUCE_PLANKS), torch(Blocks.STONE), torch(Blocks.STONE_BRICKS),
      torch(Blocks.STRIPPED_ACACIA_LOG), torch(Blocks.STRIPPED_BAMBOO_BLOCK), torch(Blocks.STRIPPED_BIRCH_LOG), torch(Blocks.STRIPPED_DARK_OAK_LOG),
      torch(Blocks.STRIPPED_JUNGLE_LOG), torch(Blocks.STRIPPED_MANGROVE_LOG), torch(Blocks.STRIPPED_OAK_LOG), torch(Blocks.STRIPPED_SPRUCE_LOG),
      torch(Blocks.TERRACOTTA), torch(Blocks.TUBE_CORAL_BLOCK), torch(Blocks.TUFF), torch(Blocks.WARPED_FUNGUS),
      torch(Blocks.WARPED_NYLIUM), torch(Blocks.WARPED_PLANKS), torch(Blocks.WARPED_ROOTS), torch(Blocks.WARPED_WART_BLOCK),
      torch(Blocks.WEATHERED_COPPER), torch(Blocks.WEATHERED_CUT_COPPER), torch(Blocks.WET_SPONGE), torch(Blocks.WHITE_TERRACOTTA),
      torch(Blocks.WHITE_WOOL), torch(Blocks.YELLOW_TERRACOTTA), torch(Blocks.YELLOW_WOOL)
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
