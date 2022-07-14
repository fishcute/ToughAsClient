package fishcute.toughasclient.fluid;

import fishcute.toughasclient.ClientInit;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CleanedWater extends ICustomFluid{
    public CleanedWater(BlockPos loc, int uses) {
        super(loc, uses);
    }

    @Override
    public int waterColor() {
        return 0x44aff5;
    }

    @Override
    @Nullable
    public DefaultParticleType getParticle() {
        return ClientInit.CLEAN_PARTICLE;
    }

    @Override
    @Nullable
    public DefaultParticleType getUsageParticle() {
        return ParticleTypes.HEART;
    }

    @Override
    public String getName() {
        return "Cleaned Water";
    }

    @Override
    void tickFluid() {

    }

    @Override
    void useFluid() {

    }
}
