package io.github.jason13official.more_beautiful_torches.datagen.client;

import com.google.common.hash.Hashing;
import io.github.jason13official.more_beautiful_torches.Constants;
import io.github.jason13official.more_beautiful_torches.impl.common.registry.ModBlocks;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.data.ExistingFileHelper;

/// Vanilla's torch texture is still a 16x16 PNG:
/// - thin 2px-wide column at x=7-8.
/// - rows 0-5 are transparent,
/// - rows 6-7 are the flame,
/// - rows 8-15 are the wooden stick.
///
/// We keep the flame as-is (current version/normal and soul torches) and
/// replace the stick pixels 1:1 by sampling the source block's
/// own texture at the same (x, y) coordinate.
public class MBTTorchTextureProvider implements DataProvider {

  private static final ResourceLocation TORCH_TEMPLATE = new ResourceLocation("minecraft", "block/torch");
  private static final int STICK_ROW_START = 8;

  private final PackOutput.PathProvider textures;
  private final ExistingFileHelper existingFileHelper;

  public MBTTorchTextureProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    this.textures = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures/block");
    this.existingFileHelper = existingFileHelper;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput output) {
    
    // this is on purpose; ExistingFileHelper's generated-resource multimap isn't
    // thread-safe, and trackGenerated() calls from parallel tasks silently dropped
    // entries (the PNG would still be written, but the later BlockStateProvider/
    // ItemModelProvider validation against that texture would randomly fail).
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      writeTexture(output, entry);
    }
    return CompletableFuture.completedFuture(null);
  }

  private void writeTexture(CachedOutput output, ModBlocks.TorchEntry entry) {
    try {
      BufferedImage mask = readTexture(TORCH_TEMPLATE);
      BufferedImage source = readTexture(sourceTextureLocation(entry));
      byte[] png = toPng(composite(mask, source));

      ResourceLocation textureLocation = new ResourceLocation(Constants.MOD_ID, entry.name());
      output.writeIfNeeded(textures.file(textureLocation, "png"), png, Hashing.sha1().hashBytes(png));

      // BlockStateProvider/ItemModelProvider reference this texture as "block/<name>"
      // (the path prefix baked into the ResourceLocation itself, not the PathProvider's
      // "kind") -> trackGenerated must be registered under that same location or their
      // ExistingFileHelper#exists() check won't see it as generated.
      existingFileHelper.trackGenerated(textureLocation.withPrefix("block/"), PackType.CLIENT_RESOURCES, ".png", "textures");
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static ResourceLocation sourceTextureLocation(ModBlocks.TorchEntry entry) {
    return BuiltInRegistries.BLOCK.getKey(entry.source()).withPrefix("block/");
  }

  private static BufferedImage composite(BufferedImage mask, BufferedImage source) {
    BufferedImage result = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < mask.getHeight(); y++) {
      for (int x = 0; x < mask.getWidth(); x++) {
        int maskPixel = mask.getRGB(x, y);
        boolean isStick = (maskPixel >>> 24) != 0 && y >= STICK_ROW_START;
        result.setRGB(x, y, isStick ? source.getRGB(x, y) : maskPixel);
      }
    }
    return result;
  }

  private BufferedImage readTexture(ResourceLocation blockTexture) throws IOException {
    Resource resource = existingFileHelper.getResource(blockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
    try (InputStream stream = resource.open()) {
      return ImageIO.read(stream);
    } catch (FileNotFoundException e) {
      throw new IOException("Could not find texture " + blockTexture, e);
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
