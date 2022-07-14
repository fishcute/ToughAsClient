package fishcute.toughasclient.fluid;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import fishcute.toughasclient.util.Utils;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class ICustomFluid {
    private boolean hasRendered = false;
    public abstract int waterColor();
    public int waterColorMixed(int color) {
        return Utils.blendColors(waterColor(), color, (float)(uses)/(float)(maxUses));
    }
    @Nullable
    public abstract DefaultParticleType getParticle();
    @Nullable
    public abstract DefaultParticleType getUsageParticle();
    public abstract String getName();
    public void tickFluidInit() {
        if (!this.hasRendered) {
            this.hasRendered = true;
            ClientFluidManager.reRenderFluid(this.pos);
        }
        int particleCount = this.uses;
        if (particleCount>5)
            particleCount = 5;
        if (getParticle()!=null&&Math.random()>(0.9 + ((this.uses/100)*2)))
            ClientUtils.particleGroup(getParticle(), this.pos.getX()+0.5, this.pos.getY() + 1, this.pos.getZ()+0.5,
                    1, 0.1, 1, particleCount);
        if (this.uses<=0||!ClientUtils.world().getBlockState(this.pos).getBlock().getTranslationKey().contains("water"))
            remove();
        //TODO: Rerender fluid on end of usages
        tickFluid();
    }
    abstract void tickFluid();
    public void useFluidInit() {
        this.uses--;
        useFluid();
    }
    abstract void useFluid();
    public BlockPos pos;
    public int uses;
    public final int maxUses;
    public ICustomFluid(BlockPos pos, int uses) {
        this.pos = pos;
        this.uses = uses;
        this.maxUses = uses;
    }
    public void remove() {
        ClientFluidManager.pendingRemovals.add(this);
    }
    public void assign() {
        ClientFluidManager.fluids.add(this);
    }
    public boolean isInstanceOf(String fluid) {
        return getName().equals(fluid);
    }
    /*
    package fishcute.toughasclient.block;

import fishcute.toughasclient.ClientInit;
import fishcute.toughasclient.util.ClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class CleanedWater {
    public static ArrayList<CleanedWater> waterList = new ArrayList<>();
    static ArrayList<CleanedWater> pendingRemoval = new ArrayList<>();
    public CleanedWater(BlockPos p, int uses) {
        pos = p;
        uses = uses;
    }
    public BlockPos pos;
    public int uses;
    public void tick() {
        if (Math.random()>(0.9 + ((uses/100)*2)))
            ClientUtils.particleGroup(ClientInit.CLEAN_PARTICLE, pos.getX()+0.5, pos.getY() + 1, pos.getZ()+0.5,
                1, 0.1, 1, uses);
        if (uses<=0||!ClientUtils.world().getBlockState(pos).getBlock().getTranslationKey().contains("water"))
            remove();
    }
    public boolean isAtLoc(BlockPos p) {
        return (p.getX() == pos.getX() && p.getY() == pos.getY() && p.getZ() == pos.getZ());
    }
    public void remove() {
        pendingRemoval.add(this);
    }
    public void drinkFrom() {
        uses--;
    }
    public void assign() {
        waterList.add(this);
    }
    public static void tickAll() {
        for (CleanedWater w : waterList)
            w.tick();
        if (pendingRemoval.size()>0)
            for (CleanedWater w : pendingRemoval)
                waterList.remove(w);
    }
    public static boolean isCleanedAtLoc(BlockPos p) {
        for (CleanedWater w : waterList) {
            if (w.isAtLoc(p))
                return true;
        }
        return false;
    }
    public static CleanedWater getCleanedAtLoc(BlockPos p) {
        for (CleanedWater w : waterList)
            if (w.isAtLoc(p))
                return w;
        return null;
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

     */
}
