package fishcute.toughasclient.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VectorDirection {
    public double x;
    public double y;
    public double z;

    public VectorDirection(float pitch, float yaw) {
        float f = pitch;
        float f1 = yaw;
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        this.x=f6;
        this.y=f5;
        this.z=f7;
    }

    public VectorDirection() {
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public static VectorDirection fromDoubles(double x, double y, double z) {
        VectorDirection returnValue = new VectorDirection();
        returnValue.x = x;
        returnValue.y = y;
        returnValue.z = z;
        return returnValue;
    }

    public static VectorDirection getVectorDirectionFromAngle (float pitch, float yaw) {
        float f = pitch;
        float f1 = yaw;
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        return fromDoubles(f6,f5,f7);
    }

    public Vec3d getVec3d() {
        return new Vec3d(x,y,z);
    }

    public VectorDirection mult(double factor) {
        this.x*=factor;
        this.y*=factor;
        this.z*=factor;
        return this;
    }

    public VectorDirection add(double factor) {
        this.x+=factor;
        this.y+=factor;
        this.z+=factor;
        return this;
    }

}
