package monsh.steamengineconfig.mixin;

import com.jesz.createdieselgenerators.content.burner.BurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import monsh.steamengineconfig.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BurnerBlockEntity.class, remap = false)
public class BurnerBlockEntityMixin {

    @ModifyConstant(
        method = "tick",
        constant = @Constant(intValue = 1, ordinal = 0)
    )
    private int steamengineconfig$modifyDrainRate(int original) {
        return Config.INSTANCE.burnerDrainRate.get();
    }

    @Overwrite
    public BlazeBurnerBlock.HeatLevel calculateHeatLevel(float heat) {
        double seething = Config.INSTANCE.burnerHeatThresholdSeething.get();
        double kindled = Config.INSTANCE.burnerHeatThresholdKindled.get();
        double fading = Config.INSTANCE.burnerHeatThresholdFading.get();
        double smouldering = Config.INSTANCE.burnerHeatThresholdSmouldering.get();

        if (heat >= seething)
            return BlazeBurnerBlock.HeatLevel.SEETHING;
        if (heat >= kindled)
            return BlazeBurnerBlock.HeatLevel.KINDLED;
        if (heat >= fading)
            return BlazeBurnerBlock.HeatLevel.FADING;
        if (heat >= smouldering)
            return BlazeBurnerBlock.HeatLevel.SMOULDERING;
        return BlazeBurnerBlock.HeatLevel.NONE;
    }
}
