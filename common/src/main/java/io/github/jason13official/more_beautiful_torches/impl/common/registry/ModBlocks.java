package io.github.jason13official.more_beautiful_torches.impl.common.registry;

import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.block.RedstoneTorchBlockBase;
import io.github.jason13official.more_beautiful_torches.impl.common.block.RedstoneWallTorchBlockBase;
import io.github.jason13official.more_beautiful_torches.impl.common.block.TorchBlockBase;
import io.github.jason13official.more_beautiful_torches.impl.common.block.WallTorchBlockBase;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public enum TorchKind {NORMAL, REDSTONE, SOUL}

  public record TorchEntry(String name, TorchKind kind, Block standing, Block wall, Block source) {}

  // Every vanilla block whose own texture is a single flat square i.e. Blocks/<name>.png
  // exists as-is; excludes multi-face blocks (logs' end caps, pumpkin, basalt, etc.) for now
  private static final List<Block> MATERIALS = List.of(
      Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.AMETHYST_BLOCK, Blocks.AMETHYST_CLUSTER,
      Blocks.ANDESITE, Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, Blocks.BIRCH_LOG,
      Blocks.BIRCH_PLANKS, Blocks.BLACK_TERRACOTTA, Blocks.BLACK_WOOL, Blocks.BLACKSTONE,
      Blocks.BLUE_ICE, Blocks.BLUE_TERRACOTTA, Blocks.BLUE_WOOL, Blocks.BOOKSHELF,
      Blocks.BRAIN_CORAL_BLOCK, Blocks.BRICKS, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK,
      Blocks.BROWN_TERRACOTTA, Blocks.BROWN_WOOL, Blocks.BUBBLE_CORAL_BLOCK, Blocks.BUDDING_AMETHYST,
      Blocks.CALCITE, Blocks.CHISELED_DEEPSLATE, Blocks.CHISELED_NETHER_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE,
      Blocks.CHISELED_QUARTZ_BLOCK, Blocks.CHISELED_RED_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CHISELED_STONE_BRICKS,
      Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.CLAY, Blocks.COAL_BLOCK,
      Blocks.COAL_ORE, Blocks.COARSE_DIRT, Blocks.COBBLED_DEEPSLATE, Blocks.COBBLESTONE,
      Blocks.COPPER_BLOCK, Blocks.COPPER_ORE, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_TILES,
      Blocks.CRACKED_NETHER_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CRIMSON_FUNGUS,
      Blocks.CRIMSON_NYLIUM, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_ROOTS, Blocks.CRYING_OBSIDIAN,
      Blocks.CUT_COPPER, Blocks.CUT_RED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CYAN_TERRACOTTA,
      Blocks.CYAN_WOOL, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_PRISMARINE,
      Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK,
      Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_COAL_ORE,
      Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
      Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_TILES, Blocks.DIAMOND_BLOCK,
      Blocks.DIAMOND_ORE, Blocks.DIORITE, Blocks.DIRT, Blocks.DRIPSTONE_BLOCK,
      Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE, Blocks.END_STONE, Blocks.END_STONE_BRICKS,
      Blocks.EXPOSED_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.FIRE_CORAL_BLOCK, Blocks.GILDED_BLACKSTONE,
      Blocks.GLOWSTONE, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.GRANITE,
      Blocks.GRAVEL, Blocks.GRAY_TERRACOTTA, Blocks.GRAY_WOOL, Blocks.GREEN_TERRACOTTA,
      Blocks.GREEN_WOOL, Blocks.HONEYCOMB_BLOCK, Blocks.HORN_CORAL_BLOCK, Blocks.ICE,
      Blocks.IRON_BARS, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.JUNGLE_LOG,
      Blocks.JUNGLE_PLANKS, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIGHT_BLUE_TERRACOTTA,
      Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_WOOL, Blocks.LIME_TERRACOTTA,
      Blocks.LIME_WOOL, Blocks.MAGENTA_TERRACOTTA, Blocks.MAGENTA_WOOL, Blocks.MANGROVE_LOG,
      Blocks.MANGROVE_PLANKS, Blocks.MOSS_BLOCK, Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_STONE_BRICKS,
      Blocks.MUD, Blocks.MUD_BRICKS, Blocks.MUSHROOM_STEM, Blocks.NETHER_BRICKS,
      Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE, Blocks.NETHER_WART_BLOCK, Blocks.NETHERITE_BLOCK,
      Blocks.NETHERRACK, Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.OBSIDIAN,
      Blocks.ORANGE_TERRACOTTA, Blocks.ORANGE_WOOL, Blocks.OXIDIZED_COPPER, Blocks.OXIDIZED_CUT_COPPER,
      Blocks.PACKED_ICE, Blocks.PACKED_MUD, Blocks.PINK_TERRACOTTA, Blocks.PINK_WOOL,
      Blocks.POLISHED_ANDESITE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_DEEPSLATE,
      Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.PRISMARINE_BRICKS, Blocks.PURPLE_TERRACOTTA,
      Blocks.PURPLE_WOOL, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.QUARTZ_BRICKS,
      Blocks.QUARTZ_PILLAR, Blocks.RAW_COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_IRON_BLOCK,
      Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK, Blocks.RED_NETHER_BRICKS, Blocks.RED_SAND,
      Blocks.RED_SANDSTONE, Blocks.RED_TERRACOTTA, Blocks.RED_WOOL, Blocks.REDSTONE_BLOCK,
      Blocks.REDSTONE_ORE, Blocks.ROOTED_DIRT, Blocks.SAND, Blocks.SANDSTONE,
      Blocks.SCULK, Blocks.SHROOMLIGHT, Blocks.SLIME_BLOCK, Blocks.SMOOTH_BASALT,
      Blocks.SMOOTH_STONE, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.SPONGE,
      Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.STONE, Blocks.STONE_BRICKS,
      Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_BAMBOO_BLOCK, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_DARK_OAK_LOG,
      Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_SPRUCE_LOG,
      Blocks.TERRACOTTA, Blocks.TUBE_CORAL_BLOCK, Blocks.TUFF, Blocks.WARPED_FUNGUS,
      Blocks.WARPED_NYLIUM, Blocks.WARPED_PLANKS, Blocks.WARPED_ROOTS, Blocks.WARPED_WART_BLOCK,
      Blocks.WEATHERED_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.WET_SPONGE, Blocks.WHITE_TERRACOTTA,
      Blocks.WHITE_WOOL, Blocks.YELLOW_TERRACOTTA, Blocks.YELLOW_WOOL,

      // New in 1.21.1 vs 1.20.1 (tuff variants added with the Tuff building blocks update).
      // waxed_chiseled_copper/waxed_copper_bulb/crafter excluded - multi-face or complex model.
      Blocks.CHISELED_TUFF, Blocks.CHISELED_TUFF_BRICKS, Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS,

      // New in 26.1.2 vs 1.21.1 (pale oak wood set + resin blocks + assorted plants/decor).
      // Excluded: *_shelf family (32x32 textures, our compositing assumes 16x16), pale_oak_wood/
      // stripped_pale_oak_wood (no plain texture - they reuse the log's bark texture), copper
      // chests/golem statues (complex multi-part models), copper_torch/copper_wall_torch
      // (already a torch), creaking_heart/dried_ghast/lightning_rod variants (complex or thin-rod
      // shapes), potted_* (a pot holding a plant, not a material), test blocks.
      Blocks.BUSH, Blocks.CACTUS_FLOWER, Blocks.CHISELED_RESIN_BRICKS, Blocks.CLOSED_EYEBLOSSOM,
      Blocks.FIREFLY_BUSH, Blocks.GOLDEN_DANDELION, Blocks.IRON_CHAIN, Blocks.LEAF_LITTER,
      Blocks.OPEN_EYEBLOSSOM, Blocks.PALE_HANGING_MOSS, Blocks.PALE_MOSS_BLOCK, Blocks.PALE_MOSS_CARPET,
      Blocks.PALE_OAK_LEAVES, Blocks.PALE_OAK_LOG, Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_SAPLING,
      Blocks.RESIN_BLOCK, Blocks.RESIN_BRICKS, Blocks.RESIN_CLUMP, Blocks.SHORT_DRY_GRASS,
      Blocks.STRIPPED_PALE_OAK_LOG, Blocks.TALL_DRY_GRASS, Blocks.WILDFLOWERS
  );

  public static final List<TorchEntry> TORCHES = MATERIALS.stream().map(ModBlocks::torch).toList();
  public static final List<TorchEntry> REDSTONE_TORCHES = MATERIALS.stream().map(ModBlocks::redstoneTorch).toList();
  public static final List<TorchEntry> SOUL_TORCHES = MATERIALS.stream().map(ModBlocks::soulTorch).toList();

  private static TorchEntry torch(Block source) {
    String name = BuiltInRegistries.BLOCK.getKey(source).getPath() + "_torch";
    Block standing = new TorchBlockBase(ParticleTypes.FLAME, torchProperties(14).setId(blockKey(name)));
    Block wall = new WallTorchBlockBase(ParticleTypes.FLAME,
        torchProperties(14).overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.NORMAL, standing, wall, source);
  }

  private static TorchEntry soulTorch(Block source) {
    String name = BuiltInRegistries.BLOCK.getKey(source).getPath() + "_soul_torch";
    Block standing = new TorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME, torchProperties(10).setId(blockKey(name)));
    Block wall = new WallTorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME,
        torchProperties(10).overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.SOUL, standing, wall, source);
  }

  private static TorchEntry redstoneTorch(Block source) {
    String name = BuiltInRegistries.BLOCK.getKey(source).getPath() + "_redstone_torch";
    Block standing = new RedstoneTorchBlockBase(redstoneTorchProperties().setId(blockKey(name)));
    Block wall = new RedstoneWallTorchBlockBase(
        redstoneTorchProperties().overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.REDSTONE, standing, wall, source);
  }

  private static ResourceKey<Block> blockKey(String name) {
    return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
  }

  private static BlockBehaviour.Properties torchProperties(int lightLevel) {
    return BlockBehaviour.Properties.of()
        .noCollision()
        .instabreak()
        .lightLevel(state -> lightLevel)
        .sound(SoundType.WOOD);
  }

  private static BlockBehaviour.Properties redstoneTorchProperties() {
    return BlockBehaviour.Properties.of()
        .noCollision()
        .instabreak()
        .lightLevel(state -> state.getValue(RedstoneTorchBlock.LIT) ? 7 : 0)
        .sound(SoundType.WOOD);
  }

  public static void register(BiConsumer<Block, Identifier> consumer) {
    registerAll(TORCHES, consumer);
    registerAll(REDSTONE_TORCHES, consumer);
    registerAll(SOUL_TORCHES, consumer);
  }

  private static void registerAll(List<TorchEntry> entries, BiConsumer<Block, Identifier> consumer) {
    for (TorchEntry entry : entries) {
      consumer.accept(entry.standing(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.name()));
      consumer.accept(entry.wall(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.name() + "_wall"));
    }
  }
}
