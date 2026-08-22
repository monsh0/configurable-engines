package monsh.steamengineconfig.mixin;

import com.simibubi.create.content.fluids.tank.BoilerData;
import monsh.steamengineconfig.Config;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BoilerData.class, remap = false)
public abstract class BoilerDataMixin {

    @Shadow
    private float waterSupply;

    @Shadow
    private int activeHeat;

    @Shadow
    private boolean passiveHeat;

    @Shadow
    private int attachedEngines;

    @Shadow
    public abstract int getMaxHeatLevelForBoilerSize(int boilerSize);

    @Shadow
    public abstract int getMaxHeatLevelForWaterSupply();

    @Inject(method = "getMaxHeatLevelForWaterSupply", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getMaxHeatLevelForWaterSupply(CallbackInfoReturnable<Integer> cir) {
        int waterPerLevel = Config.INSTANCE.waterPerTick.get();
        if (waterPerLevel <= 0) {
            cir.setReturnValue(18);
        } else {
            int value = (int) Math.min(18, Mth.ceil(waterSupply) / waterPerLevel);
            cir.setReturnValue(value);
        }
    }

    @Inject(method = "getEngineEfficiency", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getEngineEfficiency(int boilerSize, CallbackInfoReturnable<Float> cir) {
        int maxWater = getMaxHeatLevelForWaterSupply();
        boolean passive = passiveHeat
                && getMaxHeatLevelForBoilerSize(boilerSize) > 0
                && maxWater > 0;
        float efficiency;
        if (passive) {
            efficiency = (float) (Config.INSTANCE.passiveEfficiency.get() / Config.INSTANCE.levelsPerEngine.get());
        } else if (activeHeat == 0) {
            efficiency = 0;
        } else {
            int actualHeat = getActualHeat(boilerSize);
            float levelsPerEngine = Config.INSTANCE.levelsPerEngine.get().floatValue();
            float maxLevelsSupported = attachedEngines * levelsPerEngine;
            efficiency = (maxLevelsSupported >= actualHeat) ? 1 : actualHeat / maxLevelsSupported;
        }
        cir.setReturnValue(efficiency);
    }

    private int getActualHeat(int boilerSize) {
        int forBoilerSize = getMaxHeatLevelForBoilerSize(boilerSize);
        int forWaterSupply = getMaxHeatLevelForWaterSupply();
        return Math.min(activeHeat, Math.min(forWaterSupply, forBoilerSize));
    }
}
