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
        this.pos = p;
        this.uses = uses;
    }
    public BlockPos pos;
    public int uses;
    public void tick() {
        if (Math.random()>(0.9 + ((this.uses/100)*2)))
            ClientUtils.particleGroup(ClientInit.CLEAN_PARTICLE, pos.getX()+0.5, pos.getY() + 1, pos.getZ()+0.5,
                1, 0.1, 1, this.uses);
        if (this.uses<=0||!ClientUtils.world().getBlockState(pos).getBlock().getTranslationKey().contains("water"))
            this.remove();
    }
    public boolean isAtLoc(BlockPos p) {
        return (p.getX() == pos.getX() && p.getY() == pos.getY() && p.getZ() == pos.getZ());
    }
    public void remove() {
        pendingRemoval.add(this);
    }
    public void drinkFrom() {
        this.uses--;
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
