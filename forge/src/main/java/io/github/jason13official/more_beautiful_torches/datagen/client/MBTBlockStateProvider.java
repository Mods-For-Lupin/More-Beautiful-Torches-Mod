package io.github.jason13official.more_beautiful_torches.datagen.client;

import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RedstoneTorchBlock;
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
    for (TorchEntry entry : ModBlocks.TORCHES) {
      registerTorch(entry);
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      registerTorch(entry);
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      registerRedstoneTorch(entry);
    }
  }

  private void registerTorch(TorchEntry entry) {
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

  private void registerRedstoneTorch(TorchEntry entry) {
    ResourceLocation onTexture = modLoc("block/" + entry.name());
    ResourceLocation offTexture = modLoc("block/" + entry.name() + "_off");

    ModelFile standingOn = models()
        .withExistingParent(entry.name(), mcLoc("block/template_torch"))
        .texture("torch", onTexture)
        .renderType("minecraft:cutout");
    ModelFile standingOff = models()
        .withExistingParent(entry.name() + "_off", mcLoc("block/template_torch"))
        .texture("torch", offTexture)
        .renderType("minecraft:cutout");
    getVariantBuilder(entry.standing()).forAllStates(state -> ConfiguredModel.builder()
        .modelFile(state.getValue(RedstoneTorchBlock.LIT) ? standingOn : standingOff)
        .build());

    ModelFile wallOn = models()
        .withExistingParent(entry.name() + "_wall", mcLoc("block/template_torch_wall"))
        .texture("torch", onTexture)
        .renderType("minecraft:cutout");
    ModelFile wallOff = models()
        .withExistingParent(entry.name() + "_wall_off", mcLoc("block/template_torch_wall"))
        .texture("torch", offTexture)
        .renderType("minecraft:cutout");
    getVariantBuilder(entry.wall()).forAllStates(state -> ConfiguredModel.builder()
        .modelFile(state.getValue(RedstoneTorchBlock.LIT) ? wallOn : wallOff)
        .rotationY(wallAngle(state.getValue(WallTorchBlock.FACING)))
        .uvLock(true)
        .build());
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
