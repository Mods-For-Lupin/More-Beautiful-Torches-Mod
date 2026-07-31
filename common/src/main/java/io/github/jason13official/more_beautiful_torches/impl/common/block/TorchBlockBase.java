package io.github.jason13official.more_beautiful_torches.impl.common.block;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TorchBlockBase extends TorchBlock {

  public TorchBlockBase(SimpleParticleType flameParticle, BlockBehaviour.Properties properties) {
    super(flameParticle, properties);
  }
}
