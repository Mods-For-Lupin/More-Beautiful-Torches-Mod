package io.github.jason13official.more_beautiful_torches.impl.common.block;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallTorchBlockBase extends WallTorchBlock {

  public WallTorchBlockBase(SimpleParticleType flameParticle, BlockBehaviour.Properties properties) {
    super(flameParticle, properties);
  }
}
