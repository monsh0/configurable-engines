package monsh.steamengineconfig.mixin;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import monsh.steamengineconfig.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(value = PoweredShaftBlockEntity.class, remap = false)
public class PoweredShaftBlockEntityMixin {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getGeneratedSpeed(CallbackInfoReturnable<Float> cir) {
        float rpm = Config.INSTANCE.engineRPM.get();
        if (rpm == 0f) { cir.setReturnValue(0f); return; }
        cir.setReturnValue(cir.getReturnValueF() * rpm / 64f);
    }

    @ModifyReturnValue(method = "calculateAddedStressCapacity")
    private float steamengineconfig$stress(float original) {
        float rpm = Config.INSTANCE.engineRPM.get();
        if (rpm == 0f) return 0f;
        return original * 64f / rpm * Config.INSTANCE.levelsPerEngine.get().floatValue();
    }
}
