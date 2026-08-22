package monsh.steamengineconfig.mixin;

import com.jesz.createdieselgenerators.content.diesel_engine.modular.ModularDieselEngineBlockEntity;
import monsh.steamengineconfig.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModularDieselEngineBlockEntity.class, remap = false)
public class ModularDieselEngineBlockEntityMixin {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getGeneratedSpeed(CallbackInfoReturnable<Float> cir) {
        float base = cir.getReturnValueF();
        float result = base
                * Config.INSTANCE.globalRpmMultiplier.get().floatValue()
                * Config.INSTANCE.modularRpmMultiplier.get().floatValue();
        cir.setReturnValue(result);
    }

    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$calculateAddedStressCapacity(CallbackInfoReturnable<Float> cir) {
        float base = cir.getReturnValueF();
        float result = base
                * Config.INSTANCE.globalStressMultiplier.get().floatValue()
                * Config.INSTANCE.modularStressMultiplier.get().floatValue();
        cir.setReturnValue(result);
    }


    @Inject(method = "getHeight", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getHeight(CallbackInfoReturnable<Integer> cir) {
        int current = cir.getReturnValue();
        int max = Config.INSTANCE.modularMaxLength.get();
        if (current > max) {
            cir.setReturnValue(max);
        }
    }
}
