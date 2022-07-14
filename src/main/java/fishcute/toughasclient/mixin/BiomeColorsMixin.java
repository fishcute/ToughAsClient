package fishcute.toughasclient.mixin;

import ca.weblite.objc.Client;
import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.fluid.CleanedWater;
import fishcute.toughasclient.fluid.ClientFluidManager;
import fishcute.toughasclient.fluid.ICustomFluid;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.IntSupplier;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Inject(method = "getWaterColor", at = @At("RETURN"), cancellable = true)
    private static void getWaterColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> info) {
        ICustomFluid fluid = ClientFluidManager.getFluidAtLoc(pos);
        if (fluid!=null) {
            info.setReturnValue(fluid.waterColorMixed(info.getReturnValue()));
        }
    }

    /*
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
    private Sprite[] sprites(Sprite[] sprite) {
        return sprite;
    }
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
    private double injected(double x) {
        return x * 1.5D;
    }*/
}
