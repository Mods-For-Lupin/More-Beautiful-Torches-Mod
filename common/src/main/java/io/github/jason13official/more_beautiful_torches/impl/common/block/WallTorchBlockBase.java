package io.github.jason13official.more_beautiful_torches.impl.common.block;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallTorchBlockBase extends WallTorchBlock {

  public WallTorchBlockBase(BlockBehaviour.Properties properties, ParticleOptions flameParticle) {
    super(properties, flameParticle);
  }
}
