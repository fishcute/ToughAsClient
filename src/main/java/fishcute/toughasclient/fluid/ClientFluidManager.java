package fishcute.toughasclient.fluid;

import fishcute.toughasclient.ToughAsClientMod;
import fishcute.toughasclient.util.ClientUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ClientFluidManager {
    public static ArrayList<ICustomFluid> fluids = new ArrayList<>();
    static ArrayList<ICustomFluid> pendingRemovals = new ArrayList<>();
    public static boolean isFluidAtLoc(BlockPos b) {
        if (fluids.size()>0)
            for (ICustomFluid fluid : fluids) {
                if (fluid.pos.equals(b))
                    return true;
            }
        return false;
    }
    @Nullable
    public static ICustomFluid getFluidAtLoc(BlockPos b) {
        if (fluids.size()>0)
            for (ICustomFluid fluid : fluids) {
                if (fluid.pos.equals(b))
                    return fluid;
        }
        return null;
    }
    static void tickAllFluids() {
        if (fluids.size()>0)
            for (ICustomFluid fluid : fluids) {
                fluid.tickFluidInit();
            }
    }
    static void reRenderFluid(BlockPos pos) {
        //System.out.println(pos);
        BlockState b = ClientUtils.client().world.getBlockState(pos);
        ClientUtils.client().worldRenderer.updateBlock(
                ClientUtils.client().world, pos, b, b, 0
        );
    }
    public static void useFluidAtLoc(BlockPos b) {
        ICustomFluid fluid = getFluidAtLoc(b);
        if (fluid!=null)
            fluid.useFluidInit();
    }
    public static boolean instanceOfFluidAt(BlockPos loc, String type) {
        return isFluidAtLoc(loc)&&getFluidAtLoc(loc).isInstanceOf(type);
    }
    public static void tick() {
        if (pendingRemovals.size()>0) {
            for (ICustomFluid fluid : pendingRemovals) {
                fluids.remove(fluid);
                reRenderFluid(fluid.pos);
            }
        }
        tickAllFluids();
    }
    public static boolean canBePlacedAt(BlockPos p, ClientWorld w) {
        return canBePlacedAtDir(p, w, 1, 0, 0)&&
                canBePlacedAtDir(p, w, -1, 0, 0)&&
                canBePlacedAtDir(p, w, 0, 0, 1)&&
                canBePlacedAtDir(p, w, 0, 0, -1)&&
                canBePlacedAtDir(p, w, 0, 1, 0)&&
                canBePlacedAtDir(p, w, 0, -1, 0);
    }
    static boolean canBePlacedAtDir(BlockPos b, ClientWorld w, int x, int y, int z) {
        return !w.getBlockState(b.add(x, y, z)).getBlock().getTranslationKey().contains("water");
    }
}
