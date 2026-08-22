package monsh.steamengineconfig.mixin;

import com.jesz.createdieselgenerators.content.diesel_engine.EngineUpgrades;
import com.jesz.createdieselgenerators.content.diesel_engine.IEngine;
import monsh.steamengineconfig.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EngineUpgrades.TurbochargerUpgrade.class, remap = false)
public class TurbochargerUpgradeMixin {

    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getSpeed(float speed, IEngine engine, CallbackInfoReturnable<Float> cir) {
        if (!Config.INSTANCE.turboEnabled.get()) {
            cir.setReturnValue(speed);
            return;
        }
        float multiplier = Config.INSTANCE.turboSpeedMultiplier.get().floatValue();
        cir.setReturnValue(speed * multiplier);
    }

    @Inject(method = "getCapacity", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getCapacity(float capacity, IEngine engine, CallbackInfoReturnable<Float> cir) {
        if (!Config.INSTANCE.turboEnabled.get()) {
            cir.setReturnValue(capacity);
            return;
        }
        float multiplier = Config.INSTANCE.turboStressMultiplier.get().floatValue();
        cir.setReturnValue(capacity * multiplier);
    }
}
