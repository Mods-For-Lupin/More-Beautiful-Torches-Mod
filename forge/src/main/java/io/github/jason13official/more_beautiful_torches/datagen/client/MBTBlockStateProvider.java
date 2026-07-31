package io.github.jason13official.more_beautiful_torches.datagen.client;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MBTBlockStateProvider extends BlockStateProvider {

  public MBTBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
    super(output, modid, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      ResourceLocation torchTexture = modLoc("block/" + entry.name());

      ModelFile standingModel = models()
          .withExistingParent(entry.name(), mcLoc("block/template_torch"))
          .texture("torch", torchTexture)
          .renderType("minecraft:cutout");
      simpleBlock(entry.standing(), standingModel);

      ModelFile wallModel = models()
          .withExistingParent(entry.name() + "_wall", mcLoc("block/template_torch_wall"))
          .texture("torch", torchTexture)
          .renderType("minecraft:cutout");
      getVariantBuilder(entry.wall()).forAllStates(state -> ConfiguredModel.builder()
          .modelFile(wallModel)
          .rotationY(wallAngle(state.getValue(WallTorchBlock.FACING)))
          .uvLock(true)
          .build());
    }
  }

  private static int wallAngle(Direction facing) {
    return switch (facing) {
      case NORTH -> 270;
      case SOUTH -> 90;
      case WEST -> 180;
      default -> 0; // EAST
    };
  }
}
