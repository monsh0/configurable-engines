package monsh.steamengineconfig.mixin;

import com.jesz.createdieselgenerators.content.diesel_engine.normal.DieselEngineBlockEntity;
import monsh.steamengineconfig.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DieselEngineBlockEntity.class, remap = false)
public class DieselEngineBlockEntityMixin {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getGeneratedSpeed(CallbackInfoReturnable<Float> cir) {
        float base = cir.getReturnValueF();
        float result = base
                * Config.INSTANCE.globalRpmMultiplier.get().floatValue()
                * Config.INSTANCE.normalRpmMultiplier.get().floatValue();
        cir.setReturnValue(result);
    }

    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$calculateAddedStressCapacity(CallbackInfoReturnable<Float> cir) {
        float base = cir.getReturnValueF();
        float result = base
                * Config.INSTANCE.globalStressMultiplier.get().floatValue()
                * Config.INSTANCE.normalStressMultiplier.get().floatValue();
        cir.setReturnValue(result);
    }


}
