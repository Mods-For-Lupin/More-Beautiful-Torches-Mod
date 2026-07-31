package io.github.jason13official.more_beautiful_torches.datagen.client;

import com.mojang.math.Quadrant;
import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.util.stream.Stream;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MBTModelProvider extends ModelProvider {

  private static final VariantMutator NOP = v -> v;
  private static final VariantMutator Y_ROT_90 = VariantMutator.Y_ROT.withValue(Quadrant.R90);
  private static final VariantMutator Y_ROT_180 = VariantMutator.Y_ROT.withValue(Quadrant.R180);
  private static final VariantMutator Y_ROT_270 = VariantMutator.Y_ROT.withValue(Quadrant.R270);

  // Mirrors vanilla BlockModelGenerators' private ROTATION_TORCH dispatch (not
  // accessible from here since it's a different package) - EAST is the model's
  // unrotated default facing, matching vanilla's own wall_torch blockstate.
  private static final PropertyDispatch<VariantMutator> ROTATION_TORCH =
      PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
          .select(net.minecraft.core.Direction.EAST, NOP)
          .select(net.minecraft.core.Direction.SOUTH, Y_ROT_90)
          .select(net.minecraft.core.Direction.WEST, Y_ROT_180)
          .select(net.minecraft.core.Direction.NORTH, Y_ROT_270);

  public MBTModelProvider(PackOutput output) {
    super(output, Constants.MOD_ID);
  }

  @Override
  protected Stream<? extends Holder<Block>> getKnownBlocks() {
    return Stream.of(ModBlocks.TORCHES, ModBlocks.SOUL_TORCHES, ModBlocks.REDSTONE_TORCHES)
        .flatMap(entries -> entries.stream().flatMap(e -> Stream.of(e.standing(), e.wall())))
        .map(Block::builtInRegistryHolder);
  }

  @Override
  protected void registerModels(BlockModelGenerators bg, ItemModelGenerators ig) {
    for (TorchEntry entry : ModBlocks.TORCHES) {
      normalTorch(bg, entry);
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      normalTorch(bg, entry);
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      redstoneTorch(bg, entry);
    }
  }

  private static void normalTorch(BlockModelGenerators bg, TorchEntry entry) {
    TextureMapping textures = TextureMapping.torch(entry.standing());

    bg.blockStateOutput.accept(
        MultiVariantGenerator.dispatch(entry.standing(),
            mv(ModelTemplates.TORCH.create(entry.standing(), textures, bg.modelOutput)))
    );
    bg.blockStateOutput.accept(
        MultiVariantGenerator.dispatch(entry.wall(),
                mv(ModelTemplates.WALL_TORCH.create(entry.wall(), textures, bg.modelOutput)))
            .with(ROTATION_TORCH)
    );
    bg.registerSimpleFlatItemModel(entry.standing());
  }

  private static void redstoneTorch(BlockModelGenerators bg, TorchEntry entry) {
    Material onTex = TextureMapping.getBlockTexture(entry.standing());
    Material offTex = TextureMapping.getBlockTexture(entry.standing(), "_off");
    TextureMapping onTextures = TextureMapping.torch(onTex);
    TextureMapping offTextures = TextureMapping.torch(offTex);

    Identifier groundOn = ModelTemplates.REDSTONE_TORCH.create(entry.standing(), onTextures, bg.modelOutput);
    Identifier groundOff = ModelTemplates.TORCH_UNLIT.createWithSuffix(entry.standing(), "_off", offTextures, bg.modelOutput);
    bg.blockStateOutput.accept(
        MultiVariantGenerator.dispatch(entry.standing())
            .with(PropertyDispatch.initial(BlockStateProperties.LIT)
                .select(true, mv(groundOn))
                .select(false, mv(groundOff)))
    );

    Identifier wallOn = ModelTemplates.REDSTONE_WALL_TORCH.create(entry.wall(), onTextures, bg.modelOutput);
    Identifier wallOff = ModelTemplates.WALL_TORCH_UNLIT.createWithSuffix(entry.wall(), "_off", offTextures, bg.modelOutput);
    bg.blockStateOutput.accept(
        MultiVariantGenerator.dispatch(entry.wall())
            .with(PropertyDispatch.initial(BlockStateProperties.LIT)
                .select(true, mv(wallOn))
                .select(false, mv(wallOff)))
            .with(ROTATION_TORCH)
    );

    bg.registerSimpleFlatItemModel(entry.standing());
  }

  private static MultiVariant mv(Identifier model) {
    return new MultiVariant(WeightedList.of(new Variant(model)));
  }
}
