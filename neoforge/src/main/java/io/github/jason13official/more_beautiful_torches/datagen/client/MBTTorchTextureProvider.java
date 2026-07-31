package io.github.jason13official.more_beautiful_torches.datagen.client;

import com.google.common.hash.Hashing;
import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks.TorchEntry;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;

// Vanilla's torch textures are 16x16: a thin 2px-wide column at x=7-8, rows 0-5
// transparent, and everything below that is flame/glow down to a "stick start"
// row where the wood texture begins - we replace the stick rows 1:1 by sampling
// the source block's own texture at the same (x, y) coordinate, and leave the
// flame/glow rows untouched. The stick start row differs per template because
// the lit redstone glow is drawn one row taller than the others.
public class MBTTorchTextureProvider implements DataProvider {

  private static final Identifier TORCH = fullTexture(Identifier.withDefaultNamespace("block/torch"));
  private static final Identifier SOUL_TORCH = fullTexture(Identifier.withDefaultNamespace("block/soul_torch"));
  private static final Identifier REDSTONE_TORCH = fullTexture(Identifier.withDefaultNamespace("block/redstone_torch"));
  private static final Identifier REDSTONE_TORCH_OFF = fullTexture(Identifier.withDefaultNamespace("block/redstone_torch_off"));

  private final PackOutput.PathProvider textures;
  private final ResourceManager resourceManager;

  public MBTTorchTextureProvider(PackOutput output, ResourceManager resourceManager) {
    this.textures = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures/block");
    this.resourceManager = resourceManager;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput output) {
    for (TorchEntry entry : ModBlocks.TORCHES) {
      writeTexture(output, entry.name(), TORCH, 8, entry.source());
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      writeTexture(output, entry.name(), SOUL_TORCH, 8, entry.source());
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      writeTexture(output, entry.name(), REDSTONE_TORCH, 9, entry.source());
      writeTexture(output, entry.name() + "_off", REDSTONE_TORCH_OFF, 8, entry.source());
    }
    return CompletableFuture.completedFuture(null);
  }

  private void writeTexture(CachedOutput output, String textureName, Identifier mask, int stickRowStart, Block source) {
    try {
      BufferedImage maskImage = readTexture(mask);
      BufferedImage sourceImage = readTexture(sourceTextureLocation(source));
      byte[] png = toPng(composite(maskImage, sourceImage, stickRowStart));

      Identifier textureLocation = Identifier.fromNamespaceAndPath(Constants.MOD_ID, textureName);
      output.writeIfNeeded(textures.file(textureLocation, "png"), png, Hashing.sha1().hashBytes(png));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static Identifier sourceTextureLocation(Block source) {
    return fullTexture(BuiltInRegistries.BLOCK.getKey(source).withPrefix("block/"));
  }

  private static Identifier fullTexture(Identifier blockTexture) {
    return blockTexture.withPrefix("textures/").withSuffix(".png");
  }

  private static BufferedImage composite(BufferedImage mask, BufferedImage source, int stickRowStart) {
    BufferedImage result = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < mask.getHeight(); y++) {
      for (int x = 0; x < mask.getWidth(); x++) {
        int maskPixel = mask.getRGB(x, y);
        boolean isStick = (maskPixel >>> 24) != 0 && y >= stickRowStart;
        result.setRGB(x, y, isStick ? source.getRGB(x, y) : maskPixel);
      }
    }
    return result;
  }

  private BufferedImage readTexture(Identifier fullResourceLocation) throws IOException {
    Optional<Resource> resource = resourceManager.getResource(fullResourceLocation);
    if (resource.isEmpty()) {
      throw new IOException("Could not find texture " + fullResourceLocation);
    }
    try (InputStream stream = resource.get().open()) {
      return ImageIO.read(stream);
    }
  }

  private static byte[] toPng(BufferedImage image) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ImageIO.write(image, "png", bytes);
    return bytes.toByteArray();
  }

  @Override
  public String getName() {
    return "More Beautiful Torches Textures";
  }
}
